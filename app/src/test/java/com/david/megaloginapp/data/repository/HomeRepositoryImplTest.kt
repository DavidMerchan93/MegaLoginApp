package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.UserEntity
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.HomeRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeRepositoryImplTest {

    private val userDao: UserDao = mockk()
    private lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {
        homeRepository = HomeRepositoryImpl(userDao)
    }

    @After
    fun tearDown() {
        confirmVerified(userDao)
    }

    @Test
    fun getUserData() {
        val userEntity: UserEntity = mockk()

        // Preparation
        every {
            userDao.getUserById(1)
        } returns userEntity

        every { userEntity.id } returns 1
        every { userEntity.name } returns "name"
        every { userEntity.email } returns "email"
        every { userEntity.password } returns "password"

        // Execution
        val result = homeRepository.getUserData(1)

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
            userDao.getUserById(1)
            userEntity.id
            userEntity.name
            userEntity.email
            userEntity.password
        }

        confirmVerified(userEntity)
    }

    @Test
    fun getUserDataNull() {
        // Preparation
        every {
            userDao.getUserById(1)
        } returns null

        // Execution
        val result = homeRepository.getUserData(1)

        // Verification
        assertEquals(
            result,
            null,
        )
        verify {
            userDao.getUserById(1)
        }
    }

    @Test
    fun logout() {
        // Preparation
        every { userDao.logout() } just runs

        // Execution
        homeRepository.logout()

        // Verification
        verify {
            userDao.logout()
        }
    }
}
