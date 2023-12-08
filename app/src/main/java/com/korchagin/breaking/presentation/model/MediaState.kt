package com.korchagin.breaking.presentation.model

data class MediaState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)
