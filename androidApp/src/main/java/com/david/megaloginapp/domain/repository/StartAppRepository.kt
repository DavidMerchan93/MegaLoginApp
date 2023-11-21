package com.david.megaloginapp.domain.repository

import com.david.megaloginapp.domain.model.User

interface StartAppRepository {
    fun getCurrentUserLogged(): User?
}
