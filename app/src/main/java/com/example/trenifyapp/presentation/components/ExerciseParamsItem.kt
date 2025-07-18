package com.example.trenifyapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun ExerciseParamsItem(
    exerciseName: String,
    weight: String,
    onWeightChanged: (String) -> Unit,
    weightErrorValue: String?,
    sets: String,
    onSetsChange: (String) -> Unit,
    setsErrorValue: String?,
    isSelected: Boolean,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Orange else Color.LightGray
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = exerciseName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            ValidatedTextField(
                modifier = Modifier.padding(top = 10.dp),
                value = weight,
                onValueChanged = onWeightChanged,
                label = "Рабочий вес",
                errorValue = weightErrorValue,
                keyboardType = KeyboardType.Number,
            )

            ValidatedTextField(
                value = sets,
                onValueChanged = onSetsChange,
                label = "Количество подходов",
                errorValue = setsErrorValue,
                keyboardType = KeyboardType.Number
            )
        }
    }
}

@Preview
@Composable
private fun ExerciseParamsItemPreview() {
    ExerciseParamsItem(
        exerciseName = "Жим лёжа",
        weight = "20",
        sets = "4",
        onSetsChange = {},
        weightErrorValue = null,
        setsErrorValue = null,
        onWeightChanged = {},
        isSelected = false,
    )
}