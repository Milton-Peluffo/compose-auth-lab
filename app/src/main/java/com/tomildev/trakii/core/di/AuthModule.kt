package com.tomildev.trakii.core.di

import android.content.Context
import com.tomildev.trakii.core.data.preferences.UserPreferences
import com.tomildev.trakii.core.data.repository.SessionRepositoryImpl
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.features.auth.signin.data.OAuthRepositoryImpl
import com.tomildev.trakii.features.auth.signin.domain.OAuthRepository
import com.tomildev.trakii.features.auth.signin.util.GoogleAuthClient
import com.tomildev.trakii.features.onboarding.data.repository.OnBoardingRepositoryImpl
import com.tomildev.trakii.features.onboarding.domain.repository.OnboardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Provides
    @Singleton
    fun provideGoogleAuthClient(@ApplicationContext context: Context): GoogleAuthClient {
        return GoogleAuthClient(context)
    }

    @Provides
    @Singleton
    fun provideOnboardingRepository(
        supabaseClient: SupabaseClient,
        userPreferences: UserPreferences
    ): OnboardingRepository {
        return OnBoardingRepositoryImpl(supabaseClient, userPreferences)
    }

    @Provides
    @Singleton
    fun provideOauthRepository(supabaseClient: SupabaseClient): OAuthRepository {
        return OAuthRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(
        supabaseClient: SupabaseClient,
        userPreferences: UserPreferences
    ): SessionRepository {
        return SessionRepositoryImpl(supabaseClient, userPreferences)
    }
}
