package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AccountSelectionScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Выберите аккаунт",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Здесь должен быть список доступных аккаунтов
        // Для примера - просто кнопка
        Button(
            onClick = {
                // При выборе аккаунта переходим на основной экран
                navController.navigate("main") {
                    // Очищаем back stack, чтобы нельзя было вернуться назад
                    popUpTo("accountSelection") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Выбрать существующий аккаунт")
        }

        // Кнопка создания нового аккаунта
        Button(
            onClick = {
                navController.navigate("signUp")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Создать новый аккаунт")
        }
    }
}