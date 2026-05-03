package com.tomildev.trakii.core.common.presentation.components.topbars

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tomildev.trakii.core.common.presentation.components.buttons.BackButton
import com.tomildev.trakii.core.common.presentation.components.texts.Texts
import com.tomildev.trakii.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackbuttonTitleTopBar(
    modifier: Modifier = Modifier,
    title: String,
    backButton: () -> Unit
) {

    CenterAlignedTopAppBar(
        modifier = modifier.padding(horizontal = Dimens.ScreenHorizontalPadding),
        navigationIcon = { BackButton(onClick = { backButton() }) },
        title = {
            Texts.TitleLarge(modifier = Modifier, title)
        },
        windowInsets = TopAppBarDefaults.windowInsets,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
    )
}