package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.error.login.LoginEmailException
import com.david.megaloginapp.domain.error.login.LoginPasswordException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.LoginRepository
import com.david.megaloginapp.presentation.utils.isValidEmail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    operator fun invoke(email: String, password: String): Flow<User> = flow {
        if (isValidFields(email, password)) {
            val user = loginRepository.loginUser(email, password)
            delay(3000L)
            if (user != null) {
                emit(user)
            } else {
                throw UserException
            }
        }
    }

    private fun isValidFields(email: String, password: String): Boolean {
        return when {
            (email.isEmpty() || email.isValidEmail().not()) -> {
                throw LoginEmailException
            }

            (password.isEmpty() || password.length < 5) -> {
                throw LoginPasswordException
            }

            else -> true
        }
    }
}
