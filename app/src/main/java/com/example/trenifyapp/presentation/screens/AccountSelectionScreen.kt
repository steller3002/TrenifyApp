package com.example.trenifyapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trenifyapp.presentation.viewmodels.AccountSelectionViewModel
import com.example.trenifyapp.ui.theme.Orange
import androidx.compose.foundation.lazy.items

@Composable
fun AccountSelectionScreen(
    navController: NavController,
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            items(users) { user ->
//                AccountItem(
//                    user = user,
//                    isSelected = selectedUser?.id == user.id,
//                    onSelect = { viewModel.selectUser(user) }
//                )

        }

        Button(
            onClick = {
                navController.navigate("signUp")
            },
            modifier = Modifier
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange,
                contentColor = Color.White
            )
        ) {
            Text("Создать")
        }
    }
}