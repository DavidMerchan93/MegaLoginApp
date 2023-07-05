package com.david.megaloginapp.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.david.megaloginapp.presentation.theme.MegaLoginAppTheme
import com.david.megaloginapp.presentation.view.screen.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginContent {
                LoginScreen(
                    onLogin = {},
                    onRegister = {},
                    onForgotPassword = {},
                )
            }
        }
    }
}

@Composable
fun LoginContent(content: @Composable () -> Unit) {
    MegaLoginAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            content()
        }
    }
}
