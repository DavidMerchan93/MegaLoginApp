package com.david.megaloginapp.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import com.david.megaloginapp.BaseViewModelTest
import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.useCase.GetUserDataUseCase
import com.david.megaloginapp.domain.useCase.OnLogoutUseCase
import com.david.megaloginapp.presentation.state.UserHomeState
import com.david.megaloginapp.presentation.view.navigation.NavigationArgs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class HomeViewModelTest : BaseViewModelTest() {

    private val getUserDataUseCase: GetUserDataUseCase = mockk()
    private val onLogoutUseCase: OnLogoutUseCase = mockk()
    private val stateHandle: SavedStateHandle = mockk()
    private val user: User = mockk()

    private lateinit var homeViewModel: HomeViewModel

    override fun setup() {
        super.setup()
        every {
            stateHandle.get<Int>(NavigationArgs.USER_ID.key)
        } returns 1
    }

    override fun tearDown() {
        super.tearDown()
        confirmVerified(
            getUserDataUseCase,
            onLogoutUseCase,
            stateHandle,
            user,
        )
    }

    private fun prepareViewModel() {
        homeViewModel = HomeViewModel(
            getUserDataUseCase,
            onLogoutUseCase,
            stateHandle,
        )
    }

    @Test
    fun getUserDataSuccess() = runBlocking {
        // Preparation
        every {
            getUserDataUseCase(1)
        } returns flowOf(user)

        // Execution
        prepareViewModel()

        // Verification
        verify {
            getUserDataUseCase(1)
        }

        assertEquals(
            homeViewModel.userHomeState,
            UserHomeState(userData = user),
        )
    }

    @Test
    fun getUserDataError() = runBlocking {
        // Preparation
        every {
            getUserDataUseCase(1)
        } returns flow { UserException }

        // Execution
        prepareViewModel()

        // Verification
        verify {
            getUserDataUseCase(1)
        }

        assertEquals(
            homeViewModel.userHomeState,
            UserHomeState(userData = null),
        )
    }

    @Test
    fun logoutSuccess() = runBlocking {
        // Preparation
        every {
            getUserDataUseCase(1)
        } returns flowOf(user)

        every {
            onLogoutUseCase()
        } returns flowOf(true)

        // Execution
        prepareViewModel()
        homeViewModel.logout()

        // Verification
        verify {
            onLogoutUseCase()
            homeViewModel.userHomeState.copy(isLoading = true, userData = null)
        }

        assertTrue(homeViewModel.userLogoutState)
    }

    @Test
    fun logoutError() = runBlocking {
        // Preparation
        every {
            getUserDataUseCase(1)
        } returns flowOf(user)

        every {
            onLogoutUseCase()
        } returns flow { throw UserException }

        // Execution
        prepareViewModel()
        homeViewModel.logout()

        // Verification
        verify {
            onLogoutUseCase()
            homeViewModel.userHomeState.copy(isLoading = true, userData = null)
        }

        assertFalse(homeViewModel.userLogoutState)
    }
}
