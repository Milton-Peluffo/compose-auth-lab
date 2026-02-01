package com.tomildev.room_login_compose.core.presentation.components.dialogs

import androidx.compose.runtime.Composable

object Dialogs {

    @Composable
    fun LogOut(
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
    ) {
        ConfirmationDialogBase(
            title = "Confirm logout",
            message = "Are you sure you want to log out of your account?",
            confirmText = "Log out",
            dismissText = "Cancel",
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }

    @Composable
    fun DeleteAccount(
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
    ) {
        ConfirmationDialogBase(
            title = "Delete account",
            message = "This action will permanently delete your account and all associated data. Are you sure you want to continue?",
            confirmText = "Delete account",
            dismissText = "Cancel",
            isWarning = true,
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }
}