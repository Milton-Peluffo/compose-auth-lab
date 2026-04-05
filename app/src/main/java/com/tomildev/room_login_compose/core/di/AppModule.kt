package com.tomildev.room_login_compose.core.di

import android.content.Context
import androidx.room.Room
import com.tomildev.room_login_compose.core.data.dao.UserDao
import com.tomildev.room_login_compose.core.data.local.db.AppDatabase
import com.tomildev.room_login_compose.core.data.preferences.UserPreferences
import com.tomildev.room_login_compose.core.data.repository.UserRepositoryImpl
import com.tomildev.room_login_compose.core.domain.repository.UserRepository
import com.tomildev.room_login_compose.features.auth.otp.data.OtpRepositoryImpl
import com.tomildev.room_login_compose.features.auth.otp.domain.OtpRepository
import com.tomildev.room_login_compose.features.auth.signup.data.SignUpRepositoryImpl
import com.tomildev.room_login_compose.features.auth.signup.domain.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
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
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideSignUpRepository(supabaseClient: SupabaseClient): SignUpRepository {
        return SignUpRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideOtpRepository(supabaseClient: SupabaseClient): OtpRepository {
        return OtpRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, userPreferences: UserPreferences): UserRepository {
        return UserRepositoryImpl(userDao, userPreferences)
    }
}