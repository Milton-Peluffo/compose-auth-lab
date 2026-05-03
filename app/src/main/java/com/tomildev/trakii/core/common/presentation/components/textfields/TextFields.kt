package com.tomildev.trakii.core.common.presentation.components.textfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tomildev.trakii.R

object TextFields {


    //----------------------- USER TEXT FIELDS -----------------------
    @Composable
    fun Name(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        value: String,
        label: String = stringResource(R.string.common_user_label_name),
        isError: Boolean = false,
        leadingIcon: Painter = painterResource(id = R.drawable.ic_user),
    ) {
        TextFieldBase(
            modifier = modifier,
            onValueChange = onValueChange,
            value = value,
            label = label,
            isError = isError,
            leadingIcon = leadingIcon,
        )
    }

    @Composable
    fun Email(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        value: String,
        label: String = stringResource(R.string.common_user_label_email),
        isError: Boolean = false,
        leadingIcon: Painter = painterResource(id = R.drawable.ic_email_outlined),
    ) {
        TextFieldBase(
            modifier = modifier,
            onValueChange = onValueChange,
            value = value,
            label = label,
            isError = isError,
            leadingIcon = leadingIcon,
        )
    }

    @Composable
    fun Password(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        value: String,
        label: String = stringResource(R.string.common_user_label_password),
        isError: Boolean = false,
        leadingIcon: Painter = painterResource(id = R.drawable.ic_lock_outlined),
        isPasswordField: Boolean = true
    ) {
        TextFieldBase(
            modifier = modifier,
            onValueChange = onValueChange,
            value = value,
            label = label,
            isError = isError,
            leadingIcon = leadingIcon,
            isPasswordField = isPasswordField
        )
    }

    @Composable
    fun ConfirmPassword(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        value: String,
        label: String = stringResource(R.string.common_user_label_confirm_password),
        isError: Boolean = false,
        isPasswordField: Boolean = true,
        isPasswordMatch: Boolean = false
    ) {
        val leadingIcon: Painter =
            if (isPasswordMatch) {
                painterResource(id = R.drawable.ic_check_outlined)
            } else {
                painterResource(id = R.drawable.ic_lock_outlined)
            }

        TextFieldBase(
            modifier = modifier,
            onValueChange = onValueChange,
            value = value,
            label = label,
            isError = isError,
            leadingIcon = leadingIcon,
            isPasswordField = isPasswordField
        )
    }
}