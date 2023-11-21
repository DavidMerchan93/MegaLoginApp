package com.david.megaloginapp.domain.repository

import com.david.megaloginapp.domain.model.User

interface HomeRepository {
    fun getUserData(id: Int): User?
    fun logout()
}
