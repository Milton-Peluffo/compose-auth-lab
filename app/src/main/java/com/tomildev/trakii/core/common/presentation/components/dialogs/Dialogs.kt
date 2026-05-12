package com.tomildev.trakii.core.common.presentation.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tomildev.trakii.R

object Dialogs {

    @Composable
    fun LogOut(
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
    ) {
        ConfirmationDialogBase(
            title = stringResource(R.string.dialog_logout_title),
            message = stringResource(R.string.dialog_logout_message),
            confirmText = stringResource(R.string.common_btn_confirm),
            dismissText = stringResource(R.string.common_btn_cancel),
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
            title = stringResource(R.string.dialog_delete_account_title),
            message = stringResource(R.string.dialog_delete_account_message),
            confirmText = stringResource(R.string.dialog_delete_account_confirm),
            dismissText = stringResource(R.string.common_btn_cancel),
            isWarning = true,
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }
}
