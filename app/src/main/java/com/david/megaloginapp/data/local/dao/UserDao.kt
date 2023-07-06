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

    @Query("SELECT * FROM user WHERE email=:email AND password=:password")
    fun getUserByEmailAndPassword(email: String, password: String): UserEntity?
}
