package com.tomildev.trakii.core.di

import android.content.Context
import com.tomildev.trakii.core.domain.use_case.user.ValidateName
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
    fun provideValidateName(): ValidateName {
        return ValidateName()
    }

    @Provides
    @Singleton
    fun provideGoogleAuthClient(@ApplicationContext context: Context): GoogleAuthClient {
        return GoogleAuthClient(context)
    }
}
