package com.example.trenifyapp.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ValidatedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    errorValue: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        label = { Text(label) },
        isError = errorValue != "",
        supportingText = {
            if (errorValue != "") {
                Text(errorValue)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
@Preview
private fun ValidatedTextFieldPreview() {
    ValidatedTextField(
        value = "Тест",
        onValueChanged = { updateText(value = "test") },
        label = "Тестовый textField",
        errorValue = "Тестовая ошибка"
    )
}

private fun updateText(value: String) {
}