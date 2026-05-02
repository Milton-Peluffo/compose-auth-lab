package com.tomildev.trakii.core.common.presentation.components.topbars

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tomildev.trakii.core.common.presentation.components.buttons.BackButton
import com.tomildev.trakii.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackButtonTopBar(
    modifier: Modifier = Modifier,
    backButton: () -> Unit
) {
    TopAppBar(
        modifier = modifier.padding(horizontal = Dimens.ScreenHorizontalPadding),
        title = {},
        navigationIcon = { BackButton(onClick = { backButton() }) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            scrolledContainerColor = androidx.compose.ui.graphics.Color.Transparent
        ),
    )
}