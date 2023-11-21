package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.HomeRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUserDataUseCaseTest {

    private val homeRepository: HomeRepository = mockk()

    private lateinit var getUserDataUseCase: GetUserDataUseCase

    @Before
    fun setUp() {
        getUserDataUseCase = GetUserDataUseCase(homeRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(homeRepository)
    }

    @Test
    fun invokeSuccess() = runBlocking {
        val user: User = mockk()

        // Preparation
        every {
            homeRepository.getUserData(1)
        } returns user

        // Execution
        getUserDataUseCase(1).collect {
            assertEquals(it, user)
        }

        // Verification
        verify {
            homeRepository.getUserData(1)
            user.equals(any())
        }

        confirmVerified(user)
    }

    @Test
    fun invokeError() = runBlocking {
        // Preparation
        every {
            homeRepository.getUserData(1)
        } returns null

        // Execution
        getUserDataUseCase(1).catch {
            assertEquals(it, UserException)
        }.collect()

        // Verification
        verify {
            homeRepository.getUserData(1)
        }
    }
}
