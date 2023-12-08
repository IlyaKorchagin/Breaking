package com.korchagin.breaking.domain.model

import com.korchagin.breaking.domain.model.PupilEntity


data class PupilState(
    val isLoading: Boolean = false,
    val pupil: PupilEntity? = null,
    val error: String = ""
)
