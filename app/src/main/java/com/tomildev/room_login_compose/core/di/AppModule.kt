package com.tomildev.room_login_compose.core.di

import android.content.Context
import androidx.room.Room
import com.tomildev.room_login_compose.core.data.dao.UserDao
import com.tomildev.room_login_compose.core.data.local.db.AppDatabase
import com.tomildev.room_login_compose.core.data.repository.UserRepositoryImpl
import com.tomildev.room_login_compose.core.data.session.SessionManager
import com.tomildev.room_login_compose.core.domain.repository.UserRepository
import com.tomildev.room_login_compose.features.auth.data.repository.AuthRepositoryImpl
import com.tomildev.room_login_compose.features.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "user_database"
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideAuthRepository(userDao: UserDao): AuthRepository {
        return AuthRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, sessionManager: SessionManager): UserRepository {
        return UserRepositoryImpl(userDao, sessionManager)
    }
}