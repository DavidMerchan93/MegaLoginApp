package com.david.megaloginapp.presentation.state

import com.david.megaloginapp.domain.model.User

data class SplashScreenState(
    val isLoading: Boolean = false,
    val isUserLogged: Boolean = false,
    val user: User? = null,
)
