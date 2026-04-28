package com.tomildev.trakii.core.di

import android.content.Context
import androidx.room.Room
import com.tomildev.trakii.core.data.dao.UserDao
import com.tomildev.trakii.core.data.local.db.AppDatabase
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.data.repository.UserRepositoryImpl
import com.tomildev.trakii.core.domain.repository.UserRepository
import com.tomildev.trakii.features.auth.common.data.OAuthRepositoryImpl
import com.tomildev.trakii.features.auth.common.domain.OAuthRepository
import com.tomildev.trakii.features.auth.common.util.GoogleAuthClient
import com.tomildev.trakii.features.auth.otp.data.OtpRepositoryImpl
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import com.tomildev.trakii.features.auth.signin.data.SignInRepositoryImpl
import com.tomildev.trakii.features.auth.signin.domain.SignInRepository
import com.tomildev.trakii.features.auth.signup.data.SignUpRepositoryImpl
import com.tomildev.trakii.features.auth.signup.domain.SignUpRepository
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
    @Singleton
    fun provideGoogleAuthClient(@ApplicationContext context: Context): GoogleAuthClient {
        return GoogleAuthClient(context)
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideOauthRepository(supabaseClient: SupabaseClient): OAuthRepository {
        return OAuthRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideSignUpRepository(supabaseClient: SupabaseClient): SignUpRepository {
        return SignUpRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideSignInRepository(supabaseClient: SupabaseClient): SignInRepository {
        return SignInRepositoryImpl(supabaseClient)
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