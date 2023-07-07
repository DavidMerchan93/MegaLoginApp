package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.common.Constants
import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.UserEntity
import com.david.megaloginapp.data.local.entity.mapToDomain
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : RegisterRepository {
    override fun registerUser(fullName: String, email: String, password: String) {
        userDao.insert(
            UserEntity(
                name = fullName,
                email = email,
                password = password,
            ),
        )
    }

    override fun getUserByEmail(email: String, password: String): User? {
        return if (email != Constants.EMAIL_ERROR) {
            val user = userDao.getUserByEmailAndPassword(email, password)
            user?.mapToDomain()
        } else {
            null
        }
    }
}
