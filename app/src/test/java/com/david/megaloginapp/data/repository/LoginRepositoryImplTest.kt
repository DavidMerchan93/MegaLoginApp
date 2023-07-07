package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.common.Constants
import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.UserEntity
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.LoginRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoginRepositoryImplTest {

    private val userDao: UserDao = mockk()
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        loginRepository = LoginRepositoryImpl(userDao)
    }

    @After
    fun tearDown() {
        confirmVerified(userDao)
    }

    @Test
    fun loginUserSuccess() {
        val user: UserEntity = mockk()

        // Preparation
        every {
            userDao.getUserByEmailAndPassword("email", "password")
        } returns user

        every {
            userDao.updateUserLogin("email", true)
        } just runs

        every { user.id } returns 1
        every { user.name } returns "name"
        every { user.email } returns "email"
        every { user.password } returns "password"

        // Execution
        val result = loginRepository.loginUser("email", "password")

        // Verification
        assertEquals(
            result,
            User(
                id = 1,
                name = "name",
                email = "email",
                password = "password",
            ),
        )
        verify {
            userDao.getUserByEmailAndPassword("email", "password")
            userDao.updateUserLogin("email", true)
            user.id
            user.name
            user.email
            user.password
        }
        confirmVerified(user)
    }

    @Test
    fun loginUserSuccessSample() {
        // Preparation
        every {
            userDao.getUserByEmailAndPassword("usuario@prueba.com", "megalogin")
        } returns null

        // Execution
        val result = loginRepository.loginUser("usuario@prueba.com", "megalogin")

        // Verification
        assertEquals(
            result,
            Constants.USER_SAMPLE,
        )
        verify {
            userDao.getUserByEmailAndPassword("usuario@prueba.com", "megalogin")
        }
    }

    @Test
    fun loginUserError() {
        // Preparation
        every {
            userDao.getUserByEmailAndPassword("error@error.com", "password")
        } returns null

        // Execution
        val result = loginRepository.loginUser("error@error.com", "password")

        // Verification
        assertEquals(
            result,
            null,
        )
        verify {
            userDao.getUserByEmailAndPassword("error@error.com", "password")
        }
    }
}
