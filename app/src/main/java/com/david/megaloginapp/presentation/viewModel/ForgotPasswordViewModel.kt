package com.david.megaloginapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotConfirmPasswordException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotEmailException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotPasswordException
import com.david.megaloginapp.domain.useCase.OnChangePasswordUseCase
import com.david.megaloginapp.presentation.state.ForgotPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val onChangePasswordUseCase: OnChangePasswordUseCase,
) : ViewModel() {

    var changePasswordState by mutableStateOf(ForgotPasswordState())
        private set

    fun changePassword(email: String, password: String, confirmPassword: String) {
        changePasswordState = ForgotPasswordState(isLoading = true)
        onChangePasswordUseCase(email, password, confirmPassword).catch { exception ->
            processErrors(exception)
        }.map { result ->
            if (result) {
                changePasswordState = ForgotPasswordState(isSuccessChange = true)
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun processErrors(exception: Throwable) {
        when (exception) {
            ForgotEmailException -> {
                changePasswordState =
                    ForgotPasswordState(errorEmail = ForgotPasswordState.Error.EMPTY_EMAIL)
            }

            ForgotPasswordException -> {
                changePasswordState =
                    ForgotPasswordState(errorPassword = ForgotPasswordState.Error.EMPTY_PASSWORD)
            }

            ForgotConfirmPasswordException -> {
                changePasswordState =
                    ForgotPasswordState(errorConfirmPassword = ForgotPasswordState.Error.CONFIRM_PASSWORD)
            }

            UserException -> {
                changePasswordState =
                    ForgotPasswordState(errorUser = ForgotPasswordState.Error.USER_NOT_FOUND)
            }
        }
    }
}
