package com.david.megaloginapp.data.repository

import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.mapToDomain
import com.david.megaloginapp.domain.model.User
import com.david.megaloginapp.domain.repository.StartAppRepository
import javax.inject.Inject

class StartAppRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : StartAppRepository {
    override fun getCurrentUserLogged(): User? {
        val user = userDao.getUserLogged()
        return user?.mapToDomain()
    }
}
