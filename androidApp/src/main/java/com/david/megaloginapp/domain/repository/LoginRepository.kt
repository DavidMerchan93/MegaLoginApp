package com.david.megaloginapp.domain.repository

import com.david.megaloginapp.domain.model.User

interface LoginRepository {
    fun loginUser(email: String, password: String): User?
}
