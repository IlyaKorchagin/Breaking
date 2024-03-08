package com.korchagin.breaking.presentation.screens.common

import android.graphics.drawable.Drawable
import com.korchagin.breaking.R
import com.korchagin.breaking.domain.common.AIRFLARE
import com.korchagin.breaking.domain.common.ANGLE
import com.korchagin.breaking.domain.common.BABY
import com.korchagin.breaking.domain.common.BACKSPIN
import com.korchagin.breaking.domain.common.BRIDGE
import com.korchagin.breaking.domain.common.BUTTERFLY
import com.korchagin.breaking.domain.common.CHAIR
import com.korchagin.breaking.domain.common.CRICKET
import com.korchagin.breaking.domain.common.ELBOW
import com.korchagin.breaking.domain.common.ELBOW_AIRFLARE
import com.korchagin.breaking.domain.common.FINGERS
import com.korchagin.breaking.domain.common.FLARE
import com.korchagin.breaking.domain.common.FOLD
import com.korchagin.breaking.domain.common.HALO
import com.korchagin.breaking.domain.common.HANDSTAND
import com.korchagin.breaking.domain.common.HEAD
import com.korchagin.breaking.domain.common.HEADSPIN
import com.korchagin.breaking.domain.common.HEAD_HOLLOWBACK
import com.korchagin.breaking.domain.common.HOLLOWBACK
import com.korchagin.breaking.domain.common.HORIZONT
import com.korchagin.breaking.domain.common.INVERT
import com.korchagin.breaking.domain.common.JACKHAMMER
import com.korchagin.breaking.domain.common.MUCHMILL
import com.korchagin.breaking.domain.common.NINETYNINE
import com.korchagin.breaking.domain.common.ONE_HAND
import com.korchagin.breaking.domain.common.PRESS_TO_HANDSTAND
import com.korchagin.breaking.domain.common.PUSHUPS
import com.korchagin.breaking.domain.common.SHOULDER
import com.korchagin.breaking.domain.common.SHOULDERS
import com.korchagin.breaking.domain.common.SITUPS
import com.korchagin.breaking.domain.common.SWIPES
import com.korchagin.breaking.domain.common.TURTLE
import com.korchagin.breaking.domain.common.TURTLEMOVE
import com.korchagin.breaking.domain.common.TWINE
import com.korchagin.breaking.domain.common.UFO
import com.korchagin.breaking.domain.common.WEB
import com.korchagin.breaking.domain.common.WINDMILL
import com.korchagin.breaking.domain.common.WOLF
import com.korchagin.breaking.domain.model.PupilEntity

fun Int.getSplashGif(): Int {
    return when(this){
        0 -> R.drawable.break_splash1
        1 -> R.drawable.break_splash2
        2 -> R.drawable.break_splash3
        3 -> R.drawable.break_splash4
        else -> R.drawable.break_splash5
    }
}
fun PupilEntity.getElementRating(title: String): Int {
    return when (title) {
        "Baby" -> babyfrezze
        "Chair" -> chairfrezze
        "Elbow" -> elbowfrezze
        "Head" -> headfrezze
        "HeadHollowback" -> headhollowbackfrezze
        "Hollowback" -> hollowbackfrezze
        "Invert" -> invertfrezze
        "OneHand" -> onehandfrezze
        "Shoulder" -> shoulderfrezze
        "Turtle" -> turtlefrezze

        "Airflare" -> airflare
        "Backspin" -> backspin
        "Cricket" -> cricket
        "ElbowAirflare" -> elbowairflare
        "Flare" -> flare
        "Jackhammer" -> jackhammer
        "Muchmill" -> munchmill
        "Ninetynine" -> ninety_nine
        "Web" -> web
        "Swipes" -> swipes
        "TurtleMove" -> turtle
        "Ufo" -> ufo
        "Windmill" -> windmill
        "Halo" -> halo
        "Wolf" -> wolf
        "HeadSpin" -> headspin

        "Angle" -> angle
        "Bridge" -> bridge
        "Fingers" -> finger
        "Handstand" -> handstand
        "PressToHandstand" -> presstohands
        "PushUps" -> pushups
        "SitUps" -> situps
        "Horizont" -> horizont

        "Butterfly" -> butterfly
        "Fold" -> fold
        "Shoulders" -> shoulders
        "Twine" -> twine

        else -> 0
    }
}

fun PupilEntity.setProgress(elementTitle: String): Float {
    return when (elementTitle) {
        BABY -> babyfrezze.toFloat()
        SHOULDER -> shoulderfrezze.toFloat()
        TURTLE -> turtlefrezze.toFloat()
        HEAD -> headfrezze.toFloat()
        CHAIR -> chairfrezze.toFloat()
        ELBOW -> elbowfrezze.toFloat()
        HEAD_HOLLOWBACK -> headhollowbackfrezze.toFloat()
        ONE_HAND -> onehandfrezze.toFloat()
        INVERT -> invertfrezze.toFloat()
        HOLLOWBACK -> hollowbackfrezze.toFloat()

        BACKSPIN -> backspin.toFloat()
        TURTLEMOVE -> turtle.toFloat()
        HEADSPIN -> headspin.toFloat()
        WINDMILL -> windmill.toFloat()
        MUCHMILL -> munchmill.toFloat()
        HALO -> halo.toFloat()
        FLARE -> flare.toFloat()
        WOLF -> wolf.toFloat()
        WEB -> web.toFloat()
        CRICKET -> cricket.toFloat()
        AIRFLARE -> airflare.toFloat()
        NINETYNINE -> ninety_nine.toFloat()
        UFO -> ufo.toFloat()
        ELBOW_AIRFLARE -> elbowairflare.toFloat()
        JACKHAMMER -> jackhammer.toFloat()
        SWIPES -> swipes.toFloat()

        ANGLE -> angle.toFloat()
        BRIDGE -> bridge.toFloat()
        FINGERS -> finger.toFloat()
        PUSHUPS -> pushups.toFloat()
        SITUPS -> situps.toFloat()
        HANDSTAND -> handstand.toFloat()
        HORIZONT -> horizont.toFloat()
        PRESS_TO_HANDSTAND -> presstohands.toFloat()

        TWINE -> twine.toFloat()
        BUTTERFLY -> butterfly.toFloat()
        FOLD -> fold.toFloat()
        SHOULDERS -> shoulders.toFloat()
        else -> 0.0f
    }
}