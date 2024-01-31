package com.korchagin.breaking.helper

fun setStatus(status: Int): String {
    return when (status) {
        0 -> "не указана"
        1 -> "Дети до 7 лет"
        2 -> "Начинающие"
        3 -> "Второгодки"
        4 -> "Продолжающие"
        5 -> "Kids Pro"
        6 -> "Администратор"
        else -> "Номинация: не указана"
    }
}