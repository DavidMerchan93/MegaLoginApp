package com.david.megaloginapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.error.register.EmailException
import com.david.megaloginapp.domain.error.register.FullNameException
import com.david.megaloginapp.domain.error.register.PasswordException
import com.david.megaloginapp.domain.useCase.OnRegisterUseCase
import com.david.megaloginapp.presentation.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val onRegisterUseCase: OnRegisterUseCase,
) : ViewModel() {

    var registerState by mutableStateOf(RegisterState())
        private set

    fun register(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        onRegisterUseCase(fullName, email, password, confirmPassword).catch { exception ->
            processErrors(exception)
        }.map { user ->
            registerState = registerState.copy(
                isLoading = false,
                successRegister = user,
            )
        }.onStart {
            registerState = registerState.copy(isLoading = true, successRegister = null)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun processErrors(exception: Throwable) {
        when (exception) {
            FullNameException -> {
                registerState = RegisterState(errorFullName = RegisterState.Errors.FULL_NAME)
            }

            EmailException -> {
                registerState = RegisterState(errorEmail = RegisterState.Errors.EMAIL)
            }

            is PasswordException -> {
                registerState = if (exception.isEmpty) {
                    RegisterState(errorPassword = RegisterState.Errors.PASSWORD)
                } else {
                    RegisterState(errorRepeatPassword = RegisterState.Errors.REPEAT_PASSWORD)
                }
            }

            UserException -> {
                registerState = RegisterState(errorUser = RegisterState.Errors.USER)
            }
        }
    }
}
