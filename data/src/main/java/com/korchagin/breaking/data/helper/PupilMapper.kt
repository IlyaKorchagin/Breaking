package com.korchagin.breaking.data.helper

import com.korchagin.breaking.data.storage.models.PupilEntry
import com.korchagin.breaking.domain.model.PupilEntity

class PupilMapper : Mapper<PupilEntry?, PupilEntity> {

    override fun transform(input: PupilEntry?) = PupilEntity(
        id = input?.id.orEmpty(),
        name = input?.name.orEmpty(),
        email = input?.email.orEmpty(),
        avatar = input?.avatar.orEmpty(),

        born = input?.born.orEmpty(),
        country = input?.country.orEmpty(),
        city = input?.city.orEmpty(),
        video = input?.video.orEmpty(),

        status = input?.status ?: 0,
        subscription = input?.subscription ?: 0,
        subscriptionDay = input?.subscriptionDay ?: 0,
        subscriptionMonth = input?.subscriptionMonth ?: 0,
        subscriptionYear = input?.subscriptionYear ?: 0,

        rating = input?.rating ?: 0.0f,
        freeze_rating = input?.freeze_rating ?: 0.0f,
        powermove_rating = input?.powermove_rating ?: 0.0f,
        ofp_rating = input?.ofp_rating ?: 0.0f,
        strechingRating = input?.streching_rating ?: 0.0f,
        battle_rating = input?.battle_rating ?: 0,
        battle_cur_position = input?.battle_cur_position ?: 0,
        battle_new_position = input?.battle_new_position ?: 0,
        current_position = input?.current_position ?: 0,
        new_position = input?.new_position ?: 0,

        babyfrezze = input?.babyfrezze ?: 0,
        chairfrezze = input?.chairfrezze ?: 0,
        elbowfrezze = input?.elbowfrezze ?: 0,
        headfrezze = input?.headfrezze ?: 0,
        headhollowbackfrezze = input?.headhollowbackfrezze ?: 0,
        hollowbackfrezze = input?.hollowbackfrezze ?: 0,
        invertfrezze = input?.invertfrezze ?: 0,
        onehandfrezze = input?.onehandfrezze ?: 0,
        shoulderfrezze = input?.shoulderfrezze ?: 0,
        turtlefrezze = input?.turtlefrezze ?: 0,

        airflare = input?.airflare ?: 0,
        backspin = input?.backspin ?: 0,
        cricket = input?.cricket ?: 0,
        elbowairflare = input?.elbowairflare ?: 0,
        flare = input?.flare ?: 0,
        jackhammer = input?.jackhammer ?: 0,
        halo = input?.halo ?: 0,
        headspin = input?.headspin ?: 0,
        munchmill = input?.munchmill ?: 0,
        ninety_nine = input?.ninety_nine ?: 0,
        swipes = input?.swipes ?: 0,
        turtle = input?.turtle ?: 0,
        ufo = input?.ufo ?: 0,
        web = input?.web ?: 0,
        windmill = input?.windmill ?: 0,
        wolf = input?.wolf ?: 0,

        angle = input?.angle ?: 0,
        bridge = input?.bridge ?: 0,
        finger = input?.finger ?: 0,
        handstand = input?.handstand ?: 0,
        horizont = input?.horizont ?: 0,
        situps = input?.sit_ups?:0,
        presstohands = input?.press_up_handstand ?: 0,
        pushups = input?.pushups ?: 0,

        butterfly = input?.butterfly ?: 0,
        fold = input?.fold ?: 0,
        shoulders = input?.shoulders ?: 0,
        twine = input?.twine ?: 0
    )
}