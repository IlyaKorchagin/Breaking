package com.korchagin.breaking.helper

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.ui.theme.*

fun setAvatarBorder(curPupil: PupilEntity): List<Color> {
   // Log.d("ILYA","position = ${curPupil.new_position}")
    return when(curPupil.new_position){
        1 -> listOf(StartGold, Gold)
        2 -> listOf(StartAvatar, Silver)
        3 -> listOf(StartBronze, Bronze)
        else -> listOf(StartAvatar, Default)
    }
}