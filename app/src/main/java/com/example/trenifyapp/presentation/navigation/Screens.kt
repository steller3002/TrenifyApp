package com.example.trenifyapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Journal() {
    Text(
        modifier = Modifier.fillMaxSize().wrapContentHeight(),
        text = "Journal",
        textAlign = TextAlign.Center
    )
}

@Composable
fun Workout() {
    Text(
        modifier = Modifier.fillMaxSize().wrapContentHeight(),
        text = "Workout",
        textAlign = TextAlign.Center
    )
}

@Composable
fun Account() {
    Text(
        modifier = Modifier.fillMaxSize().wrapContentHeight(),
        text = "Account",
        textAlign = TextAlign.Center
    )
}