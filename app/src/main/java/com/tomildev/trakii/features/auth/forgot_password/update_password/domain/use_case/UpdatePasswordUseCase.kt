package com.tomildev.trakii.features.auth.forgot_password.update_password.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.util.Result
import com.tomildev.trakii.features.auth.forgot_password.update_password.domain.UpdatePasswordRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val repository: UpdatePasswordRepository
) {
    suspend operator fun invoke(password: String): Result<Unit, DataError.Network> {
        // Validation could be added here or in the ViewModel
        // Usually we just delegate to the repository after potential domain-level checks
        return repository.updatePassword(password)
    }
}