package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.common.UserException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.StartAppRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class OnStartAppUseCaseTest {

    private val startAppRepository: StartAppRepository = mockk()

    private lateinit var onStartAppUseCase: OnStartAppUseCase

    @Before
    fun setUp() {
        onStartAppUseCase = OnStartAppUseCase(startAppRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(startAppRepository)
    }

    @Test
    fun invokeSuccess() = runBlocking {
        val user: User = mockk()

        // Preparation
        every {
            startAppRepository.getCurrentUserLogged()
        } returns user

        // Execution
        onStartAppUseCase.invoke().collect {
            assertEquals(it, user)
        }

        // Verification
        verify {
            startAppRepository.getCurrentUserLogged()
            user.equals(any())
        }

        confirmVerified(user)
    }

    @Test
    fun invokeError() = runBlocking {
        // Preparation
        every {
            startAppRepository.getCurrentUserLogged()
        } returns null

        // Execution
        onStartAppUseCase.invoke().catch {
            assertEquals(it, UserException)
        }.collect()

        // Verification
        verify {
            startAppRepository.getCurrentUserLogged()
        }
    }
}
