package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.mapToDomain
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val userDao: UserDao,
) : HomeRepository {
    override fun getUserData(id: Int): User? {
        return userDao.getUserById(id)?.mapToDomain()
    }

    override fun logout() {
        userDao.logout()
    }
}
