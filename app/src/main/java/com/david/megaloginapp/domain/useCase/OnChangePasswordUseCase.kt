package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotConfirmPasswordException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotEmailException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotPasswordException
import com.david.megaloginapp.domain.repository.ForgotPasswordRepository
import com.david.megaloginapp.presentation.view.common.isValidEmail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnChangePasswordUseCase @Inject constructor(
    private val forgotPasswordRepository: ForgotPasswordRepository,
) {
    operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
    ): Flow<Boolean> = flow {
        if (isValidFields(email, password, confirmPassword)) {
            val result = forgotPasswordRepository.changePassword(email, password)
            if (result) {
                delay(2000L)
                emit(true)
            } else {
                throw UserException
            }
        }
    }

    private fun isValidFields(
        email: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        return when {
            email.isEmpty() || email.isValidEmail().not() -> {
                throw ForgotEmailException
            }

            password.isEmpty() || password.length < 5 -> {
                throw ForgotPasswordException
            }

            password != confirmPassword -> {
                throw ForgotConfirmPasswordException
            }

            else -> true
        }
    }
}
