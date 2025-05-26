    package com.example.trenifyapp.presentation.screens

    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
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
    import com.example.trenifyapp.presentation.viewmodels.AccountSelectionViewModel
    import com.example.trenifyapp.ui.theme.Orange
    import androidx.compose.foundation.lazy.items
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Check
    import androidx.compose.material3.CardDefaults
    import androidx.compose.material3.ElevatedCard
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Surface
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

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Выберите аккаунт",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.padding(top = 20.dp, bottom = 24.dp)
                                    .align(Alignment.CenterHorizontally)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(accounts) { user ->
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(72.dp),
                                colors = CardDefaults.elevatedCardColors(
                                    containerColor = if (user.userId == selectedUserId)
                                        Orange.copy(alpha = 0.2f)
                                    else
                                        MaterialTheme.colorScheme.surfaceVariant
                                ),
                                elevation = CardDefaults.elevatedCardElevation(
                                    defaultElevation = 2.dp,
                                    pressedElevation = 8.dp
                                ),
                                onClick = {
                                    if (user.userId == selectedUserId) {
                                        navigateToUserProfileScreen(user.userId!!)
                                    } else {
                                        viewModel.changeSelectedUser(user.userId!!)
                                    }
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = user.username,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = if (user.userId == selectedUserId)
                                            Orange
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (user.userId == selectedUserId) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Выбрано",
                                            tint = Orange,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(96.dp)) // место под кнопку
                }

                Button(
                    onClick = navigateToInitialUserDataScreen,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(start = 24.dp, end = 24.dp, bottom = 60.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "Создать новый аккаунт",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
