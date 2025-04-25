package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trenifyapp.ui.theme.Orange

@Composable
fun SignUpScreen(
    navController: NavController
) {
    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Заголовок "Trenify"
        Text(
            text = "Trenify",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Orange,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Подзаголовок "Создание аккаунта"
        Text(
            text = "Создание аккаунта",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Поле ввода имени пользователя
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Имя пользователя") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Кнопка "Продолжить"
        Button(
            onClick = {
                navController.navigate("accountSelection")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Продолжить")
        }
    }
}