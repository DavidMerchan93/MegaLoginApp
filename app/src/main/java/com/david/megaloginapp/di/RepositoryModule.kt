package com.david.megaloginapp.di

import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.repository.LoginRepositoryImpl
import com.david.megaloginapp.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideLoginRepository(userDao: UserDao): LoginRepository = LoginRepositoryImpl(userDao)
}
