package com.korchagin.breaking.presentation.model

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isVerification: Boolean = false,
    val isError: String? = ""
)
