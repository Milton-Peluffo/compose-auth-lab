package com.tomildev.trakii.core.di

import com.tomildev.trakii.features.settings.sub_settings.account.data.AccountSettingsRepositoryImpl
import android.content.Context
import androidx.room.Room
import com.tomildev.trakii.core.data.local.database.HabitiiDatabase
import com.tomildev.trakii.core.data.local.dao.ProfileDao
import com.tomildev.trakii.features.settings.sub_settings.account.domain.AccountSettingsRepository
import com.tomildev.trakii.features.settings.sub_settings.account.domain.use_case.AccountUseCasesWrapper
import com.tomildev.trakii.features.settings.main_settings.domain.use_case.LogoutUseCase
import com.tomildev.trakii.features.settings.sub_settings.account.domain.use_case.UpdateDisplayNameUseCase
import com.tomildev.trakii.features.settings.sub_settings.account.domain.use_case.ValidateNameUseCase
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
    fun provideHabitiiDatabase(@ApplicationContext context: Context): HabitiiDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = HabitiiDatabase::class.java,
            name = "habitii.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProfileDao(database: HabitiiDatabase): ProfileDao {
        return database.profileDao
    }

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
        supabaseClient: SupabaseClient,
        profileDao: ProfileDao
    ): AccountSettingsRepository {
        return AccountSettingsRepositoryImpl(supabaseClient, profileDao)
    }
}
