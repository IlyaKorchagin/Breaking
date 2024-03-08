package com.korchagin.breaking.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.korchagin.breaking.domain.model.BboyEntity
import com.korchagin.breaking.domain.model.ElementEntity
import com.korchagin.breaking.domain.model.PupilEntity

class ElementViewModel: ViewModel() {
    var element by mutableStateOf<ElementEntity?>(null)
        private set
    var elementRating by mutableStateOf<Int>(0)
        private set
    var curPupil by mutableStateOf<PupilEntity?>(null)
        private set
    var clickedPupil by mutableStateOf<PupilEntity?>(null)
        private set
    var bboy by mutableStateOf<BboyEntity?>(null)
        private set

    fun addClickedPupil(pupil: PupilEntity) {
        clickedPupil = pupil
    }
    fun addCurrentPupil(pupil: PupilEntity) {
        curPupil = pupil
    }
    fun addBboy(bboy: BboyEntity) {
        this.bboy = bboy
    }

    fun addElement(currentElement: ElementEntity) {
        element = currentElement
    }

    fun addElementRating(rating: Int) {
        elementRating = rating
    }

    var elementTabPosition by mutableStateOf<Int>(0)

    fun setPosition(position: Int) {
        elementTabPosition = position
    }
}