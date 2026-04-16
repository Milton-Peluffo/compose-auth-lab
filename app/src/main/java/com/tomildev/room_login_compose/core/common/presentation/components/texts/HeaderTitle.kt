package com.tomildev.room_login_compose.core.common.presentation.components.texts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.tomildev.room_login_compose.core.common.presentation.components.spacers.VerticalSpacer
import com.tomildev.room_login_compose.ui.theme.Dimens

@Composable
fun HeaderTitle(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    title: String,
    subtitle: String? = null
) {

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Texts.Headline(
            text = title,
            textAlign = textAlign,
        )
    }

    if (subtitle != null) {
        VerticalSpacer(Dimens.SpacingSmall)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = subtitle,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}