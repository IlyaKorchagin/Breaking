package com.korchagin.breaking.helper

import androidx.compose.ui.graphics.Color
import com.korchagin.breaking.ui.theme.*

fun setPositionColor(index: Int): Color {
    return when(index){
        0 -> OnePosition
        1 -> TwoPosition
        2 -> ThreePosition
        3 -> FourPosition
        4 -> FivePosition
        5 -> SixPosition
        6 -> SevenPosition
        7 -> EightPosition
        8 -> NinePosition
        9 -> TenPosition
        else -> Color.White
    }
}