package com.korchagin.breaking.helper

fun setPositionFontSize(index: Int): Int {
    return when(index){
        in 0..2 -> 20
        in 3..5 -> 18
        in 6..9 -> 16
        in 10..19 -> 15
        else -> 14
    }
}