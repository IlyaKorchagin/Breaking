package com.korchagin.breaking.helper

import androidx.compose.ui.graphics.Color
import com.korchagin.breaking.domain.common.*
import com.korchagin.breaking.ui.theme.Easy
import com.korchagin.breaking.ui.theme.Hard
import com.korchagin.breaking.ui.theme.Medium

fun setElementColor(elementTitle: String): Color {
    return when (elementTitle) {
        BABY, SHOULDER, HEAD, BACKSPIN, TURTLEMOVE, HEADSPIN, WINDMILL, BUTTERFLY, FOLD, TWINE,
        SHOULDERS, PUSHUPS, SITUPS, BRIDGE -> Easy
        TURTLE, HEAD_HOLLOWBACK, SWIPES, MUCHMILL, WEB, WOLF, CRICKET, FLARE, NINETYNINE, HALO, HANDSTAND, FINGERS, ANGLE -> Medium
        CHAIR, ONE_HAND, INVERT, HOLLOWBACK, ELBOW, ELBOW_AIRFLARE, AIRFLARE, JACKHAMMER, UFO, HORIZONT, PRESS_TO_HANDSTAND -> Hard
        else -> Color.Black
    }
}