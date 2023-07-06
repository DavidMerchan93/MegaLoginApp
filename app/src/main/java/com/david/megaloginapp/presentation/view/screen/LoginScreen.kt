package com.david.megaloginapp.presentation.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.david.megaloginapp.R
import com.david.megaloginapp.presentation.view.common.InputType
import com.david.megaloginapp.presentation.view.common.SimpleButton
import com.david.megaloginapp.presentation.view.common.TextInput
import com.david.megaloginapp.presentation.view.common.ViewAnimation
import com.david.megaloginapp.presentation.view.common.buildExoplayer
import com.david.megaloginapp.presentation.view.common.buildPlayerView
import com.david.megaloginapp.presentation.view.common.getVideoUri
import com.david.megaloginapp.presentation.viewModel.LoginViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@Composable
fun LoginScreen(
    onLogin: (id: Int) -> Unit,
    onForgotPassword: () -> Unit,
    onRegister: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val loginState = loginViewModel.loginState

    val context = LocalContext.current
    val exoplayer = remember { context.buildExoplayer(context.getVideoUri()) }

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    if (loginState.userLoggedSuccess != null) {
        onLogin(loginState.userLoggedSuccess.id)
    }

    DisposableEffect(
        AndroidView(factory = {
            context.buildPlayerView(exoplayer)
        }, modifier = Modifier.fillMaxSize()),
    ) {
        onDispose {
            exoplayer.release()
        }
    }

    ProvideWindowInsets {
        Column(
            modifier = Modifier
                .navigationBarsWithImePadding()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dimen_16dp),
                    vertical = dimensionResource(id = R.dimen.dimen_40dp),
                ).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(id = R.dimen.dimen_8dp),
                alignment = Alignment.Bottom,
            ),
        ) {
            ViewAnimation(
                animationFile = R.raw.cute_astronaut,
                width = R.dimen.dimen_100dp,
                height = R.dimen.dimen_100dp,
            )
            Spacer(
                modifier = Modifier.fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dimen_40dp)),
            )
            TextInput(
                value = emailValue,
                inputType = InputType.Email,
                messageError = loginState.errorInvalidEmail?.message,
                onChangeValue = { emailValue = it },
            )
            TextInput(
                value = passwordValue,
                inputType = InputType.Password,
                messageError = loginState.errorEmptyPassword?.message,
                onChangeValue = { passwordValue = it },
            )
            SimpleButton(
                label = stringResource(id = R.string.login_button_login),
                onClick = {
                    loginViewModel.login(emailValue, passwordValue)
                },
            )
            Text(
                modifier = Modifier.clickable { onForgotPassword() },
                text = stringResource(id = R.string.login_button_forgot_password),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Divider(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.dimen_56dp)),
                thickness = 2.dp,
                color = Color.White.copy(alpha = 0.6f),
            )
            Text(
                modifier = Modifier.clickable { onRegister() },
                text = stringResource(id = R.string.login_button_register),
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}
