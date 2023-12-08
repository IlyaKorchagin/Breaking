package com.korchagin.breaking.model

import java.io.Serializable

// Описание класса Pupils
data class Pupils(
    //---------- Personal information -----------
    var id: String? = "",
    var name: String? = "AAA",
    var email: String? = "",
    var avatar: String? = "",
    var status: Int /* 0 - не указан; 1 - дети до 7 лет; 2 - начинающие; 3 - второгодки
                                   4 - продолжающие; 5 - kidsPro; 6 - преподаватель
                                */ = 0,
    var subscription:Int /* 5 - 1 месяц; 2 - 3 месяца; 3 - 5 месяцев; 4 - 10 месяцев;
                                   10 - без ограничений
                                */ = 0,
    var subscriptionDay:Int = 0,
    var subscriptionMonth:Int = 0,
    var subscriptionYear:Int = 0,

    //---------- Personal information -----------
    //---------- Rating -----------
    var rating:Float = 0.0f,
    var frezze_rating:Float = 0.0f,
    var powermove_rating:Float = 0.0f,
    var ofp_rating:Float = 0.0f,
    var streching_rating :Float = 0.0f,
    var battle_rating:Int = 0,
    var battle_cur_position:Int = 0,
    var battle_new_position:Int = 0,
    var current_position:Int = 0,
    var new_position:Int = 0,

    //---------- Rating -----------
    //---------- FREZZE -----------
    var babyfrezze:Int = 0,
    var chairfrezze:Int = 0,
    var elbowfrezze:Int = 0,
    var headfrezze:Int = 0,
    var headhollowbackfrezze:Int = 0,
    var hollowbackfrezze:Int = 0,
    var invertfrezze:Int = 0,
    var onehandfrezze:Int = 0,
    var shoulderfrezze:Int = 0,
    var turtlefrezze:Int = 0,

    //---------- FREZZE -----------
    //-------- POWER MOVE -------------
    var airflare:Int = 0,
    var backspin:Int = 0,
    var cricket:Int = 0,
    var elbowairflare:Int = 0,
    var flare:Int = 0,
    var jackhammer:Int = 0,
    var halo:Int = 0,
    var headspin:Int = 0,
    var munchmill:Int = 0,
    var ninety_nine:Int = 0,
    var swipes:Int = 0,
    var turtle:Int = 0,
    var ufo:Int = 0,
    var web:Int = 0,
    var windmill:Int = 0,
    var wolf:Int = 0,

    //-------- POWER MOVE -------------
    //----------- OFP -------------
    var angle:Int = 0,
    var bridge:Int = 0,
    var finger:Int = 0,
    var handstand:Int = 0,
    var horizont:Int = 0,
    var pushups:Int = 0,

    //----------- OFP -------------
    //-------- stretching -------------
    var butterfly:Int = 0,
    var fold:Int = 0,
    var shoulders:Int = 0,
    var twine:Int = 0

    //-------- stretching -------------
){

    // Инициализатор root
    constructor(name: String?) : this() {
        id = "Pupils"
        this.name = name
        email = name
        status = 6
        subscription = 10
        subscriptionDay = 15
        subscriptionMonth = 5 //июнь
        subscriptionYear = 2021
        rating = 100.0f
        frezze_rating = 100.0f
        powermove_rating = 100.0f
        ofp_rating = 100.0f
        streching_rating = 100.0f
        battle_rating = 100
        battle_cur_position = 0
        battle_new_position = 0
        current_position = 0
        new_position = 0
        babyfrezze = 78
        chairfrezze = 12
        elbowfrezze = 34
        headfrezze = 71
        headhollowbackfrezze = 70
        hollowbackfrezze = 34
        invertfrezze = 24
        onehandfrezze = 23
        shoulderfrezze = 88
        turtlefrezze = 72
        airflare = 70
        backspin = 90
        cricket = 45
        elbowairflare = 45
        flare = 67
        jackhammer = 12
        halo = 67
        headspin = 99
        munchmill = 67
        ninety_nine = 34
        swipes = 87
        turtle = 67
        ufo = 34
        web = 65
        windmill = 87
        wolf = 56
        angle = 46
        bridge = 91
        finger = 67
        handstand = 69
        horizont = 45
        pushups = 78
        butterfly = 61
        fold = 20
        shoulders = 80
        twine = 56
    }
}