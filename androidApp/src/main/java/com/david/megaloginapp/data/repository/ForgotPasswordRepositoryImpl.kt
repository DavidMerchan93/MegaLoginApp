package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.common.Constants
import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.domain.repository.ForgotPasswordRepository
import javax.inject.Inject

class ForgotPasswordRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : ForgotPasswordRepository {
    override fun changePassword(email: String, password: String): Boolean {
        return if (email == Constants.EMAIL_ERROR) {
            false
        } else {
            val user = userDao.getUserByEmail(email)
            if (user != null) {
                userDao.changePassword(email, password)
                true
            } else {
                false
            }
        }
    }
}
