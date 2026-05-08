package com.tomildev.trakii.core.domain.use_case

import com.tomildev.trakii.core.domain.model.error.DataError
import com.tomildev.trakii.core.domain.repository.PasswordRepository
import com.tomildev.trakii.core.domain.util.Result
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(password: String): Result<Unit, DataError.Network> {
        return repository.updatePassword(password)
    }
}
