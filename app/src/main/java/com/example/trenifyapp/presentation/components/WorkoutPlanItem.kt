package com.example.trenifyapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trenifyapp.data.entities.WorkoutPlan
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun WorkoutPlanItem(
    plan: WorkoutPlan,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect()
            },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Orange else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = plan.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
            )
            if (plan.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = plan.description,
                    fontSize = 14.sp,
                    color = if (isSelected) Color.White.copy(0.8f)
                    else MaterialTheme.colorScheme.onSurface.copy(0.8f)
                )
            }
        }
    }
}

@Composable
@Preview
private fun WorkoutPlanItemPreview() {
    val workoutPlan = WorkoutPlan(
        workoutPlanId = null,
        name = "План тренировки",
        description = "Описание плана тренировки в несколько слов",
        daysPerCycle = 5
    )

    WorkoutPlanItem(
        plan = workoutPlan,
        isSelected = true,
    ) {}
}