package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.repository.HomeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnLogoutUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    operator fun invoke(): Flow<Boolean> = flow {
        homeRepository.logout()
        delay(1000L)
        emit(true)
    }
}
