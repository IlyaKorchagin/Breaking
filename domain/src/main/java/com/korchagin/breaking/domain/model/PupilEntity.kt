package com.korchagin.breaking.domain.model

data class PupilEntity(

    //---------- Personal information -----------
    var id: String,
    var name: String,
    var born: String,
    var video: String,
    var country: String,
    var city: String,
    var email: String,
    var avatar: String,
    var status: Int,
    /*
    0 - не указан;
    1 - дети до 7 лет;
    2 - начинающие;
    3 - второгодки
    4 - продолжающие;
    5 - kidsPro;
    6 - преподаватель
    */
    var subscription:Int,
    /*
    5 - 1 месяц;
    2 - 3 месяца;
    3 - 5 месяцев;
    4 - 10 месяцев;
    10 - без ограничений
    */
    var subscriptionDay:Int,
    var subscriptionMonth:Int,
    var subscriptionYear:Int,

    //---------- Personal information -----------
    //---------- Rating -----------
    var rating:Float,
    var freeze_rating:Float,
    var powermove_rating:Float,
    var ofp_rating:Float,
    var strechingRating :Float,
    var battle_rating:Int,
    var battle_cur_position:Int,
    var battle_new_position:Int,
    var current_position:Int,
    var new_position:Int,

    //---------- Rating -----------
    //---------- FREZZE -----------
    var babyfrezze:Int,
    var chairfrezze:Int,
    var elbowfrezze:Int,
    var headfrezze:Int,
    var headhollowbackfrezze:Int,
    var hollowbackfrezze:Int,
    var invertfrezze:Int,
    var onehandfrezze:Int,
    var shoulderfrezze:Int,
    var turtlefrezze:Int,

    //---------- FREZZE -----------
    //-------- POWER MOVE -------------
    var airflare:Int,
    var backspin:Int,
    var cricket:Int,
    var elbowairflare:Int,
    var flare:Int,
    var jackhammer:Int,
    var halo:Int,
    var headspin:Int,
    var munchmill:Int,
    var ninety_nine:Int,
    var swipes:Int,
    var turtle:Int,
    var ufo:Int,
    var web:Int,
    var windmill:Int,
    var wolf:Int,

    //-------- POWER MOVE -------------
    //----------- OFP -------------
    var angle:Int,
    var bridge:Int,
    var finger:Int,
    var handstand:Int,
    var horizont:Int,
    var situps:Int,
    var presstohands:Int,
    var pushups:Int,

    //----------- OFP -------------
    //-------- stretching -------------
    var butterfly:Int,
    var fold:Int,
    var shoulders:Int,
    var twine:Int

    //-------- stretching -------------
)
