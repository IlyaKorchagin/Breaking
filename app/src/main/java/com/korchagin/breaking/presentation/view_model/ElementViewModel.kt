package com.korchagin.breaking.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.korchagin.breaking.domain.model.ElementEntity

class ElementViewModel: ViewModel() {
    var element by mutableStateOf<ElementEntity?>(null)
        private set

    fun addElement(currentElement: ElementEntity) {
        element = currentElement
    }

    var elementTabPosition by mutableStateOf<Int>(0)
        private set

    fun setPosition(position: Int) {
        elementTabPosition = position
    }
}