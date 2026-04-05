package com.tomildev.room_login_compose.features.auth.otp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tomildev.room_login_compose.R

/**
 * A custom numeric keypad component designed for OTP screen.
 * Displays a grid of numbers from 0-9 and a delete action button.
 *
 * @param modifier The [Modifier] to be applied to the keyboard layout.
 * @param onNumberClick Callback invoked when a numeric key is pressed, providing the [String] value of the digit.
 * @param onDeleteClick Callback invoked when the delete key is pressed once.
 * @param onClearAll Callback invoked when the delete key is long-pressed, used to reset the entire input.
 */
@Composable
fun CustomNumericKeyboard(
    modifier: Modifier = Modifier,
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onClearAll: () -> Unit,
) {

    val numbers = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9")
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        numbers.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                row.forEach { number ->
                    NumericKey(text = number, onClick = { onNumberClick(number) })
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NumericKey(text = "", onClick = { })
            NumericKey(text = "0", onClick = { onNumberClick("0") })
            NumericKey(
                icon = painterResource(R.drawable.ic_delete_left),
                onClick = onDeleteClick,
                onLongClick = onClearAll
            )
        }
    }
}