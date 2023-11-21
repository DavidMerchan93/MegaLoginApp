package com.david.megaloginapp.domain.error.register

data class PasswordException(
    val isEmpty: Boolean = false,
    val isConfirm: Boolean = false,
) : Exception()
