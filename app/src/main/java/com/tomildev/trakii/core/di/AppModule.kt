package com.tomildev.trakii.core.di

import com.tomildev.trakii.features.settings.sub_settings.account.data.AccountSettingsRepositoryImpl
import com.tomildev.trakii.features.settings.sub_settings.account.domain.AccountSettingsRepository
import com.tomildev.trakii.features.settings.sub_settings.account.domain.use_case.AccountUseCasesWrapper
import com.tomildev.trakii.features.settings.main_settings.domain.use_case.LogoutUseCase
import com.tomildev.trakii.features.settings.sub_settings.account.domain.use_case.UpdateDisplayNameUseCase
import com.tomildev.trakii.features.settings.sub_settings.account.domain.use_case.ValidateNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideValidateName(): ValidateNameUseCase {
        return ValidateNameUseCase()
    }

    @Provides
    @Singleton
    fun provideAccountSettingsUseCases(
        accountSettingsRepository: AccountSettingsRepository,
        validateNameUseCase: ValidateNameUseCase,
        logoutUseCase: LogoutUseCase
    ): AccountUseCasesWrapper {
        return AccountUseCasesWrapper(
            updateDisplayName = UpdateDisplayNameUseCase(accountSettingsRepository),
            validateNameUseCase = validateNameUseCase,
            logout = logoutUseCase
        )
    }

    @Provides
    @Singleton
    fun provideAccountSettingsRepository(
        supabaseClient: SupabaseClient
    ): AccountSettingsRepository {
        return AccountSettingsRepositoryImpl(supabaseClient)
    }
}