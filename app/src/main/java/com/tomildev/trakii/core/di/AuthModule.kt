package com.tomildev.trakii.core.di

import com.tomildev.trakii.core.data.repository.SessionRepositoryImpl
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.core.domain.use_case.session.LogoutUseCase
import com.tomildev.trakii.features.auth.common.data.OAuthRepositoryImpl
import com.tomildev.trakii.features.auth.common.domain.OAuthRepository
import com.tomildev.trakii.features.auth.common.domain.use_case.AuthUseCases
import com.tomildev.trakii.features.auth.common.domain.use_case.AuthWithGoogleUseCase
import com.tomildev.trakii.features.settings.subsettings.account.data.AccountSettingsRepositoryImpl
import com.tomildev.trakii.features.settings.subsettings.account.domain.AccountSettingsRepository
import com.tomildev.trakii.features.settings.subsettings.account.domain.use_case.AccountSettingsUseCases
import com.tomildev.trakii.features.settings.subsettings.account.domain.use_case.UpdateDisplayNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAccountSettingsRepository(
        supabaseClient: SupabaseClient
    ): AccountSettingsRepository {
        return AccountSettingsRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideAccountSettingsUseCases(
        accountSettingsRepository: AccountSettingsRepository,
        logoutUseCase: LogoutUseCase
    ): AccountSettingsUseCases {
        return AccountSettingsUseCases(
            updateDisplayName = UpdateDisplayNameUseCase(accountSettingsRepository),
            logout = logoutUseCase
        )
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(
        oauthRepository: OAuthRepository
    ): AuthUseCases {
        return AuthUseCases(
            authWithGoogle = AuthWithGoogleUseCase(oauthRepository)
        )
    }

    @Provides
    @Singleton
    fun provideOauthRepository(supabaseClient: SupabaseClient): OAuthRepository {
        return OAuthRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(supabaseClient: SupabaseClient): SessionRepository {
        return SessionRepositoryImpl(supabaseClient)
    }
}
