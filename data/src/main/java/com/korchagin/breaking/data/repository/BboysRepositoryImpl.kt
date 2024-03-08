package com.korchagin.breaking.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.korchagin.breaking.data.helper.BboyMapper
import com.korchagin.breaking.data.storage.models.BboyEntry
import com.korchagin.breaking.domain.common.Result
import com.korchagin.breaking.domain.model.BboyEntity
import com.korchagin.breaking.domain.repository.BboyRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class BboysRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase
): BboyRepository {
    private val BBOYS_KEY = "Bio" //  идентификатор таблицы в БД
    val database = firebaseDatabase.getReference(BBOYS_KEY)

    override suspend fun getAllBboys(rating: String) = callbackFlow<Result<List<BboyEntity>>> {

        database.orderByChild(rating).get().addOnSuccessListener {
         //   Log.d("ILYA", "Got value OnSuccess ${it.value}")
            val bboyList: MutableList<BboyEntity> =
                emptyList<BboyEntity>().toMutableList()
            if (it!!.exists()) {
                for (e in it.children) {
                    val item = e.getValue(BboyEntry::class.java)
                    bboyList.add(BboyMapper().transform(item))
                }
                bboyList.reverse()

                this@callbackFlow.trySendBlocking(
                    Result.success(
                        bboyList
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

    override suspend fun getCurrentBboy(name: String) = callbackFlow<Result<BboyEntity>> {
        this@callbackFlow.trySendBlocking(
            Result.loading(null)
        )
        database.equalTo(name).get().addOnSuccessListener {
       //     Log.d("ILYA", "Got value OnSuccess ${it.value}")
            if (it.exists()) {
                for (e in it.children) {
                    this@callbackFlow.trySendBlocking(
                        Result.success(
                            BboyMapper().transform(e.getValue(BboyEntry::class.java))
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