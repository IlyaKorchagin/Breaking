package com.korchagin.breaking.helper

import com.korchagin.breaking.R
import com.korchagin.breaking.domain.model.ElementEntity

fun setDescriptionText(index: Int, element: ElementEntity): String {
    return when(index){
        0 -> element.progress10
        1 -> element.progress20
        2 -> element.progress30
        3 -> element.progress40
        4 -> element.progress50
        5 -> element.progress60
        6 -> element.progress70
        7 -> element.progress80
        8 -> element.progress90
        9 -> element.progress100
        else -> element.progress10
    }
}

fun setDescriptionImage(index: Int): Int {
    return when(index){
        0 -> R.drawable.percent10
        1 -> R.drawable.percent20
        2 -> R.drawable.percent30
        3 -> R.drawable.percent40
        4 -> R.drawable.percent50
        5 -> R.drawable.percent60
        6 -> R.drawable.percent70
        7 -> R.drawable.percent80
        8 -> R.drawable.percent90
        9 -> R.drawable.percent100
        else -> R.drawable.percent10
    }
}