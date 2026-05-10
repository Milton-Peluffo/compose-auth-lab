package com.tomildev.trakii.core.common.presentation.components.textfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tomildev.trakii.R

object TextFields {
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
}