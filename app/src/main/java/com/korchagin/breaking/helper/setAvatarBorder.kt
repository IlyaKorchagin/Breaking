package com.korchagin.breaking.helper

import androidx.compose.ui.graphics.Color
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.ui.theme.Bronze
import com.korchagin.breaking.ui.theme.Default
import com.korchagin.breaking.ui.theme.Gold
import com.korchagin.breaking.ui.theme.Silver
import com.korchagin.breaking.ui.theme.StartAvatar
import com.korchagin.breaking.ui.theme.StartBronze
import com.korchagin.breaking.ui.theme.StartGold

fun setAvatarBorder(curPupil: PupilEntity): List<Color> {
   // Log.d("ILYA","position = ${curPupil.new_position}")
    return when(curPupil.rating.toInt()){
        in 0..9 -> listOf(StartAvatar, Default)
        in 10..19 -> listOf(StartAvatar, Default)
        in 20..29 -> listOf(StartAvatar, Default)
        in 30..39 -> listOf(StartBronze, Bronze)
        in 40..49 -> listOf(StartBronze, Bronze)
        in 50..59 -> listOf(StartBronze, Bronze)
        in 60..69 -> listOf(StartAvatar, Silver)
        in 70..79 -> listOf(StartAvatar, Silver)
        in 80..89 -> listOf(StartGold, Gold)
        in 90..100 -> listOf(StartGold, Gold)
        else -> listOf(StartGold, Gold)
    }
}