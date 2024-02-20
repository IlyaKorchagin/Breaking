package com.korchagin.breaking.helper

import com.korchagin.breaking.domain.common.*
import com.korchagin.breaking.domain.model.ElementEntity
import com.korchagin.breaking.domain.model.PupilEntity

fun setElementImage(
    elementTitle: String,
    currentPupil: PupilEntity,
    info: ElementEntity
): String {
    return when (elementTitle) {
        CHAIR -> {
            if (currentPupil.babyfrezze >= 20 && currentPupil.turtlefrezze >= 20) info.image
            else LOCK
        }
        ELBOW -> {
            if (currentPupil.babyfrezze >= 50 && currentPupil.turtlefrezze >= 40) info.image
            else LOCK
        }
        HEAD_HOLLOWBACK -> {
            if (currentPupil.headfrezze >= 70) info.image
            else LOCK
        }
        ONE_HAND -> {
            if (currentPupil.handstand >= 50 ) info.image
            else LOCK
        }
        INVERT -> {
            if (currentPupil.babyfrezze >= 40) info.image
            else LOCK
        }
        HOLLOWBACK -> {
            if (currentPupil.handstand >= 50 && currentPupil.bridge >= 80 && currentPupil.headhollowbackfrezze >= 60) info.image
            else LOCK
        }
        AIRFLARE -> {
            if (currentPupil.handstand >= 80) info.image
            else LOCK
        }
        CRICKET -> {
            if (currentPupil.turtle >= 65) info.image
            else LOCK
        }
        ELBOW_AIRFLARE -> {
            if (currentPupil.elbowfrezze >= 80 && currentPupil.handstand >= 80) info.image
            else LOCK
        }
        FLARE -> {
            if (currentPupil.handstand >= 30 && currentPupil.horizont >=45) info.image
            else LOCK
        }
        HALO -> {
            if (currentPupil.windmill >= 80 && currentPupil.chairfrezze >=50) info.image
            else LOCK
        }
        HEADSPIN -> {
            if (currentPupil.headfrezze >= 60) info.image
            else LOCK
        }
        JACKHAMMER -> {
            if (currentPupil.cricket >= 90) info.image
            else LOCK
        }
        MUCHMILL -> {
            if (currentPupil.windmill >= 80) info.image
            else LOCK
        }
        NINETYNINE -> {
            if (currentPupil.handstand >= 80) info.image
            else LOCK
        }
        TURTLEMOVE -> {
            if (currentPupil.turtlefrezze >= 40) info.image
            else LOCK
        }
        UFO -> {
            if (currentPupil.wolf >= 60 && currentPupil.horizont >=70) info.image
            else LOCK
        }
        WEB -> {
            if (currentPupil.windmill >= 80) info.image
            else LOCK
        }
        WINDMILL -> {
            if (currentPupil.babyfrezze >= 50 && currentPupil.turtlefrezze >=40) info.image
            else LOCK
        }
        WOLF -> {
            if (currentPupil.horizont >= 55) info.image
            else LOCK
        }
        else -> info.image
    }
}