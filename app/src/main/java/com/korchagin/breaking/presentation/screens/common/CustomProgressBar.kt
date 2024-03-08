package com.korchagin.breaking.presentation.screens.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomProgressBar(
    modifier: Modifier, backgroundColor: Color, foregroundColor: Brush, percent: Int,
    isShownText: Boolean
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(key1 = percent) {
        animatedProgress.animateTo(percent.toFloat(), animationSpec = tween(durationMillis = 2000))
    }
    Box(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()

    ) {
        Box(
            modifier = modifier
                .background(foregroundColor)
                .fillMaxWidth(animatedProgress.value / 100f)
        ) {
            if (isShownText && percent > 9) Text(
                text = "${percent}%",
                modifier = Modifier.align(alignment = Alignment.Center),
                fontSize = 13.sp,
                color = Color.Black,
            )
        }
    }
}