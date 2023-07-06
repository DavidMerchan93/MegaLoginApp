package com.david.megaloginapp.presentation.state

import com.david.megaloginapp.domain.model.User

data class UserHomeState(
    val isLoading: Boolean = false,
    val userData: User? = null,
    val isLogout: Boolean = false,
)
