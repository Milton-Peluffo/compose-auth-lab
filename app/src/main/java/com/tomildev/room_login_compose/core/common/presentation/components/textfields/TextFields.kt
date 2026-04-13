package com.tomildev.room_login_compose.core.common.presentation.components.textfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.tomildev.room_login_compose.R

object TextFields {


    //----------------------- USER TEXT FIELDS -----------------------

    @Composable
    fun Name(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        value: String,
        label: String,
        isError: Boolean = false,
        leadingIcon: Painter = painterResource(id = R.drawable.ic_user),
        contentDescription: String = "User name text field",
    ) {
        TextFieldBase(
            modifier = modifier,
            onValueChange = onValueChange,
            value = value,
            label = label,
            isError = isError,
            leadingIcon = leadingIcon,
            contentDescription = contentDescription,
        )
    }

    @Composable
    fun Email(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        value: String,
        label: String,
        isError: Boolean = false,
        leadingIcon: Painter = painterResource(id = R.drawable.ic_email),
        contentDescription: String = "Email text field",
    ) {
        TextFieldBase(
            modifier = modifier,
            onValueChange = onValueChange,
            value = value,
            label = label,
            isError = isError,
            leadingIcon = leadingIcon,
            contentDescription = contentDescription,
        )
    }

    @Composable
    fun Password(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        value: String,
        label: String,
        isError: Boolean = false,
        leadingIcon: Painter = painterResource(id = R.drawable.ic_lock_outlined),
        contentDescription: String = "Password text field",
        isPasswordField: Boolean = true
    ) {
        TextFieldBase(
            modifier = modifier,
            onValueChange = onValueChange,
            value = value,
            label = label,
            isError = isError,
            leadingIcon = leadingIcon,
            contentDescription = contentDescription,
            isPasswordField = isPasswordField
        )
    }

    @Composable
    fun ConfirmPassword(
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit,
        value: String,
        label: String,
        isError: Boolean = false,
        contentDescription: String = "Password confirmation text field",
        isPasswordField: Boolean = true,
        isPasswordMatch: Boolean = false
    ) {
        val leadingIcon: Painter =
            if (isPasswordMatch) {
                painterResource(id = R.drawable.ic_check)
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
            contentDescription = contentDescription,
            isPasswordField = isPasswordField
        )
    }
}