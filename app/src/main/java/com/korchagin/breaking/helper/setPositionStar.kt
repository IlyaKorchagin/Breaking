package com.korchagin.breaking.helper

import com.korchagin.breaking.R

fun setPositionStar(index: Int): Int {
    return when(index){
        0 -> R.drawable.star_top_1
        1 -> R.drawable.star_top_2
        2 -> R.drawable.star_top_3
        else -> R.drawable.star_default
    }
}