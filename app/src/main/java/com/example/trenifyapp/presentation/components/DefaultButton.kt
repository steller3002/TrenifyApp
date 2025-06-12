package com.example.trenifyapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = Orange,
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(56.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
        )
    ) {
        Text(text)
    }
}

@Composable
@Preview
private fun DefaultButtonPreview() {
    DefaultButton(
        text = "Preview",
    ) { }
}