package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.repository.HomeRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class OnLogoutUseCaseTest {
    private val homeRepository: HomeRepository = mockk()

    private lateinit var onLogoutUseCase: OnLogoutUseCase

    @Before
    fun setUp() {
        onLogoutUseCase = OnLogoutUseCase(homeRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(homeRepository)
    }

    @Test
    fun invoke() = runBlocking {
        // Preparation
        every { homeRepository.logout() } just runs

        // Execution
        onLogoutUseCase().collect {
            assertTrue(it)
        }

        // Verification
        verify {
            homeRepository.logout()
        }
    }
}
