package com.tomildev.trakii.core.di

import com.tomildev.trakii.core.data.repository.SessionRepositoryImpl
import com.tomildev.trakii.core.domain.repository.SessionRepository
import com.tomildev.trakii.features.auth.common.data.AuthUserRepositoryImpl
import com.tomildev.trakii.features.auth.common.data.OAuthRepositoryImpl
import com.tomildev.trakii.features.auth.common.domain.AuthUserRepository
import com.tomildev.trakii.features.auth.common.domain.OAuthRepository
import com.tomildev.trakii.features.auth.common.domain.use_case.AuthUseCases
import com.tomildev.trakii.features.auth.common.domain.use_case.AuthWithGoogleUseCase
import com.tomildev.trakii.features.auth.forgot_password.email_request.data.EmailRequestRepositoryImpl
import com.tomildev.trakii.features.auth.forgot_password.email_request.domain.EmailRequestRepository
import com.tomildev.trakii.features.auth.forgot_password.email_request.domain.use_case.SendResetOtpUseCase
import com.tomildev.trakii.features.auth.forgot_password.update_password.data.UpdatePasswordRepositoryImpl
import com.tomildev.trakii.features.auth.forgot_password.update_password.domain.UpdatePasswordRepository
import com.tomildev.trakii.features.auth.forgot_password.update_password.domain.use_case.UpdatePasswordUseCase
import com.tomildev.trakii.features.auth.otp.data.OtpRepositoryImpl
import com.tomildev.trakii.features.auth.otp.domain.OtpRepository
import com.tomildev.trakii.features.auth.otp.domain.use_case.ResendOtpUseCase
import com.tomildev.trakii.features.auth.otp.domain.use_case.VerifyOtpUseCase
import com.tomildev.trakii.features.auth.signin.data.SignInRepositoryImpl
import com.tomildev.trakii.features.auth.signin.domain.SignInRepository
import com.tomildev.trakii.features.auth.signin.domain.use_case.SignInWithEmailUseCase
import com.tomildev.trakii.features.auth.signup.data.SignUpRepositoryImpl
import com.tomildev.trakii.features.auth.signup.domain.SignUpRepository
import com.tomildev.trakii.features.auth.signup.domain.use_case.SendOtpUseCase
import com.tomildev.trakii.features.settings.subsettings.account.domain.use_case.LogoutUseCase
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
    fun provideAuthUserRepository(supabaseClient: SupabaseClient): AuthUserRepository {
        return AuthUserRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideEmailRequestRepository(supabaseClient: SupabaseClient): EmailRequestRepository {
        return EmailRequestRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideUpdatePasswordRepository(supabaseClient: SupabaseClient): UpdatePasswordRepository {
        return UpdatePasswordRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(
        signUpRepository: SignUpRepository,
        signInRepository: SignInRepository,
        otpRepository: OtpRepository,
        oauthRepository: OAuthRepository,
        authUserRepository: AuthUserRepository,
        emailRequestRepository: EmailRequestRepository,
        updatePasswordRepository: UpdatePasswordRepository,
        sessionRepository: SessionRepository
    ): AuthUseCases {
        return AuthUseCases(
            sendOtp = SendOtpUseCase(signUpRepository, authUserRepository),
            sendResetOtp = SendResetOtpUseCase(emailRequestRepository, authUserRepository),
            updatePassword = UpdatePasswordUseCase(updatePasswordRepository),
            verifyOtp = VerifyOtpUseCase(otpRepository, authUserRepository),
            resendOtp = ResendOtpUseCase(otpRepository),
            authWithGoogle = AuthWithGoogleUseCase(oauthRepository),
            signInWithEmail = SignInWithEmailUseCase(signInRepository),
            logout = LogoutUseCase(sessionRepository)
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
