package com.david.megaloginapp.domain.useCase

import com.david.megaloginapp.domain.error.register.EmailException
import com.david.megaloginapp.domain.error.register.FullNameException
import com.david.megaloginapp.domain.error.register.PasswordException
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.RegisterRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OnRegisterUseCaseTest {

    private val registerRepository: RegisterRepository = mockk()

    private lateinit var onRegisterUseCase: OnRegisterUseCase

    @Before
    fun setUp() {
        onRegisterUseCase = OnRegisterUseCase(registerRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(registerRepository)
    }

    @Test
    fun invokeSuccess() = runBlocking {
        val user: User = mockk()
        val fullName = "fullname"
        val email = "email@email.com"
        val password = "123123"

        // Preparation
        every {
            registerRepository.registerUser(fullName, email, password)
        } just runs

        every {
            registerRepository.getUserByEmail(email, password)
        } returns user

        // Execution
        onRegisterUseCase.invoke(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = password,
        ).collect {
            assertEquals(it, user)
        }

        // Verification
        verify {
            registerRepository.registerUser(fullName, email, password)
            registerRepository.getUserByEmail(email, password)
            user.equals(any())
        }

        confirmVerified(user)
    }

    @Test
    fun invokeNameEmptyError() = runBlocking {
        val fullName = ""
        val email = "email@email.com"
        val password = "123123"

        // Execution
        onRegisterUseCase.invoke(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = password,
        ).catch {
            assertEquals(it, FullNameException)
        }.collect()
    }

    @Test
    fun invokeNameError() = runBlocking {
        val fullName = "abc"
        val email = "email@email.com"
        val password = "123123"

        // Execution
        onRegisterUseCase.invoke(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = password,
        ).catch {
            assertEquals(it, FullNameException)
        }.collect()
    }

    @Test
    fun invokeEmailEmptyError() = runBlocking {
        val fullName = "fullName"
        val email = ""
        val password = "123123"

        // Execution
        onRegisterUseCase.invoke(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = password,
        ).catch {
            assertEquals(it, EmailException)
        }.collect()
    }

    @Test
    fun invokeEmailError() = runBlocking {
        val fullName = "fullName"
        val email = "email"
        val password = "123123"

        // Execution
        onRegisterUseCase.invoke(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = password,
        ).catch {
            assertEquals(it, EmailException)
        }.collect()
    }

    @Test
    fun invokePasswordEmptyError() = runBlocking {
        val fullName = "fullName"
        val email = "email@email.com"
        val password = ""

        // Execution
        onRegisterUseCase.invoke(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = password,
        ).catch {
            assertEquals(it, PasswordException(isEmpty = true))
        }.collect()
    }

    @Test
    fun invokePasswordError() = runBlocking {
        val fullName = "fullName"
        val email = "email@email.com"
        val password = "123"

        // Execution
        onRegisterUseCase.invoke(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = password,
        ).catch {
            assertEquals(it, PasswordException(isEmpty = true))
        }.collect()
    }

    @Test
    fun invokeConfirmPasswordError() = runBlocking {
        val fullName = "fullName"
        val email = "email@email.com"
        val password = "123456"

        // Execution
        onRegisterUseCase.invoke(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = "",
        ).catch {
            assertEquals(it, PasswordException(isConfirm = true))
        }.collect()
    }
}
