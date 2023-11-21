package com.david.megaloginapp.presentation.viewModel

import com.david.megaloginapp.BaseViewModelTest
import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotConfirmPasswordException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotEmailException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotPasswordException
import com.david.megaloginapp.domain.useCase.OnChangePasswordUseCase
import com.david.megaloginapp.presentation.state.ForgotPasswordState
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class ForgotPasswordViewModelTest : BaseViewModelTest() {

    private val onChangePasswordUseCase: OnChangePasswordUseCase = mockk()

    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun setup() {
        super.setup()
        forgotPasswordViewModel = ForgotPasswordViewModel(onChangePasswordUseCase)
    }

    override fun tearDown() {
        super.tearDown()
        confirmVerified(onChangePasswordUseCase)
    }

    @Test
    fun changePasswordSuccess() {
        val email = "email@email.com"
        val password = "123456"
        val confirmPassword = "123456"

        // Preparation
        every {
            onChangePasswordUseCase(email, password, confirmPassword)
        } returns flowOf(true)

        // Execution
        forgotPasswordViewModel.changePassword(email, password, confirmPassword)

        // Verification
        verify {
            onChangePasswordUseCase(email, password, confirmPassword)
            forgotPasswordViewModel.changePasswordState.copy(
                isLoading = true,
                isSuccessChange = false,
            )
        }
        assertEquals(
            forgotPasswordViewModel.changePasswordState,
            ForgotPasswordState(isSuccessChange = true),
        )
    }

    @Test
    fun changePasswordError() {
        val email = "email@email.com"
        val password = "123456"
        val confirmPassword = "123456"

        // Preparation
        every {
            onChangePasswordUseCase(email, password, confirmPassword)
        } returns flowOf(false)

        // Execution
        forgotPasswordViewModel.changePassword(email, password, confirmPassword)

        // Verification
        verify {
            onChangePasswordUseCase(email, password, confirmPassword)
            forgotPasswordViewModel.changePasswordState.copy(
                isLoading = true,
                isSuccessChange = false,
            )
        }
        assertEquals(
            forgotPasswordViewModel.changePasswordState,
            ForgotPasswordState(errorUser = ForgotPasswordState.Error.USER_NOT_FOUND),
        )
    }

    @Test
    fun forgotEmailExceptionTest() {
        prepareErrorTest(
            ForgotEmailException,
            ForgotPasswordState(errorEmail = ForgotPasswordState.Error.EMPTY_EMAIL),
        )
    }

    @Test
    fun forgotPasswordException() {
        prepareErrorTest(
            ForgotPasswordException,
            ForgotPasswordState(errorPassword = ForgotPasswordState.Error.EMPTY_PASSWORD),
        )
    }

    @Test
    fun forgotConfirmPasswordException() {
        prepareErrorTest(
            ForgotConfirmPasswordException,
            ForgotPasswordState(errorConfirmPassword = ForgotPasswordState.Error.CONFIRM_PASSWORD),
        )
    }

    @Test
    fun userException() {
        prepareErrorTest(
            UserException,
            ForgotPasswordState(errorUser = ForgotPasswordState.Error.USER_NOT_FOUND),
        )
    }

    private fun prepareErrorTest(
        exception: Throwable,
        state: ForgotPasswordState,
    ) {
        val email = "email@email.com"
        val password = "123456"
        val confirmPassword = "123456"

        // Preparation
        every {
            onChangePasswordUseCase(email, password, confirmPassword)
        } returns flow { throw exception }

        // Execution
        forgotPasswordViewModel.changePassword(email, password, confirmPassword)

        // Verification
        verify {
            onChangePasswordUseCase(email, password, confirmPassword)
            forgotPasswordViewModel.changePasswordState.copy(
                isLoading = true,
                isSuccessChange = false,
            )
        }
        assertEquals(
            forgotPasswordViewModel.changePasswordState,
            state,
        )
    }
}
