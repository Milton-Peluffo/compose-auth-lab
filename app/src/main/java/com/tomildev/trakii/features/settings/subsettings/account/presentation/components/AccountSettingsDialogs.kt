package com.tomildev.trakii.features.settings.subsettings.account.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.dialogs.ConfirmationDialogBase
import com.tomildev.trakii.core.common.presentation.components.dialogs.Dialogs
import com.tomildev.trakii.core.common.presentation.components.textfields.TextFields
import com.tomildev.trakii.core.common.presentation.components.texts.TextError
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.core.common.util.mappers.toUiText
import com.tomildev.trakii.core.domain.model.user.UserValidationError

@Composable
fun AccountLogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialogs.LogOut(
        onConfirm = onConfirm,
        onDismiss = onDismiss
    )
}

@Composable
fun AccountUpdatePasswordDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmationDialogBase(
        title = stringResource(R.string.sub_settings_account_update_password_dialog_title),
        message = stringResource(R.string.sub_settings_account_update_password_dialog_message),
        confirmText = stringResource(R.string.common_btn_confirm),
        dismissText = stringResource(R.string.common_btn_cancel),
        onConfirm = onConfirm,
        onDismiss = onDismiss
    )
}

@Composable
fun AccountEditNameDialog(
    editedName: String,
    nameError: UserValidationError?,
    onNameChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Texts.TitleMedium(text = stringResource(R.string.sub_settings_account_edit_name))
        },
        text = {
            Column {
                TextFields.Name(
                    value = editedName,
                    onValueChange = onNameChange,
                    isError = nameError != null
                )
                nameError?.let {
                    TextError(text = it.toUiText().asString())
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Texts.LabelMedium(text = stringResource(R.string.common_btn_update))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Texts.LabelMedium(text = stringResource(R.string.common_btn_cancel))
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    )
}
