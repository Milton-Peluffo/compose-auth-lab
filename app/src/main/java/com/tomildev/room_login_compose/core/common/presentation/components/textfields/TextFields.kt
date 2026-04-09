package com.tomildev.room_login_compose.core.common.presentation.components.textfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.tomildev.room_login_compose.R

object TextFields {

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
}