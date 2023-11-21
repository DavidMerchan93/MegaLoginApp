package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.UserEntity
import com.david.megaloginapp.domain.repository.ForgotPasswordRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test

class ForgotPasswordRepositoryImplTest {

    private val userDao: UserDao = mockk()
    private lateinit var forgotPasswordRepository: ForgotPasswordRepository

    @Before
    fun setUp() {
        forgotPasswordRepository = ForgotPasswordRepositoryImpl(userDao)
    }

    @After
    fun tearDown() {
        confirmVerified(userDao)
    }

    @Test
    fun changePasswordSuccess() {
        val userEntity: UserEntity = mockk()

        // Preparation
        every {
            userDao.getUserByEmail("email")
        } returns userEntity

        every {
            userDao.changePassword("email", "password")
        } just runs

        // Execution
        val result = forgotPasswordRepository.changePassword("email", "password")

        // Verification
        assertTrue(result)
        verify {
            userDao.getUserByEmail("email")
            userDao.changePassword("email", "password")
        }

        confirmVerified(userEntity)
    }

    @Test
    fun changePasswordNull() {
        // Preparation
        every {
            userDao.getUserByEmail("email")
        } returns null

        // Execution
        val result = forgotPasswordRepository.changePassword("email", "password")

        // Verification
        assertFalse(result)
        verify {
            userDao.getUserByEmail("email")
        }
    }

    @Test
    fun changePasswordEmailError() {
        // Execution
        val result = forgotPasswordRepository.changePassword("error@error.com", "password")

        // Verification
        assertFalse(result)
    }
}
