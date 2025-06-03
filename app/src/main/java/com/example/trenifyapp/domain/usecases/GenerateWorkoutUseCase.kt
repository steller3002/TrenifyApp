package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.DateConverter
import com.example.trenifyapp.data.entities.Muscle
import com.example.trenifyapp.data.entities.MuscleExerciseCount
import com.example.trenifyapp.data.entities.PhaseOfCycle
import com.example.trenifyapp.data.entities.SelectedExercise
import com.example.trenifyapp.data.entities.Workout
import com.example.trenifyapp.data.entities.WorkoutExercise
import java.util.Date
import javax.inject.Inject

class GenerateWorkoutUseCase @Inject constructor(
    private val _appDb: AppDb
) {
    suspend operator fun invoke(userId: Int): Workout {
        val userWithSelectedExercises = _appDb.userDao.getSelectedExercises(userId)
        val workoutPlanWithPhases = _appDb.workoutPlanDao.getPhasesIds(userWithSelectedExercises.user.workoutPlanOwnerId)
        val previousWorkoutWithWorkoutExercises = _appDb.workoutDao.getLastWorkoutWithWorkoutExercises(
            userId = userId,
            phaseOfCycleId = userWithSelectedExercises.user.phaseOfCycleOwnerId
        )

        val user = userWithSelectedExercises.user
        val selectedExercises = userWithSelectedExercises.selectedExercises
        val workoutPlan = workoutPlanWithPhases.workoutPlan
        val phases = workoutPlanWithPhases.phasesOfCycle
        val previousExercisesOrNull = previousWorkoutWithWorkoutExercises?.workoutExercises

        val newPhaseOfCycle = calcNewPhaseOfCycle(
            currentPhaseId = user.phaseOfCycleOwnerId,
            phasesOfCycle = phases
        )

        val newExercises = chooseNewExercises(
            selectedExercises = selectedExercises,
            previousWorkoutExercises = previousExercisesOrNull,
            _appDb.phaseOfCycleDao.getWithMuscleExerciseCountsById(newPhaseOfCycle.phaseOfCycleId!!)
                .muscleExerciseCounts
        )

        val workout = Workout(
            workoutId = null,
            date = getCurrentDate(),
            userOwnerId = user.userId!!,
            phaseOfCycleOwnerId = newPhaseOfCycle.phaseOfCycleId
        )
        val newWorkoutId = _appDb.workoutDao.create(workout)


        newExercises.forEach { selectedExercise ->
            val workoutExercise = WorkoutExercise(
                workoutExerciseId = null,
                selectedExerciseOwnerId = selectedExercise.selectedExerciseId!!,
                workoutOwnerId = newWorkoutId.toInt()
            )

            _appDb.workoutExerciseDao.create(workoutExercise)
        }

        user.phaseOfCycleOwnerId = newPhaseOfCycle.phaseOfCycleId
        _appDb.userDao.update(user)

        return _appDb.workoutDao.getById(newWorkoutId.toInt())
    }

    private suspend fun chooseNewExercises(
        selectedExercises: List<SelectedExercise>,
        previousWorkoutExercises: List<WorkoutExercise>?,
        muscleExerciseCounts: List<MuscleExerciseCount>
    ): List<SelectedExercise> {

        val result = mutableListOf<SelectedExercise>()

        muscleExerciseCounts.forEach { muscleExerciseCount ->
            for (i in 0..<muscleExerciseCount.exercisesByMuscle) {
                val exercise = findRightExercise(
                    targetMuscle = _appDb.muscleDao.getById(muscleExerciseCount.muscleOwnerId),
                    selectedExercises = selectedExercises,
                    previousWorkoutExercises = previousWorkoutExercises,
                    result
                )

                result.add(exercise)
            }
        }

        return result
    }

    private suspend fun findRightExercise(
        targetMuscle: Muscle,
        selectedExercises: List<SelectedExercise>,
        previousWorkoutExercises: List<WorkoutExercise>?,
        alreadyAddedExercises: List<SelectedExercise>
    ): SelectedExercise {

        selectedExercises.forEach { selectedExercise ->
            val exercise = _appDb.exerciseDao.getById(selectedExercise.exerciseOwnerId)
            val exerciseTargetMuscle = _appDb.muscleDao.getById(exercise.muscleOwnerId)

            if (exerciseTargetMuscle == targetMuscle && !alreadyAddedExercises.contains(selectedExercise))
                return selectedExercise
        }

        return selectedExercises.random()
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

    private fun getCurrentDate(): Date {
        val timestamp = Date().time
        val dateConverter = DateConverter()
        return dateConverter.fromTimestamp(timestamp)
    }
}