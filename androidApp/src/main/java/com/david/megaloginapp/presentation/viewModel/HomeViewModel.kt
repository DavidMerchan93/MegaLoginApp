package com.david.megaloginapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.megaloginapp.domain.useCase.GetUserDataUseCase
import com.david.megaloginapp.domain.useCase.OnLogoutUseCase
import com.david.megaloginapp.presentation.state.UserHomeState
import com.david.megaloginapp.presentation.view.navigation.NavigationArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val onLogoutUseCase: OnLogoutUseCase,
    stateHandle: SavedStateHandle,
) : ViewModel() {

    var userHomeState by mutableStateOf(UserHomeState())
        private set

    var userLogoutState by mutableStateOf(false)
        private set

    init {
        stateHandle.get<Int>(NavigationArgs.USER_ID.key)?.let { id ->
            getUserData(id)
        }
    }

    private fun getUserData(id: Int) {
        getUserDataUseCase(id).catch {
            userHomeState = UserHomeState(userData = null)
        }.map { user ->
            userHomeState = UserHomeState(userData = user)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun logout() {
        onLogoutUseCase().catch {
            userLogoutState = false
        }.map {
            userLogoutState = true
        }.onStart {
            userHomeState = userHomeState.copy(
                isLoading = true,
                userData = null,
            )
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
}
