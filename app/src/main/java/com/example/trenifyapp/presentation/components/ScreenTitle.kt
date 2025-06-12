package com.example.trenifyapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
    )
}

@Composable
@Preview
fun ScreenTitlePreview() {
    ScreenTitle(text = "Тест")
}