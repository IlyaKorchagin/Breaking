package com.korchagin.breaking.domain.repository

import com.google.firebase.auth.AuthResult
import com.korchagin.breaking.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String, name: String): Flow<Resource<AuthResult>>
    fun sendEmailVerification(): Boolean
    fun checkVerification(): String
    fun sendPasswordRecovery(email: String): Flow<Resource<Boolean>>
    fun createNewPupil(email: String, name: String)

    fun signOut()
}