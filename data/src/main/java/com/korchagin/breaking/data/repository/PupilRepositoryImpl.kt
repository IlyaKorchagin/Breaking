package com.korchagin.breaking.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.korchagin.breaking.common.Result
import com.korchagin.breaking.data.helper.PupilMapper
import com.korchagin.breaking.data.storage.models.PupilEntry
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.domain.repository.PupilRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.HashMap
import javax.inject.Inject

class PupilRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase
): PupilRepository {
    private val PUPILS_KEY = "Pupils" //  идентификатор таблицы в БД
    val database = firebaseDatabase.getReference(PUPILS_KEY)

    override suspend fun getAllPupils(filter: String) = callbackFlow<Result<List<PupilEntity>>> {

        database.orderByChild(filter).get().addOnSuccessListener {
       //     Log.d("ILYA", "Got value OnSuccess ${it.value}")
            var pupilList: MutableList<PupilEntity> =
                emptyList<PupilEntity>().toMutableList()
            if (it!!.exists()) {
                for (e in it.children) {
                    val item = e.getValue(PupilEntry::class.java)
                    pupilList.add(PupilMapper().transform(item))
                }
                pupilList.reverse()

                this@callbackFlow.trySendBlocking(
                    Result.success(
                        pupilList
                    )
                )

            }

        }.addOnFailureListener {
      //      Log.d("ILYA", "Error getting data", it)
        }

        awaitClose {
       //     Log.d("ILYA", "awaitClose")


        }
    }

    override suspend fun updateAvatar(
        userId: String,
        hashMap: HashMap<String?, Any?>
    ) = callbackFlow<Result<Boolean>> {
        Log.d("ILYA", "update hashMap - $hashMap")
            database.child(userId).updateChildren(hashMap)
                .addOnCompleteListener { if(it.isSuccessful){
                    Log.d("ILYA", "update Success")
                    this@callbackFlow.trySendBlocking(
                        Result.success(true)
                    )
                } }
                .addOnFailureListener {  this@callbackFlow.trySendBlocking(
                    Result.error("error",null)
                ) }

        awaitClose {
     //       Log.d("ILYA", "awaitClose")

        }
    }

    override suspend fun getCurrentPupil(email: String) = callbackFlow<Result<PupilEntity>> {
        this@callbackFlow.trySendBlocking(
            Result.loading(null)
        )
        database.orderByChild("email").equalTo(email).get().addOnSuccessListener {
       //     Log.d("ILYA", "Got value OnSuccess ${it.value}")
            if (it.exists()) {
                for (e in it.children) {
                    this@callbackFlow.trySendBlocking(
                        Result.success(
                            PupilMapper().transform(e.getValue(PupilEntry::class.java))
                        )
                    )
                }
            }

        }.addOnFailureListener {
       //     Log.d("ILYA", "Error getting data", it)
        }

        awaitClose {
      //      Log.d("ILYA", "awaitClose")

        }
    }

}