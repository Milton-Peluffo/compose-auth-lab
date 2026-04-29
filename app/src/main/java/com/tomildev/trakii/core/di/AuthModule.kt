package com.tomildev.trakii.core.di

import com.tomildev.trakii.features.auth.common.domain.use_case.AuthUseCases
import com.tomildev.trakii.features.auth.otp.data.OtpRepositoryImpl
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import com.tomildev.trakii.features.auth.otp.domain.use_case.ResendOtpUseCase
import com.tomildev.trakii.features.auth.otp.domain.use_case.VerifyOtpUseCase
import com.tomildev.trakii.features.auth.signup.data.SignUpRepositoryImpl
import com.tomildev.trakii.features.auth.signup.domain.SignUpRepository
import com.tomildev.trakii.features.auth.signup.domain.use_case.SendOtpUseCase
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
    fun provideAuthUseCases(
        signUpRepository: SignUpRepository,
        otpRepository: OtpRepository
    ): AuthUseCases {
        return AuthUseCases(
            sendOtp = SendOtpUseCase(signUpRepository),
            verifyOtp = VerifyOtpUseCase(otpRepository, signUpRepository),
            resendOtp = ResendOtpUseCase(otpRepository)
        )
    }
}
