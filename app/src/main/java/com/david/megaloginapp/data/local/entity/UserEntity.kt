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
)

fun UserEntity.mapToDomain(): User {
    return User(
        id = id ?: 0,
        name = name,
        email = email,
        password = password,
    )
}

fun mapEntityFromDomain(user: User): UserEntity {
    return UserEntity(
        name = user.name,
        email = user.email,
        password = user.password,
    )
}
