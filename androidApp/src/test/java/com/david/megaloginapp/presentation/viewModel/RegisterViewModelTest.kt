package com.david.megaloginapp.presentation.viewModel

import com.david.megaloginapp.BaseViewModelTest
import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.error.register.EmailException
import com.david.megaloginapp.domain.error.register.FullNameException
import com.david.megaloginapp.domain.error.register.PasswordException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.useCase.OnRegisterUseCase
import com.david.megaloginapp.presentation.state.RegisterState
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

internal class RegisterViewModelTest : BaseViewModelTest() {

    private val onRegisterUseCase: OnRegisterUseCase = mockk()

    private lateinit var registerViewModel: RegisterViewModel

    override fun setup() {
        super.setup()
        registerViewModel = RegisterViewModel(onRegisterUseCase)
    }

    override fun tearDown() {
        super.tearDown()
        confirmVerified(onRegisterUseCase)
        clearMocks(onRegisterUseCase)
    }

    @Test
    fun registerSuccess() = runBlocking {
        val user: User = mockk()

        // Preparation
        every {
            onRegisterUseCase(
                fullName = "user",
                email = "user@user.com",
                password = "123456",
                confirmPassword = "123456",
            )
        } returns flowOf(user)

        // Execution
        registerViewModel.register(
            fullName = "user",
            email = "user@user.com",
            password = "123456",
            confirmPassword = "123456",
        )

        // Verification
        verify {
            onRegisterUseCase(
                fullName = "user",
                email = "user@user.com",
                password = "123456",
                confirmPassword = "123456",
            )
        }

        confirmVerified(user)

        assertEquals(
            registerViewModel.registerState,
            RegisterState(successRegister = user),
        )
    }

    @Test
    fun registerErrorFullName() = runBlocking {
        generalTestException(
            FullNameException,
            RegisterState(errorFullName = RegisterState.Errors.FULL_NAME),
        )
    }

    @Test
    fun registerErrorEmail() = runBlocking {
        generalTestException(
            EmailException,
            RegisterState(errorEmail = RegisterState.Errors.EMAIL),
        )
    }

    @Test
    fun registerErrorPassword() = runBlocking {
        generalTestException(
            PasswordException(isEmpty = true),
            RegisterState(errorPassword = RegisterState.Errors.PASSWORD),
        )
    }

    @Test
    fun registerErrorConfirmPassword() = runBlocking {
        generalTestException(
            PasswordException(isEmpty = false),
            RegisterState(errorRepeatPassword = RegisterState.Errors.REPEAT_PASSWORD),
        )
    }

    @Test
    fun registerErrorUser() = runBlocking {
        generalTestException(
            UserException,
            RegisterState(errorUser = RegisterState.Errors.USER),
        )
    }

    private fun generalTestException(
        exception: Throwable,
        registerState: RegisterState,
    ) {
        // Preparation
        every {
            onRegisterUseCase(
                fullName = "user",
                email = "user@user.com",
                password = "123456",
                confirmPassword = "123456",
            )
        } returns flow { throw exception }

        // Execution
        registerViewModel.register(
            fullName = "user",
            email = "user@user.com",
            password = "123456",
            confirmPassword = "123456",
        )

        // Verification
        verify {
            onRegisterUseCase(
                fullName = "user",
                email = "user@user.com",
                password = "123456",
                confirmPassword = "123456",
            )
            registerState.copy(
                isLoading = true,
                successRegister = null,
            )
        }
        assertEquals(
            registerViewModel.registerState,
            registerState,
        )
    }
}
