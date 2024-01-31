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

        BACKSPIN -> {currentPupil.backspin.toFloat()}
        TURTLEMOVE -> {currentPupil.turtle.toFloat()}
        HEADSPIN -> {currentPupil.headspin.toFloat()}
        WINDMILL -> {currentPupil.windmill.toFloat()}
        MUCHMILL -> {currentPupil.munchmill.toFloat()}
        HALO -> {currentPupil.halo.toFloat()}
        FLARE -> {currentPupil.flare.toFloat()}
        WOLF -> {currentPupil.wolf.toFloat()}
        WEB -> {currentPupil.web.toFloat()}
        CRICKET -> {currentPupil.cricket.toFloat()}
        AIRFLARE -> {currentPupil.airflare.toFloat()}
        NINETYNINE -> {currentPupil.ninety_nine.toFloat()}
        UFO -> {currentPupil.ufo.toFloat()}
        ELBOW_AIRFLARE -> {currentPupil.elbowairflare.toFloat()}
        JACKHAMMER -> {currentPupil.jackhammer.toFloat()}

        ANGLE -> {currentPupil.angle.toFloat()}
        BRIDGE -> {currentPupil.bridge.toFloat()}
        FINGERS -> {currentPupil.finger.toFloat()}
        PUSHUPS -> {currentPupil.pushups.toFloat()}
        SITUPS -> {currentPupil.situps.toFloat()}
        HANDSTAND -> {currentPupil.handstand.toFloat()}
        HORIZONT -> {currentPupil.horizont.toFloat()}
        PRESS_TO_HANDSTAND -> {currentPupil.presstohands.toFloat()}

        TWINE -> {currentPupil.twine.toFloat()}
        BUTTERFLY -> {currentPupil.butterfly.toFloat()}
        FOLD -> {currentPupil.fold.toFloat()}
        SHOULDERS -> {currentPupil.shoulders.toFloat()}
        else -> 0.0f
    }
}