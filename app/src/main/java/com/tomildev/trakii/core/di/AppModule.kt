package com.tomildev.trakii.core.di

import android.content.Context
import androidx.room.Room
import com.tomildev.trakii.core.data.dao.UserDao
import com.tomildev.trakii.core.data.local.db.AppDatabase
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.data.repository.UserRepositoryImpl
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.repository.UserRepository
import com.tomildev.trakii.core.domain.use_case.user.UserValidationUseCases
import com.tomildev.trakii.core.domain.use_case.user.ValidateConfirmPassword
import com.tomildev.trakii.core.domain.use_case.user.ValidateEmail
import com.tomildev.trakii.core.domain.use_case.user.ValidateName
import com.tomildev.trakii.core.domain.use_case.user.ValidatePassword
import com.tomildev.trakii.features.auth.common.util.GoogleAuthClient
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
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUserValidationUseCases(): UserValidationUseCases {
        return UserValidationUseCases(
            validateName = ValidateName(),
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateConfirmPassword = ValidateConfirmPassword()
        )
    }

    @Provides
    @Singleton
    fun provideGoogleAuthClient(@ApplicationContext context: Context): GoogleAuthClient {
        return GoogleAuthClient(context)
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: UserDao,
        userPreferences: UserPreferences,
        sessionRepository: SessionRepository
    ): UserRepository {
        return UserRepositoryImpl(userDao, userPreferences, sessionRepository)
    }
}
