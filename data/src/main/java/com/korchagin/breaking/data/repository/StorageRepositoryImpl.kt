package com.korchagin.breaking.data.repository

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.korchagin.breaking.domain.common.Resource
import com.korchagin.breaking.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class StorageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : StorageRepository {

    override suspend fun uploadImage(data: ByteArray, email: String): Flow<Resource<String>> {
        return flow {
            Log.d("ILYA","uploadImage")
            val storageRef = firebaseStorage.getReference("ImageDB")
            val avatarRef = storageRef.child("${email}-avatar.jpg")

           // emit(Resource.Loading())
            Log.d("ILYA","putBytes - $data")
            val downloadUrl = avatarRef.putBytes(data).await()
                .storage.downloadUrl.await()
            emit(Resource.Success(downloadUrl.toString()))

        }.catch {
            Log.d("ILYA","downloadUrl error= ${it.message.toString()}")
           // emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun downloadImage(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            emit(Resource.Success(true))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }


}