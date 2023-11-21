package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.error.register.EmailException
import com.david.megaloginapp.domain.error.register.FullNameException
import com.david.megaloginapp.domain.error.register.PasswordException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.RegisterRepository
import com.david.megaloginapp.presentation.utils.isValidEmail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnRegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
) {

    operator fun invoke(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): Flow<User> = flow {
        if (isValidRegisterFields(fullName, email, password, confirmPassword)) {
            registerRepository.registerUser(fullName, email, password)
            delay(1500L)
            val user = registerRepository.getUserByEmail(email, password)
            delay(1500L)
            if (user != null) {
                emit(user)
            } else {
                throw UserException
            }
        }
    }

    private fun isValidRegisterFields(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        return when {
            fullName.isEmpty() || fullName.length < 5 -> {
                throw FullNameException
            }

            email.isEmpty() || email.isValidEmail().not() -> {
                throw EmailException
            }

            password.isEmpty() || password.length < 5 -> {
                throw PasswordException(isEmpty = true)
            }

            password != confirmPassword -> {
                throw PasswordException(isConfirm = true)
            }

            else -> {
                true
            }
        }
    }
}
