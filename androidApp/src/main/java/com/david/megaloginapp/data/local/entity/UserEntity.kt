package com.david.megaloginapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.david.megaloginapp.domain.model.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val email: String,
    val password: String,
    val isLogged: Boolean = false,
)

fun UserEntity.mapToDomain(): User {
    return User(
        id = id ?: 0,
        name = name,
        email = email,
        password = password,
    )
}
