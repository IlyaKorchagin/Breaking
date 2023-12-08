package com.korchagin.breaking.data.helper

import com.korchagin.breaking.data.storage.models.ElementEntry
import com.korchagin.breaking.domain.model.ElementEntity

class ElementMapper : Mapper<ElementEntry?, ElementEntity> {

    override fun transform(input: ElementEntry?) = ElementEntity(
        image = input?.image.orEmpty(),
        title = input?.title.orEmpty(),
        blockDescription = input?.blockDescription.orEmpty(),
        description = input?.description.orEmpty(),
        progress10 = input?.progress10.orEmpty(),
        progress20 = input?.progress20.orEmpty(),
        progress30 = input?.progress30.orEmpty(),
        progress40 = input?.progress40.orEmpty(),
        progress50 = input?.progress50.orEmpty(),
        progress60 = input?.progress60.orEmpty(),
        progress70 = input?.progress70.orEmpty(),
        progress80 = input?.progress80.orEmpty(),
        progress90 = input?.progress90.orEmpty(),
        progress100 = input?.progress100.orEmpty(),
        videoUrl = input?.videoUrl.orEmpty()
    )
}