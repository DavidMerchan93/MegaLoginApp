package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    operator fun invoke(email: String, password: String): Flow<User> = flow {
        val user = loginRepository.loginUser(email, password)
        delay(3000L)
        if (user != null) {
            emit(user)
        } else {
            throw Exception("User not found")
        }
    }
}
