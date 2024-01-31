package com.korchagin.breaking.data.helper

import com.korchagin.breaking.data.storage.models.BboyEntry
import com.korchagin.breaking.domain.model.BboyEntity

class BboyMapper : Mapper<BboyEntry?, BboyEntity> {

    override fun transform(input: BboyEntry?) = BboyEntity(
        name = input?.name ?: "",
        avatar = input?.avatar ?: "",
        rating = input?.rating ?: "0",
        country = input?.country ?: "",
        born = input?.born ?: "",
        description = input?.description ?: "",
        video = input?.video ?: "",
        shortdescription = input?.shortdescription ?: ""
    )
}