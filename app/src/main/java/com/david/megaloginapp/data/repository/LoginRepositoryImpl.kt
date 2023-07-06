package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.common.Constants
import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.mapToDomain
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : LoginRepository {
    override fun loginUser(email: String, password: String): User? {
        val user = userDao.getUserByEmailAndPassword(email, password)
        return when {
            (email == Constants.USER_SAMPLE.email && password == Constants.USER_SAMPLE.password) -> {
                Constants.USER_SAMPLE
            }

            email == Constants.EMAIL_ERROR -> {
                null
            }

            user != null -> user.mapToDomain()

            else -> null
        }
    }
}
