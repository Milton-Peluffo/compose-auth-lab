package com.tomildev.trakii.core.di

import com.tomildev.trakii.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

/**
 * Provides a single [SupabaseClient] instance for the whole app.
 *
 * It uses the project URL and anon key from [BuildConfig] and
 * enables authentication with [Auth] and database operations with [Postgrest].
 */
@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY,
            supabaseUrl = BuildConfig.SUPABASE_URL
        ) {
            install(Auth)
            install(Postgrest)
        }
    }
}
