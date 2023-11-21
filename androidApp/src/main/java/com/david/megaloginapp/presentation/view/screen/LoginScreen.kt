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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import com.david.megaloginapp.presentation.state.LoginState
import com.david.megaloginapp.presentation.utils.buildExoplayer
import com.david.megaloginapp.presentation.utils.buildPlayerView
import com.david.megaloginapp.presentation.utils.getVideoUri
import com.david.megaloginapp.presentation.view.common.InputType
import com.david.megaloginapp.presentation.view.common.ModalBottomDialog
import com.david.megaloginapp.presentation.view.common.SimpleButton
import com.david.megaloginapp.presentation.view.common.TextInput
import com.david.megaloginapp.presentation.view.common.ViewAnimation
import com.david.megaloginapp.presentation.viewModel.LoginViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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
        ModalBottomDialog(
            iconAnimated = getIconModalLogin(loginState),
            title = getTitleModalLogin(loginState),
            detail = getDetailModalLogin(loginState),
            buttonTitle = R.string.login_button_continue,
            isError = loginState.errorUserNotFound == LoginState.LoginErrors.USER_NOT_FOUND,
            buttonAction = {
                if (loginState.userLoggedSuccess != null) {
                    onLogin(loginState.userLoggedSuccess.id)
                }
            },
        ) { coroutineScope, modalSheetState ->
            LaunchedEffect(loginState.userLoggedSuccess, loginState.errorUserNotFound) {
                coroutineScope.launch {
                    if (modalSheetState.isVisible) {
                        modalSheetState.hide()
                    } else if (loginState.userLoggedSuccess != null || loginState.errorUserNotFound == LoginState.LoginErrors.USER_NOT_FOUND) {
                        modalSheetState.show()
                    }
                }
            }

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
                    inputType = InputType.Email(),
                    messageError = loginState.errorInvalidEmail?.message,
                    isEnabled = loginState.isLoading.not(),
                    onChangeValue = { emailValue = it },
                )
                TextInput(
                    value = passwordValue,
                    inputType = InputType.Password(),
                    messageError = loginState.errorEmptyPassword?.message,
                    isEnabled = loginState.isLoading.not(),
                    onChangeValue = { passwordValue = it },
                )
                SimpleButton(
                    label = stringResource(id = R.string.login_button_login),
                    isLoading = loginState.isLoading,
                    onClick = {
                        loginViewModel.login(emailValue, passwordValue)
                    },
                )
                Text(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.dimen_16dp))
                        .clickable { onForgotPassword() },
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
}

private fun getIconModalLogin(loginState: LoginState): Int? {
    return if (loginState.userLoggedSuccess != null) {
        R.raw.congrats
    } else {
        null
    }
}

private fun getTitleModalLogin(loginState: LoginState): Int {
    return if (loginState.userLoggedSuccess != null) {
        R.string.login_title_success_login
    } else {
        R.string.login_error_title_user_not_found
    }
}

private fun getDetailModalLogin(loginState: LoginState): Int {
    return if (loginState.userLoggedSuccess != null) {
        R.string.login_message_success_login
    } else {
        R.string.login_error_message_user_not_found
    }
}
