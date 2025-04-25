package com.example.trenifyapp

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.trenifyapp.presentation.navigation.MainScreen
import com.example.trenifyapp.ui.theme.TrenifyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrenifyAppTheme {
                MainScreen()
            }
        }
    }
}