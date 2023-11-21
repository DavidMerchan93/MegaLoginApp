package com.david.megaloginapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.david.megaloginapp.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE id=:id")
    fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM user WHERE email=:email AND password=:password")
    fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

    @Query("SELECT * FROM user WHERE email=:email")
    fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM user WHERE isLogged = 1")
    fun getUserLogged(): UserEntity?

    @Query("UPDATE user SET password=:password WHERE email=:email")
    fun changePassword(email: String, password: String)

    @Query("UPDATE user SET isLogged=:isLogged WHERE email=:email")
    fun updateUserLogin(email: String, isLogged: Boolean)

    @Query("UPDATE user SET isLogged = 0")
    fun logout()
}
