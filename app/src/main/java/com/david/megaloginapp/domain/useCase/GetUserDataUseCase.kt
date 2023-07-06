package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.HomeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    operator fun invoke(id: Int): Flow<User> = flow {
        val user = homeRepository.getUserData(id)
        delay(1000L)
        if (user != null) {
            emit(user)
        } else {
            throw UserException
        }
    }
}
