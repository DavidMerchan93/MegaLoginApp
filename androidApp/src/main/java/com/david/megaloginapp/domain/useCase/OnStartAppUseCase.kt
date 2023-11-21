package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.StartAppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnStartAppUseCase @Inject constructor(
    private val startAppRepository: StartAppRepository,
) {
    operator fun invoke(): Flow<User> = flow {
        val user = startAppRepository.getCurrentUserLogged()
        delay(3000L)
        if (user != null) {
            emit(user)
        } else {
            throw UserException
        }
    }
}
