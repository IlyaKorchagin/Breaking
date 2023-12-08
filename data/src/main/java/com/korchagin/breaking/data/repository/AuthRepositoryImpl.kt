package com.korchagin.breaking.data.repository

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.korchagin.breaking.common.Resource
import com.korchagin.breaking.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        //    Log.d("ILYA", "firebaseAuth.currentUser = ${firebaseAuth.currentUser}")
         /*   Log.d(
                "ILYA",
                "isEmailVerified = ${firebaseAuth.currentUser?.isEmailVerified ?: "noVerified"}"
            )*/
            if(firebaseAuth.currentUser?.isEmailVerified == true) {
                emit(Resource.Success(result))
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
      //      Log.d("ILYA", "firebaseAuth.currentUser = ${firebaseAuth.currentUser}")
      //      Log.d("ILYA", "Resource.Success(result) = $result")
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun sendEmailVerification(): Boolean {
        val user = firebaseAuth.currentUser
     //   Log.d("ILYA", "user = $user")
        var result = false
        assert(user != null)
        user!!.sendEmailVerification().addOnCompleteListener { task ->
            result = task.isSuccessful
        }
        return result
    }

    override fun checkVerification(): String {
        firebaseAuth.currentUser?.getIdToken(true)
        firebaseAuth.currentUser?.reload()
     /*   Log.d("ILYA", "currentUser =  ${firebaseAuth.currentUser ?: "noUser"}")
        Log.d("ILYA", "currentUserEmail =  ${firebaseAuth.currentUser?.email ?: "noEmail"}")
        Log.d(
            "ILYA",
            "isEmailVerified = ${firebaseAuth.currentUser?.isEmailVerified ?: "noVerified"}"
        )*/
        return if (firebaseAuth.currentUser?.isEmailVerified == true) {
            firebaseAuth.currentUser!!.email.toString()
        } else ""
    }


    override fun sendPasswordRecovery(email: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(Resource.Success(true))
        }.catch {
           emit(Resource.Error(it.message.toString()))
        }
    }

}