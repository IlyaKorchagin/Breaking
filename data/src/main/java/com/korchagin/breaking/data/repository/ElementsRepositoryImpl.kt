package com.korchagin.breaking.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.korchagin.breaking.common.Result
import com.korchagin.breaking.data.helper.ElementMapper
import com.korchagin.breaking.data.storage.models.ElementEntry
import com.korchagin.breaking.domain.model.ElementEntity
import com.korchagin.breaking.domain.repository.ElementsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ElementsRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase
): ElementsRepository {
    private val FREEZE_KEY = "Freeze" //  идентификатор таблицы в БД
    private val POWER_KEY = "PowerMove" //  идентификатор таблицы в БД
    private val OFP_KEY = "OFP" //  идентификатор таблицы в БД
    private val STRETCH_KEY = "Stretch" //  идентификатор таблицы в БД
    val freezeDB = firebaseDatabase.getReference(FREEZE_KEY)
    val powerDB = firebaseDatabase.getReference(POWER_KEY)
    val ofpDB = firebaseDatabase.getReference(OFP_KEY)
    val stretchDB = firebaseDatabase.getReference(STRETCH_KEY)

    override suspend fun getFreezeElements() = callbackFlow<Result<List<ElementEntity>>> {
        freezeDB.get().addOnSuccessListener {
            var freezeList: MutableList<ElementEntity> =
                emptyList<ElementEntity>().toMutableList()
            if (it!!.exists()) {
                for (e in it.children) {
                    val item = e.getValue(ElementEntry::class.java)
                    freezeList.add(ElementMapper().transform(item))
                }
         //       Log.d("ILYA", "freezeList - $freezeList")
                this@callbackFlow.trySendBlocking(
                    Result.success(
                        freezeList
                    )
                )

            }

        }.addOnFailureListener {
       //     Log.d("ILYA", "Error getting data", it)
        }

        awaitClose {
          //  Log.d("ILYA", "awaitClose")


        }
    }

    override suspend fun getPowerMoveElements() = callbackFlow<Result<List<ElementEntity>>> {
        powerDB.get().addOnSuccessListener {
            var powerList: MutableList<ElementEntity> =
                emptyList<ElementEntity>().toMutableList()
            if (it!!.exists()) {
                for (e in it.children) {
                    val item = e.getValue(ElementEntry::class.java)
                    powerList.add(ElementMapper().transform(item))
                }
                //       Log.d("ILYA", "freezeList - $freezeList")
                this@callbackFlow.trySendBlocking(
                    Result.success(
                        powerList
                    )
                )

            }

        }.addOnFailureListener {}
        awaitClose {}
    }

    override suspend fun getOfpElements() = callbackFlow<Result<List<ElementEntity>>> {
        ofpDB.get().addOnSuccessListener {
            var ofpList: MutableList<ElementEntity> =
                emptyList<ElementEntity>().toMutableList()
            if (it!!.exists()) {
                for (e in it.children) {
                    val item = e.getValue(ElementEntry::class.java)
                    ofpList.add(ElementMapper().transform(item))
                }
                this@callbackFlow.trySendBlocking(
                    Result.success(
                        ofpList
                    )
                )

            }

        }.addOnFailureListener {}
        awaitClose {}
    }

    override suspend fun getStretchElements() = callbackFlow<Result<List<ElementEntity>>> {
        stretchDB.get().addOnSuccessListener {
            var stretchList: MutableList<ElementEntity> =
                emptyList<ElementEntity>().toMutableList()
            if (it!!.exists()) {
                for (e in it.children) {
                    val item = e.getValue(ElementEntry::class.java)
                    stretchList.add(ElementMapper().transform(item))
                }
                //       Log.d("ILYA", "freezeList - $freezeList")
                this@callbackFlow.trySendBlocking(
                    Result.success(
                        stretchList
                    )
                )

            }

        }.addOnFailureListener {}
        awaitClose {}
    }



}