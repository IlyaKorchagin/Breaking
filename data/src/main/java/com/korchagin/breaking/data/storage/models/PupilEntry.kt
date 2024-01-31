package com.korchagin.breaking.data.storage.models

import com.google.gson.annotations.SerializedName

// Описание класса Pupils
data class PupilEntry(

    //---------> Personal information ---------->
    var id: String? = "",
    var name: String? = "",
    var email: String? = "",
    var avatar: String? = "",

    var status: Int? = 0,
    /*
        0 - не указан;
        1 - дети до 7 лет;
        2 - начинающие;
        3 - второгодки
        4 - продолжающие;
        5 - kidsPro;
        6 - преподаватель
    */

    var subscription: Int? = 0,
    /* ? = 0,
        5 - 1 месяц;
        2 - 3 месяца;
        3 - 5 месяцев;
        4 - 10 месяцев;
        10 - без ограничений
    */

    var subscriptionDay: Int? = 0,
    var subscriptionMonth: Int? = 0,
    var subscriptionYear: Int? = 0,

    //<--------- Personal information <----------


    //---------> Rating ---------->

    var rating: Float? = 0.0f,
    var freeze_rating: Float? = 0.0f,
    var powermove_rating: Float? = 0.0f,
    var ofp_rating: Float? = 0.0f,
    var streching_rating: Float? = 0.0f,
    var battle_rating: Int? = 0,
    var battle_cur_position: Int? = 0,
    var battle_new_position: Int? = 0,
    var new_position: Int? = 0,
    var current_position: Int? = 0,

    //<--------- Rating <----------

    //---------> FREZZE ---------->

    var babyfrezze: Int? = 0,
    var chairfrezze: Int? = 0,
    var elbowfrezze: Int? = 0,
    var headfrezze: Int? = 0,
    var headhollowbackfrezze: Int? = 0,
    var hollowbackfrezze: Int? = 0,
    var invertfrezze: Int? = 0,
    var onehandfrezze: Int? = 0,
    var shoulderfrezze: Int? = 0,
    var turtlefrezze: Int? = 0,

    //<--------- FREZZE <----------

    //-------> POWER MOVE ------------->

    var airflare: Int? = 0,
    var backspin: Int? = 0,
    var cricket: Int? = 0,
    var elbowairflare: Int? = 0,
    var flare: Int? = 0,
    var jackhammer: Int? = 0,
    var halo: Int? = 0,
    var headspin: Int? = 0,
    var munchmill: Int? = 0,
    var ninety_nine: Int? = 0,
    var swipes: Int? = 0,
    var turtle: Int? = 0,
    var ufo: Int? = 0,
    var web: Int? = 0,
    var windmill: Int? = 0,
    var wolf: Int? = 0,

    //<-------- POWER MOVE <-------------

    //-----------> OFP ------------->

    var angle: Int? = 0,
    var bridge: Int? = 0,
    var finger: Int? = 0,
    var handstand: Int? = 0,
    var horizont: Int? = 0,
    var pushups: Int? = 0,
    var sit_ups: Int? = 0,
    var press_up_handstand: Int? = 0,

    //<----------- OFP <-------------

    //--------> stretching ------------>

    var butterfly: Int? = 0,
    var fold: Int? = 0,
    var shoulders: Int? = 0,
    var twine: Int? = 0,

    //<-------- stretching <-------------
)