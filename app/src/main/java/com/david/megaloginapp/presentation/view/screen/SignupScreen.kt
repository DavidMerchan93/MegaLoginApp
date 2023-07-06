package com.david.megaloginapp.presentation.view.screen

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
import com.david.megaloginapp.presentation.state.RegisterState
import com.david.megaloginapp.presentation.view.common.InputType
import com.david.megaloginapp.presentation.view.common.ModalBottomDialog
import com.david.megaloginapp.presentation.view.common.SimpleButton
import com.david.megaloginapp.presentation.view.common.TextInput
import com.david.megaloginapp.presentation.viewModel.RegisterViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onContinueToHome: (userId: Int) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) { padding ->
        RegisterView(modifier = Modifier.padding(padding), onContinueToHome = onContinueToHome)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = hiltViewModel(),
    onContinueToHome: (userId: Int) -> Unit,
) {
    val registerState = registerViewModel.registerState

    ModalBottomDialog(
        iconAnimated = if (registerState.successRegister != null) {
            R.raw.congrats
        } else {
            null
        },
        title = if (registerState.errorUser == RegisterState.Errors.USER) {
            R.string.register_error_title_error
        } else {
            null
        },
        detail = if (registerState.errorUser == RegisterState.Errors.USER) {
            R.string.register_error_register_user
        } else {
            R.string.register_message_success_register_user
        },
        buttonTitle = R.string.register_button_continue,
        isError = registerState.errorUser == RegisterState.Errors.USER,
        buttonAction = {
            if (registerState.successRegister != null) {
                onContinueToHome(registerState.successRegister.id)
            }
        },
    ) { coroutineScope, modalSheetState ->
        LaunchedEffect(registerState.successRegister, registerState.errorUser) {
            coroutineScope.launch {
                if (modalSheetState.isVisible) {
                    modalSheetState.hide()
                } else if (registerState.errorUser == RegisterState.Errors.USER || registerState.successRegister != null) {
                    modalSheetState.show()
                }
            }
        }

        RegisterDetail(
            modifier = modifier,
            registerState = registerState,
            onRegister = { fullName, email, password, confirmPassword ->
                registerViewModel.register(fullName, email, password, confirmPassword)
            },
        )
    }
}

@Composable
fun RegisterDetail(
    modifier: Modifier = Modifier,
    registerState: RegisterState,
    onRegister: (
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) -> Unit,
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
            text = stringResource(id = R.string.register_title_welcome),
            fontSize = 26.sp,
        )
        TextInput(
            value = fullName,
            inputType = InputType.FullName,
            messageError = registerState.errorFullName?.message,
            isEnabled = registerState.isLoading.not(),
            onChangeValue = { fullName = it },
        )
        TextInput(
            value = email,
            inputType = InputType.Email(true),
            messageError = registerState.errorEmail?.message,
            isEnabled = registerState.isLoading.not(),
            onChangeValue = { email = it },
        )
        TextInput(
            value = password,
            inputType = InputType.Password(true),
            messageError = registerState.errorPassword?.message,
            isEnabled = registerState.isLoading.not(),
            onChangeValue = { password = it },
        )
        TextInput(
            value = confirmPassword,
            inputType = InputType.ConfirmPassword,
            messageError = registerState.errorRepeatPassword?.message,
            isEnabled = registerState.isLoading.not(),
            onChangeValue = { confirmPassword = it },
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_36dp)))
        Text(text = stringResource(id = R.string.register_message_required_field))
        SimpleButton(
            label = stringResource(id = R.string.register_button_register),
            isLoading = registerState.isLoading,
            onClick = {
                onRegister(fullName, email, password, confirmPassword)
            },
        )
    }
}
