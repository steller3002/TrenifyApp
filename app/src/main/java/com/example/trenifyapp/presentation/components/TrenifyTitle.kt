package com.example.trenifyapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun TrenifyTitle(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 32.sp,
    color: Color = Orange,
) {
    Text(
        text = "Trenify",
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = modifier
            .padding(bottom = 32.dp)
    )
}

@Composable
@Preview
private fun TrenifyTitlePreview() {
    TrenifyTitle()
}