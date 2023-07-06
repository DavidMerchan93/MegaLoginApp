package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.RegisterRepository
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
    ): Flow<User> = flow {
        registerRepository.registerUser(fullName, email, password)
        delay(2000L)
        val user = registerRepository.getUserByEmail(email, password)
        delay(2000L)
        if (user != null) {
            emit(user)
        } else {
            throw Exception("Register Error")
        }
    }
}
