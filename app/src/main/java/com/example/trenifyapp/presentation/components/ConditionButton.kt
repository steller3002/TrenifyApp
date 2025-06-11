package com.example.trenifyapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun ConditionButton(
    modifier: Modifier,
    onClick: () -> Unit,
    enabledCondition: Boolean,
    backgroundColor: Color = Orange,
    textColor: Color = Color.White,
    disabledBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    disabledTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabledCondition,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledTextColor,
        )
    ) {
        Text(text)
    }
}

@Composable
@Preview
private fun ConditionButtonPreview() {
    ConditionButton(
        modifier = Modifier,
        enabledCondition = true,
        onClick = { return@ConditionButton },
        text = "Продолжить"
    )
}