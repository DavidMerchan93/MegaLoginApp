package com.david.megaloginapp.presentation.viewModel

import com.david.megaloginapp.BaseViewModelTest
import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.error.login.LoginEmailException
import com.david.megaloginapp.domain.error.login.LoginPasswordException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.useCase.OnLoginUseCase
import com.david.megaloginapp.presentation.state.LoginState
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

class LoginViewModelTest : BaseViewModelTest() {

    private val onLoginUseCase: OnLoginUseCase = mockk()

    private lateinit var loginViewModel: LoginViewModel

    override fun setup() {
        super.setup()
        loginViewModel = LoginViewModel(onLoginUseCase)
    }

    override fun tearDown() {
        super.tearDown()
        confirmVerified(onLoginUseCase)
        clearMocks(onLoginUseCase)
    }

    @Test
    fun loginSuccess() = runBlocking {
        val user: User = mockk(relaxed = true)
        val email = "email@email.com"
        val password = "123456"

        // Prepare
        every {
            onLoginUseCase(email, password)
        } returns flowOf(user)

        // Execution
        loginViewModel.login(email, password)

        // Verification
        verify {
            onLoginUseCase(email, password)
            loginViewModel.loginState.copy(isLoading = true, userLoggedSuccess = null)
        }

        confirmVerified(user)
        assertEquals(
            loginViewModel.loginState,
            LoginState(userLoggedSuccess = user),
        )
    }

    @Test
    fun loginEmailException() = runBlocking {
        generalTestException(
            LoginEmailException,
            LoginState(errorInvalidEmail = LoginState.LoginErrors.EMAIL_FORMAT),
        )
    }

    @Test
    fun loginPasswordException() = runBlocking {
        generalTestException(
            LoginPasswordException,
            LoginState(errorEmptyPassword = LoginState.LoginErrors.EMPTY_PASSWORD),
        )
    }

    @Test
    fun userException() = runBlocking {
        generalTestException(
            UserException,
            LoginState(errorUserNotFound = LoginState.LoginErrors.USER_NOT_FOUND),
        )
    }

    private fun generalTestException(
        exception: Throwable,
        registerState: LoginState,
    ) {
        val email = "email@email.com"
        val password = "123456"

        // Preparation
        every {
            onLoginUseCase(email, password)
        } returns flow { throw exception }

        // Execution
        loginViewModel.login(email, password)

        // Verification
        verify {
            onLoginUseCase(email, password)
            loginViewModel.loginState.copy(isLoading = true, userLoggedSuccess = null)
        }

        assertEquals(
            loginViewModel.loginState,
            registerState,
        )
    }
}
