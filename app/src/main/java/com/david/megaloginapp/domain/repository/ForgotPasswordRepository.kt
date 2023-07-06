package com.david.megaloginapp.domain.repository

interface ForgotPasswordRepository {
    fun changePassword(email: String, password: String): Boolean
}
