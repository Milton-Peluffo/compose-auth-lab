package com.tomildev.trakii.core.common.util.mappers

import com.tomildev.trakii.core.data.local.entity.LocalProfileEntity
import com.tomildev.trakii.core.data.remote.dto.ProfileDto
import com.tomildev.trakii.core.domain.model.user.User
import java.time.OffsetDateTime

fun LocalProfileEntity.toDomainUser(): User {
    return User(
        id = id,
        displayName = displayName,
        avatarUrl = avatarUrl.orEmpty(),
        email = email,
        onBoardingCompleted = onboardingCompleted,
        createdAt = createdAt,
        provider = provider,
        isEarlyUser = isEarlyUser,
        accountType = accountType,
        updatedAt = updatedAt
    )
}

fun User.toLocalProfileEntity(): LocalProfileEntity {
    val now = nowTimestamp()

    return LocalProfileEntity(
        id = id,
        email = email,
        displayName = displayName,
        avatarUrl = avatarUrl.ifBlank { null },
        provider = provider,
        isEarlyUser = isEarlyUser,
        accountType = accountType,
        onboardingCompleted = onBoardingCompleted,
        createdAt = createdAt ?: now,
        updatedAt = updatedAt ?: now
    )
}

fun ProfileDto.toLocalProfileEntity(fallback: User): LocalProfileEntity {
    val now = nowTimestamp()

    return LocalProfileEntity(
        id = id,
        email = email ?: fallback.email,
        displayName = display_name ?: fallback.displayName,
        avatarUrl = avatar_url ?: fallback.avatarUrl.ifBlank { null },
        provider = provider ?: fallback.provider,
        isEarlyUser = is_early_user,
        accountType = account_type,
        onboardingCompleted = onboarding_completed || fallback.onBoardingCompleted,
        createdAt = created_at ?: fallback.createdAt ?: now,
        updatedAt = updated_at ?: fallback.updatedAt ?: now
    )
}

fun nowTimestamp(): String = OffsetDateTime.now().toString()
