package com.tomildev.room_login_compose.core.common.presentation.components.snackbars

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

/**
 * Custom implementation of [SnackbarVisuals] that adds a [SnackbarType] property.
 * This allows the Snackbar host to style the snackbar based on its intent (e.g., success, error).
 *
 * @property message The text message to be displayed.
 * @property type The [SnackbarType] representing the visual category of the snackbar.
 * @property actionLabel Optional label for the action button.
 */

data class SnackbarVisualsCustom(
    override val message: String,
    val description: String? = null,
    val type: SnackbarType,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = true,
    override val duration: SnackbarDuration = SnackbarDuration.Short
) : SnackbarVisuals

enum class SnackbarType {
    Success, Info, Warning, Error
}