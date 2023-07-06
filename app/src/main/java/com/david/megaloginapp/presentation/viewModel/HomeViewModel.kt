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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val onLogoutUseCase: OnLogoutUseCase,
    stateHandle: SavedStateHandle,
) : ViewModel() {

    var userHomeState by mutableStateOf(UserHomeState())
        private set

    init {
        stateHandle.get<Int>(NavigationArgs.USER_ID.key)?.let { id ->
            getUserData(id)
        }
    }

    fun getUserData(id: Int) {
        userHomeState = UserHomeState(isLoading = true)
        getUserDataUseCase(id).catch {
            userHomeState = UserHomeState(userData = null)
        }.map { user ->
            userHomeState = UserHomeState(userData = user)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun logout() {
        userHomeState = UserHomeState(isLoading = true)
        onLogoutUseCase().catch {
            userHomeState = UserHomeState(isLogout = false)
        }.map {
            userHomeState = UserHomeState(isLogout = true)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
}
