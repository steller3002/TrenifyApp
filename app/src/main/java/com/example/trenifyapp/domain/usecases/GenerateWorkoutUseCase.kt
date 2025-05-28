package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.DateConverter
import com.example.trenifyapp.data.entities.Muscle
import com.example.trenifyapp.data.entities.PhaseOfCycle
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.Workout
import com.example.trenifyapp.data.entities.WorkoutExercise
import com.example.trenifyapp.data.entities.WorkoutWithSelectedExercises
import java.util.Date
import javax.inject.Inject

class GenerateWorkoutUseCase @Inject constructor(
    private val _appDb: AppDb
) {
    suspend operator fun invoke(userId: Int): Workout {
        val userWithSelectedExercises = _appDb.userDao.getSelectedExercises(userId)
        val user = userWithSelectedExercises.user
        val workoutPlanWithPhases = _appDb.workoutPlanDao.getPhasesIds(userWithSelectedExercises.user.workoutPlanOwnerId)

        /*TODO:
        учёт предыдущих тренировок
         */
//        val lastWorkoutWithSelectedExercises = _appDb.workoutDao.getLastWorkoutWithSelectedExercises(
//            userId,
//            user.phaseOfCycleOwnerId
//        )
        val lastWorkoutWithSelectedExercises = null

        val newPhaseOfCycle = calcNewPhaseOfCycle(
            userWithSelectedExercises.user.phaseOfCycleOwnerId,
            workoutPlanWithPhases.phasesOfCycle
        )

        val newSelectedExercises = createNewSelectedExercises(
            userWithSelectedExercises.selectedExercises,
            _appDb.phaseOfCycleDao.getById(newPhaseOfCycle.phaseOfCycleId!!),
            lastWorkoutWithSelectedExercises
        )

        val workoutId = _appDb.workoutDao.create(
            Workout(
                workoutId = null,
                date = getCurrentDate(),
                userOwnerId = userId,
                phaseOfCycleOwnerId = newPhaseOfCycle.phaseOfCycleId
            )
        )

        newSelectedExercises.forEach { selectedExercise ->
            _appDb.workoutExerciseDao.create(
                WorkoutExercise(
                    workoutExerciseId = null,
                    selectedExerciseOwnerId = selectedExercise.selectedExerciseId!!,
                    workoutOwnerId = workoutId.toInt()
                )
            )
        }

        val newUser = user.copy(
            phaseOfCycleOwnerId = newPhaseOfCycle.phaseOfCycleId
        )
        _appDb.userDao.update(newUser)

        return _appDb.workoutDao.getById(workoutId.toInt())
    }

    private fun calcNewPhaseOfCycle(currentPhaseId: Int, phasesOfCycle: List<PhaseOfCycle>): PhaseOfCycle {
        val currentPhase = phasesOfCycle.find { it.phaseOfCycleId == currentPhaseId }

        if (currentPhase == null) return phasesOfCycle.first()
        else {
            val index = phasesOfCycle.indexOf(currentPhase)

            if (index + 1 >= phasesOfCycle.size) return phasesOfCycle[0]
            return phasesOfCycle[index + 1]
        }
    }

    private suspend fun createNewSelectedExercises(
        userSelectedExercises: List<SelectedExercise>,
        phaseOfCycle: PhaseOfCycle,
        lastWorkoutWithSelectedExercises: WorkoutWithSelectedExercises?
    ): List<SelectedExercise> {
        val result = mutableListOf<SelectedExercise>()

        val muscleExerciseCounts = _appDb.phaseOfCycleDao.getWithMuscleExerciseCountsById(phaseOfCycle.phaseOfCycleId!!)
            .muscleExerciseCounts

        muscleExerciseCounts.forEach { muscleExerciseCount ->
            val neededMuscle = _appDb.muscleDao.getById(muscleExerciseCount.muscleOwnerId)

            for (i in 0..<muscleExerciseCount.exercisesByMuscle) {
                result.add(findSelectedExercise(
                    result,
                    userSelectedExercises,
                    neededMuscle,
                    lastWorkoutWithSelectedExercises
                ))
            }
        }

        return result.toList()
    }

    private suspend fun findSelectedExercise(
        alreadySelectedExercises: List<SelectedExercise>,
        selectedExercises: List<SelectedExercise>,
        neededMuscle: Muscle,
        lastWorkoutWithSelectedExercises: WorkoutWithSelectedExercises?
    ): SelectedExercise {
        val exercise = selectedExercises.firstOrNull { selectedExercise ->
            val usedMuscles = _appDb.exerciseDao.getWithMusclesById(selectedExercise.exerciseOwnerId).muscles

            if (lastWorkoutWithSelectedExercises != null) {
                val matchingMuscle = usedMuscles.find { it.muscleId == neededMuscle.muscleId }
                val matchingExerciseWithPreviousWorkout = lastWorkoutWithSelectedExercises.selectedExercises.find {
                    it.exerciseOwnerId == selectedExercise.exerciseOwnerId
                }
                val alreadySelected = alreadySelectedExercises.contains(selectedExercise)

                matchingMuscle != null && matchingExerciseWithPreviousWorkout == null && !alreadySelected
            }
            else {
                val alreadySelected = alreadySelectedExercises.contains(selectedExercise)

                val matchingMuscle = usedMuscles.find { it.muscleId == neededMuscle.muscleId }
                matchingMuscle != null && !alreadySelected
            }
        }

        if (exercise == null) return selectedExercises[0]
        return exercise
    }

    private fun getCurrentDate(): Date {
        val timestamp = Date().time
        val dateConverter = DateConverter()
        return dateConverter.fromTimestamp(timestamp)
    }
}