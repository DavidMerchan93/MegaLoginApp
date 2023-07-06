package com.david.megaloginapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.error.login.LoginEmailException
import com.david.megaloginapp.domain.error.login.LoginPasswordException
import com.david.megaloginapp.domain.useCase.OnLoginUseCase
import com.david.megaloginapp.presentation.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val onLoginUseCase: OnLoginUseCase,
) : ViewModel() {
    var loginState by mutableStateOf(LoginState())
        private set

    fun login(email: String, password: String) {
        loginState = LoginState(isLoading = true)

        onLoginUseCase(email, password).catch { exception ->
            processException(exception)
        }.map { user ->
            loginState = LoginState(userLoggedSuccess = user)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun processException(exception: Throwable) {
        when (exception) {
            LoginEmailException -> {
                loginState = LoginState(errorInvalidEmail = LoginState.LoginErrors.EMAIL_FORMAT)
            }

            LoginPasswordException -> {
                loginState = LoginState(errorEmptyPassword = LoginState.LoginErrors.EMPTY_PASSWORD)
            }

            UserException -> {
                loginState = LoginState(errorUserNotFound = LoginState.LoginErrors.USER_NOT_FOUND)
            }
        }
    }
}
