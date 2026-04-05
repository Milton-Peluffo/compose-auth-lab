package com.tomildev.room_login_compose.features.common.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tomildev.room_login_compose.R
import com.tomildev.room_login_compose.ui.theme.ExtendedTheme

@Composable
fun RegistrationSuccessDialog(
    modifier: Modifier = Modifier,
    title: String = "Account created successfully!",
    description: String = "Please check your email to verify your account",
    confirmText: String = "OK",
    onConfirm: () -> Unit = {},
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success_animation))
    val shape = RoundedCornerShape(30.dp)

    AlertDialog(
        modifier = modifier.border(
            width = 1.dp,
            shape = shape,
            color = ExtendedTheme.colors.success.copy(alpha = 0.2f)
        ),
        onDismissRequest = { onConfirm() },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        shape = shape,
        icon = {
            LottieAnimation(
                modifier = Modifier.size(120.dp),
                composition = composition,
                iterations = 1
            )
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ExtendedTheme.colors.success,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = confirmText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    )
}