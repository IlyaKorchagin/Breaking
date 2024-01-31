package com.korchagin.breaking.helper

fun setLevel(rating: Int): String {
    return when (rating) {
        in 0..9 -> "Уровень - 0"
        in 10..19 -> "Уровень - 1"
        in 20..29 -> "Уровень - 2"
        in 30..39 -> "Уровень - 3"
        in 40..49 -> "Уровень - 4"
        in 50..59 -> "Уровень - 5"
        in 60..69 -> "Уровень - 6"
        in 70..79 -> "Уровень - 7"
        in 80..89 -> "Уровень - 8"
        in 90..99 -> "Уровень - 9"
        else -> "Уровень - 10"
    }
}