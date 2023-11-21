package com.david.megaloginapp.domain.repository

import com.david.megaloginapp.domain.model.User

interface RegisterRepository {
    fun registerUser(
        fullName: String,
        email: String,
        password: String,
    )

    fun getUserByEmail(email: String, password: String): User?
}
