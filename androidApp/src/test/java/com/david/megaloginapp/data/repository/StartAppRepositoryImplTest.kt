package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.UserEntity
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.StartAppRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class StartAppRepositoryImplTest {

    private val userDao: UserDao = mockk()
    private lateinit var startAppRepository: StartAppRepository

    @Before
    fun setUp() {
        startAppRepository = StartAppRepositoryImpl(userDao)
    }

    @After
    fun tearDown() {
        confirmVerified(userDao)
    }

    @Test
    fun getCurrentUserLogged() {
        val userEntity: UserEntity = mockk()

        // Preparation
        every {
            userDao.getUserLogged()
        } returns userEntity

        every { userEntity.id } returns 1
        every { userEntity.name } returns "name"
        every { userEntity.email } returns "email"
        every { userEntity.password } returns "password"

        // Execution
        val user = startAppRepository.getCurrentUserLogged()

        // Verification
        assertEquals(
            user,
            User(
                id = 1,
                name = "name",
                email = "email",
                password = "password",
            ),
        )
        verify {
            userDao.getUserLogged()
            userEntity.id
            userEntity.name
            userEntity.email
            userEntity.password
        }

        confirmVerified(userEntity)
    }
}
