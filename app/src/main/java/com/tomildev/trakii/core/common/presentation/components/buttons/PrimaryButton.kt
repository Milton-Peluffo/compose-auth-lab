package com.tomildev.trakii.core.common.presentation.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {

    val alpha = Alpha.Secondary

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.ButtonHeight),
        onClick = { onClick() },
        shape = MaterialTheme.shapes.extraLarge,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = alpha)
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(Dimens.IconSizeNormal),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = Dimens.BorderNormal
            )
        } else {
            Texts.LabelLarge(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}