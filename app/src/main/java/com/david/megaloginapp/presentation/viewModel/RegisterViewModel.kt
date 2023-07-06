package com.david.megaloginapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.megaloginapp.domain.useCase.OnRegisterUseCase
import com.david.megaloginapp.presentation.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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
    ) {
        registerState = RegisterState(isLoading = true)
        onRegisterUseCase(fullName, email, password).catch {
            registerState = RegisterState(errorUser = RegisterState.Errors.USER)
        }.map { user ->
            registerState = RegisterState(successRegister = user)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
}
