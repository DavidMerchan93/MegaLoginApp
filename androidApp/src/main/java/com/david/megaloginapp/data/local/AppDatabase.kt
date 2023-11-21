package com.david.megaloginapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
