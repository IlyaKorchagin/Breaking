package com.korchagin.breaking.presentation.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.korchagin.breaking.domain.model.ElementEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _sharedState = MutableStateFlow(ElementEntity())
    val sharedState = _sharedState.asStateFlow()

    fun updateState(element: ElementEntity) {
        _sharedState.value = element
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel cleared")
    }
}