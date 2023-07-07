package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.forgotPassword.ForgotConfirmPasswordException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotEmailException
import com.david.megaloginapp.domain.error.forgotPassword.ForgotPasswordException
import com.david.megaloginapp.domain.repository.ForgotPasswordRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class OnChangePasswordUseCaseTest {

    private val forgotPasswordRepository: ForgotPasswordRepository = mockk()

    private lateinit var onChangePasswordUseCase: OnChangePasswordUseCase

    @Before
    fun setUp() {
        onChangePasswordUseCase = OnChangePasswordUseCase(forgotPasswordRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(forgotPasswordRepository)
    }

    @Test
    fun invokeSuccess() = runBlocking {
        val email = "email@email.com"
        val password = "123456"

        // Preparation
        every {
            forgotPasswordRepository.changePassword(email, password)
        } returns true

        // Execution
        onChangePasswordUseCase(email, password, password).collect {
            assertTrue(it)
        }

        // Verification
        verify {
            forgotPasswordRepository.changePassword(email, password)
        }
    }

    @Test
    fun invokeEmptyEmailError() = runBlocking {
        val email = ""
        val password = "123456"

        // Execution
        onChangePasswordUseCase(email, password, password).catch {
            assertEquals(it, ForgotEmailException)
        }.collect()
    }

    @Test
    fun invokeEmailError() = runBlocking {
        val email = "email@e"
        val password = "123456"

        // Execution
        onChangePasswordUseCase(email, password, password).catch {
            assertEquals(it, ForgotEmailException)
        }.collect()
    }

    @Test
    fun invokeEmptyPasswordError() = runBlocking {
        val email = "email@email.com"
        val password = ""

        // Execution
        onChangePasswordUseCase(email, password, password).catch {
            assertEquals(it, ForgotPasswordException)
        }.collect()
    }

    @Test
    fun invokePasswordError() = runBlocking {
        val email = "email@email.com"
        val password = "123"

        // Execution
        onChangePasswordUseCase(email, password, password).catch {
            assertEquals(it, ForgotPasswordException)
        }.collect()
    }

    @Test
    fun invokeConfirmPasswordError() = runBlocking {
        val email = "email@email.com"
        val password = "123456"

        // Execution
        onChangePasswordUseCase(email, password, "").catch {
            assertEquals(it, ForgotConfirmPasswordException)
        }.collect()
    }
}
