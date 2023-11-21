package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.login.LoginEmailException
import com.david.megaloginapp.domain.error.login.LoginPasswordException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.LoginRepository
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

class OnLoginUseCaseTest {

    private val loginRepository: LoginRepository = mockk()

    private lateinit var onLoginUseCase: OnLoginUseCase

    @Before
    fun setUp() {
        onLoginUseCase = OnLoginUseCase(loginRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(loginRepository)
    }

    @Test
    fun invokeSuccess() = runBlocking {
        val user: User = mockk()
        val email = "email@email.com"
        val password = "123456"

        // Preparation
        every {
            loginRepository.loginUser(email, password)
        } returns user

        // Execution
        onLoginUseCase(email, password).collect {
            assertEquals(it, user)
        }

        // Verification
        verify {
            loginRepository.loginUser(email, password)
            user.equals(any())
        }

        confirmVerified(user)
    }

    @Test
    fun invokeEmptyEmailError() = runBlocking {
        val email = ""
        val password = "123456"

        // Execution
        onLoginUseCase(email, password).catch {
            assertEquals(it, LoginEmailException)
        }.collect()
    }

    @Test
    fun invokeEmailError() = runBlocking {
        val email = "email@email"
        val password = "123456"

        // Execution
        onLoginUseCase(email, password).catch {
            assertEquals(it, LoginEmailException)
        }.collect()
    }

    @Test
    fun invokeEmptyPasswordError() = runBlocking {
        val email = "email@email.com"
        val password = ""

        // Execution
        onLoginUseCase(email, password).catch {
            assertEquals(it, LoginPasswordException)
        }.collect()
    }

    @Test
    fun invokePasswordError() = runBlocking {
        val email = "email@email.com"
        val password = "123"

        // Execution
        onLoginUseCase(email, password).catch {
            assertEquals(it, LoginPasswordException)
        }.collect()
    }
}
