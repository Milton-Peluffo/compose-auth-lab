package com.tomildev.room_login_compose.core.common.presentation.components.textfields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.tomildev.room_login_compose.R

@Composable
fun TextFieldBase(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String,
    label: String,
    isError: Boolean = false,
    leadingIcon: Painter,
    isPasswordField: Boolean = false
) {

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        label = { Text(text = label) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30),

        visualTransformation = if (isPasswordField && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },

        leadingIcon = {
            Icon(
                painter = leadingIcon,
                contentDescription = null
            )
        },

        trailingIcon = {
            if (isPasswordField) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    val iconId = if (isPasswordVisible) {
                        R.drawable.ic_eye_visibility_tue
                    } else {
                        R.drawable.ic_eye_visibility_false
                    }
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription =
                            if (isPasswordVisible)
                                stringResource(R.string.a11y_hide_password)
                            else
                                stringResource(R.string.a11y_hide_password)
                    )
                }
            }
        },

        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            errorContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.3f),
            errorLabelColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}