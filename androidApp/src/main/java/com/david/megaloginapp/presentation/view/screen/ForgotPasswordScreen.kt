package com.david.megaloginapp.presentation.view.screen

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.david.megaloginapp.R
import com.david.megaloginapp.presentation.state.ForgotPasswordState
import com.david.megaloginapp.presentation.view.common.InputType
import com.david.megaloginapp.presentation.view.common.ModalBottomDialog
import com.david.megaloginapp.presentation.view.common.SimpleButton
import com.david.megaloginapp.presentation.view.common.TextInput
import com.david.megaloginapp.presentation.viewModel.ForgotPasswordViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen() {
    val backDispatcher = requireNotNull(LocalOnBackPressedDispatcherOwner.current)
        .onBackPressedDispatcher

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { backDispatcher.onBackPressed() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) { padding ->
        ForgotPasswordView(
            modifier = Modifier.padding(padding),
            onChangePasswordSuccess = { backDispatcher.onBackPressed() },
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ForgotPasswordView(
    modifier: Modifier = Modifier,
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel(),
    onChangePasswordSuccess: () -> Unit,
) {
    val changePasswordState = forgotPasswordViewModel.changePasswordState

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    ModalBottomDialog(
        iconAnimated = getIconModalForgotPassword(changePasswordState),
        title = getTitleModalForgotPassword(changePasswordState),
        detail = getDetailModalForgotPassword(changePasswordState),
        buttonTitle = R.string.forgot_password_button_continue,
        isError = changePasswordState.errorUser == ForgotPasswordState.Error.USER_NOT_FOUND,
        buttonAction = {
            if (changePasswordState.isSuccessChange) {
                onChangePasswordSuccess()
            }
        },
    ) { coroutineScope, modalSheetState ->

        LaunchedEffect(changePasswordState.isSuccessChange, changePasswordState.errorUser) {
            coroutineScope.launch {
                if (modalSheetState.isVisible) {
                    modalSheetState.hide()
                } else if (changePasswordState.errorUser == ForgotPasswordState.Error.USER_NOT_FOUND || changePasswordState.isSuccessChange) {
                    modalSheetState.show()
                }
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.dimen_16dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(id = R.dimen.dimen_16dp),
                alignment = Alignment.CenterVertically,
            ),
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.dimen_40dp)),
                text = stringResource(id = R.string.forgot_password_title_screen),
                fontSize = 26.sp,
            )
            TextInput(
                value = email,
                inputType = InputType.Email(isRegister = true),
                messageError = changePasswordState.errorEmail?.message,
                isEnabled = changePasswordState.isLoading.not(),
                onChangeValue = { email = it },
            )
            TextInput(
                value = password,
                inputType = InputType.Password(isRegister = false),
                messageError = changePasswordState.errorPassword?.message,
                isEnabled = changePasswordState.isLoading.not(),
                onChangeValue = { password = it },
            )
            TextInput(
                value = confirmPassword,
                inputType = InputType.ConfirmPassword,
                messageError = changePasswordState.errorConfirmPassword?.message,
                isEnabled = changePasswordState.isLoading.not(),
                onChangeValue = { confirmPassword = it },
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_36dp)))
            SimpleButton(
                label = stringResource(id = R.string.forgot_password_button_change_password),
                isLoading = changePasswordState.isLoading,
                onClick = {
                    forgotPasswordViewModel.changePassword(email, password, confirmPassword)
                },
            )
        }
    }
}

private fun getIconModalForgotPassword(changePasswordState: ForgotPasswordState): Int? {
    return if (changePasswordState.isSuccessChange) {
        R.raw.congrats
    } else {
        null
    }
}

private fun getTitleModalForgotPassword(changePasswordState: ForgotPasswordState): Int? {
    return if (changePasswordState.errorUser == ForgotPasswordState.Error.USER_NOT_FOUND) {
        R.string.forgot_password_error_title_error
    } else {
        null
    }
}

private fun getDetailModalForgotPassword(changePasswordState: ForgotPasswordState): Int {
    return if (changePasswordState.errorUser == ForgotPasswordState.Error.USER_NOT_FOUND) {
        R.string.forgot_password_error_change_password
    } else {
        R.string.forgot_password_message_success_change_password
    }
}
