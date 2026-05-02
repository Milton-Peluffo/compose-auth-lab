package com.tomildev.trakii.features.auth.common.components.dividers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.texts.Texts

@Composable
fun AuthHorizontalDivider(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.auth_divider_or)
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 2.2.dp,
            color = MaterialTheme.colorScheme.surface
        )
        Texts.Body(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = text,
            textAlign = TextAlign.Center
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 2.2.dp,
            color = MaterialTheme.colorScheme.surface
        )
    }
}