package com.david.megaloginapp.di

import com.david.megaloginapp.data.local.dao.UserDao
import com.david.megaloginapp.data.repository.ForgotPasswordRepositoryImpl
import com.david.megaloginapp.data.repository.LoginRepositoryImpl
import com.david.megaloginapp.data.repository.RegisterRepositoryImpl
import com.david.megaloginapp.data.repository.StartAppRepositoryImpl
import com.david.megaloginapp.domain.repository.ForgotPasswordRepository
import com.david.megaloginapp.domain.repository.LoginRepository
import com.david.megaloginapp.domain.repository.RegisterRepository
import com.david.megaloginapp.domain.repository.StartAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideLoginRepository(userDao: UserDao): LoginRepository = LoginRepositoryImpl(userDao)

    @Provides
    fun provideRegisterRepository(userDao: UserDao): RegisterRepository =
        RegisterRepositoryImpl(userDao)

    @Provides
    fun provideForgotPasswordRepository(userDao: UserDao): ForgotPasswordRepository =
        ForgotPasswordRepositoryImpl(userDao)

    @Provides
    fun provideStartAppRepository(userDao: UserDao): StartAppRepository =
        StartAppRepositoryImpl(userDao)
}
