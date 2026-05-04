package com.tomildev.trakii.features.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tomildev.trakii.R
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Alpha
import com.tomildev.trakii.ui.theme.Dimens

@Composable
fun UserAccountHeader(
    modifier: Modifier,
    userName: String,
    userEmail: String,
    onclick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                Dimens.BorderSmall,
                color = MaterialTheme.colorScheme.outline.copy(alpha = Alpha.Overlay),
                shape = MaterialTheme.shapes.medium
            )
            .clickable(onClick = onclick),
        contentAlignment = Alignment.CenterEnd
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.ScreenHorizontalPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Texts.TitleMedium(
                    text = userName,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Texts.LabelMedium(
                    text = userEmail,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = Alpha.Secondary)
                )
            }

            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = Alpha.Secondary),
                contentDescription = null,
            )
        }
    }
}