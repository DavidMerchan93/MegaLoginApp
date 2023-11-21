package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.UserEntity
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.RegisterRepository
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

class RegisterRepositoryImplTest {

    private val userDao: UserDao = mockk()
    private lateinit var registerRepository: RegisterRepository

    @Before
    fun setUp() {
        registerRepository = RegisterRepositoryImpl(userDao)
    }

    @After
    fun tearDown() {
        confirmVerified(userDao)
    }

    @Test
    fun registerUser() {
        // Preparation
        every {
            userDao.insert(
                UserEntity(
                    name = "fullName",
                    email = "email",
                    password = "password",
                ),
            )
        } just runs

        // Execution
        registerRepository.registerUser(
            fullName = "fullName",
            email = "email",
            password = "password",
        )

        // Verification
        verify {
            userDao.insert(
                UserEntity(
                    name = "fullName",
                    email = "email",
                    password = "password",
                ),
            )
        }
    }

    @Test
    fun getUserByEmailSuccess() {
        val userEntity: UserEntity = mockk()

        // Preparation
        every {
            userDao.getUserByEmailAndPassword("email", "password")
        } returns userEntity

        every { userEntity.id } returns 1
        every { userEntity.name } returns "name"
        every { userEntity.email } returns "email"
        every { userEntity.password } returns "password"

        // Execution
        val result = registerRepository.getUserByEmail("email", "password")

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
            userEntity.id
            userEntity.name
            userEntity.email
            userEntity.password
        }

        confirmVerified(userEntity)
    }

    @Test
    fun getUserByEmailError() {
        // Execution
        val result = registerRepository.getUserByEmail("error@error.com", "password")

        // Verification
        assertEquals(
            result,
            null,
        )
    }
}
