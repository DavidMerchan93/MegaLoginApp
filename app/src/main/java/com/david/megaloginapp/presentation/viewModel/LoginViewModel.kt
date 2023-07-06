package com.david.megaloginapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.megaloginapp.domain.useCase.OnLoginUserUseCase
import com.david.megaloginapp.presentation.state.LoginState
import com.david.megaloginapp.presentation.view.common.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val onLoginUserUseCase: OnLoginUserUseCase,
) : ViewModel() {
    var loginState by mutableStateOf(LoginState())
        private set

    fun login(email: String, password: String) {
        loginState = LoginState(isLoading = true)

        if (isValidFields(email, password)) {
            onLoginUserUseCase(email, password).catch {
                loginState = LoginState(errorUserNotFound = LoginState.LoginErrors.USER_NOT_FOUND)
            }.map { user ->
                loginState = LoginState(userLoggedSuccess = user)
            }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
        }
    }

    private fun isValidFields(email: String, password: String): Boolean {
        return when {
            (email.isEmpty() || email.isValidEmail().not()) -> {
                loginState = LoginState(errorInvalidEmail = LoginState.LoginErrors.EMAIL_FORMAT)
                false
            }

            (password.isEmpty() || password.length < 5) -> {
                loginState = LoginState(errorEmptyPassword = LoginState.LoginErrors.EMPTY_PASSWORD)
                false
            }

            else -> true
        }
    }
}
