package com.korchagin.breaking.helper

import com.korchagin.breaking.domain.common.*
import com.korchagin.breaking.domain.model.PupilEntity

fun setProgress(elementTitle: String, currentPupil: PupilEntity):Float{
    return when(elementTitle){
        BABY -> {currentPupil.babyfrezze.toFloat()}
        SHOULDER -> {currentPupil.shoulderfrezze.toFloat()}
        TURTLE -> {currentPupil.turtlefrezze.toFloat()}
        HEAD -> {currentPupil.headfrezze.toFloat()}
        CHAIR -> {currentPupil.chairfrezze.toFloat()}
        ELBOW -> {currentPupil.elbowfrezze.toFloat()}
        HEAD_HOLLOWBACK -> {currentPupil.headhollowbackfrezze.toFloat()}
        ONE_HAND -> {currentPupil.onehandfrezze.toFloat()}
        INVERT -> {currentPupil.invertfrezze.toFloat()}
        HOLLOWBACK -> {currentPupil.hollowbackfrezze.toFloat()}
        else -> 0.0f
    }
}