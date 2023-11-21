package com.david.megaloginapp.presentation.viewModel

import com.david.megaloginapp.BaseViewModelTest
import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.useCase.OnStartAppUseCase
import com.david.megaloginapp.presentation.state.SplashScreenState
import io.mockk.clearMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SplashScreenViewModelTest : BaseViewModelTest() {

    private val onStartAppUseCase: OnStartAppUseCase = mockk()

    private lateinit var splashScreenViewModel: SplashScreenViewModel

    override fun setup() {
        super.setup()

        splashScreenViewModel = SplashScreenViewModel(
            onStartAppUseCase = onStartAppUseCase,
        )
    }

    override fun tearDown() {
        super.tearDown()
        confirmVerified(onStartAppUseCase)
        clearMocks(onStartAppUseCase)
    }

    @Test
    fun checkIfUserLogged() = runBlocking {
        val user: User = mockk()

        // Preparation
        every { onStartAppUseCase() } returns flowOf(user)

        // Execution
        splashScreenViewModel.checkIfUser()

        // Verification
        verify {
            onStartAppUseCase()
        }

        confirmVerified(user)

        assertEquals(
            splashScreenViewModel.splashScreenState,
            SplashScreenState(
                isLoading = false,
                user = user,
                isUserLogged = true,
            ),
        )
    }

    @Test
    fun checkIfUserNotLogged() = runBlocking {
        // Preparation
        every { onStartAppUseCase() } returns flow { throw UserException }

        // Execution
        splashScreenViewModel.checkIfUser()

        // Verification
        verify {
            onStartAppUseCase()
        }
        assertEquals(
            splashScreenViewModel.splashScreenState,
            SplashScreenState(
                isLoading = false,
                isUserLogged = false,
            ),
        )
    }
}
