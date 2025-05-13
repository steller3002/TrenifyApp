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
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.trenifyapp.presentation.viewmodels.AccountSelectionViewModel
    import com.example.trenifyapp.ui.theme.Orange
    import androidx.compose.foundation.lazy.items
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue

    @Composable
    fun AccountSelectionScreen(
        navigateToUserProfileScreen: (Int) -> Unit,
        navigateToInitialUserDataScreen: () -> Unit,
        viewModel: AccountSelectionViewModel
    ) {
        val accounts by viewModel.accounts.collectAsState(initial = emptyList())
        val selectedUserId by viewModel.selectedUserId

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
                items(accounts) { user ->
                    Button(
                        onClick = {
                            if (user.id == selectedUserId) {
                                navigateToUserProfileScreen(user.id!!)
                            } else {
                                viewModel.changeSelectedUser(user.id!!)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (user.id == selectedUserId) Orange else Color.LightGray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(user.username)
                    }
                }
            }

            Button(
                onClick = {
                    navigateToInitialUserDataScreen()
                },
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange,
                    contentColor = Color.White
                )
            ) {
                Text("Создать")
            }
        }
    }