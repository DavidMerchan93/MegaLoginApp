package com.david.megaloginapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.megaloginapp.domain.useCase.OnStartAppUseCase
import com.david.megaloginapp.presentation.state.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val onStartAppUseCase: OnStartAppUseCase,
) : ViewModel() {

    var splashScreenState by mutableStateOf(SplashScreenState(isLoading = true))
        private set

    fun checkIfUser() {
        onStartAppUseCase().catch {
            splashScreenState = SplashScreenState(isUserLogged = false)
        }.map { user ->
            splashScreenState = SplashScreenState(isUserLogged = true, user = user)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
}
