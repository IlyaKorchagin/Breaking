package com.korchagin.breaking.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korchagin.breaking.domain.common.Resource
import com.korchagin.breaking.domain.repository.AuthRepository
import com.korchagin.breaking.presentation.model.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    private val _signUpState = Channel<SignInState>()
    val signUpState = _signUpState.receiveAsFlow()

    private val _currentEmail  =  Channel<String>()
    val currentEmail = _currentEmail.receiveAsFlow()

    private val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    private val _passwordRecoveryState = Channel<SignInState>()
    val passwordRecoveryState = _passwordRecoveryState.receiveAsFlow()

    private val _userName = Channel<SignInState>()
    val userName = _userName.receiveAsFlow()

    suspend fun checkVerification(){
        Log.d("ILYA","checkVerification() - ${repository.checkVerification()}")
        _currentEmail.send(repository.checkVerification())
    }
    fun registerUser(email: String, password: String, name: String) = viewModelScope.launch {
        repository.registerUser(email,password, name).collect{result->
            when(result){
                is Resource.Success -> {
                    _signUpState.send(SignInState(isSuccess = "Sign In Success", isVerification = repository.sendEmailVerification()))
                }
                is Resource.Loading -> {
                    _signUpState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {
                    _signUpState.send(SignInState(isError = result.message))
                }
            }
        }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email,password).collect{result->
            when(result){
                is Resource.Success -> {
                    _signInState.send(SignInState(isSuccess = "Sign In Success"))
                }
                is Resource.Loading -> {
                    _signInState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {
                    _signInState.send(SignInState(isError = result.message))
                }
            }
        }
    }

    fun signOut() = viewModelScope.launch {
        repository.signOut()
    }

     fun passwordRecovery(email: String) {
         viewModelScope.launch {
             repository.sendPasswordRecovery(email).collect { result ->
                 when (result) {
                     is Resource.Success -> {
                         _passwordRecoveryState.send(SignInState(isSuccess = "Проверьте свои почту для восстановления пароля"))
                     }
                     is Resource.Loading -> {
                         _passwordRecoveryState.send(SignInState(isLoading = true))
                     }
                     is Resource.Error -> {
                         _passwordRecoveryState.send(SignInState(isError = result.message))
                     }
                 }
             }
         }
     }

}