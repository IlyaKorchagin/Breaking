package com.korchagin.breaking.data.storage.localStorage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.korchagin.breaking.data.helper.PupilMapper
import com.korchagin.breaking.data.storage.UserStorage
import com.korchagin.breaking.data.storage.models.PupilEntry
import com.korchagin.breaking.data.storage.models.User
import com.korchagin.breaking.domain.model.PupilEntity
import com.korchagin.breaking.domain.model.PupilState
import kotlinx.coroutines.*

class UserStorageImpl : UserStorage {

    private val PUPILS_KEY = "Pupils" //  идентификатор таблицы в БД




    override suspend fun getCurrentPupil(liveData: MutableLiveData<PupilState>): PupilEntity {
        var curPupil: PupilEntry? = null
        val database = Firebase.database


        database.getReference(PUPILS_KEY).orderByChild("email").equalTo("pymus@mail.ru")
            .addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

        //        Log.d("ILYA","onDataChange")
                for (ds in dataSnapshot.children) {
                    val pupil = ds.getValue(PupilEntry::class.java)
                    if (pupil != null) {
                        curPupil = pupil
                        val pupilState = PupilState(pupil = PupilMapper().transform(curPupil))
                        liveData.postValue(pupilState)
         //               Log.d("ILYA","$curPupil")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
       //         Log.d("ILYA","onCancelled")
            }
        })

    //    Log.d("ILYA","Send $curPupil")

        return PupilMapper().transform(curPupil)

    }

    override fun getAllPupils(): List<User> {
        return listOf(
            User(name = "Tom", age = 10, rating = 50, avatar = 1),
            User(name = "Bob", age = 11, rating = 25, avatar = 2),
            User(name = "Anna", age = 12, rating = 30, avatar = 3),
            User(name = "Liza", age = 8, rating = 80, avatar = 4),
            User(name = "Elena", age = 9, rating = 10, avatar = 5),
            User(name = "Ben", age = 14, rating = 5, avatar = 6)
        )
    }



        /* private fun fetchUser(firebaseDatabase: FirebaseDatabase, userId: String) {
             val coroutineScope = CoroutineScope(Dispatchers.IO)
             coroutineScope?.launch() {
                 val defer = async(Dispatchers.IO) {
                     firebaseDatabase.getReference(PUPILS_KEY).orderByChild("email").equalTo(userId)
                         .awaitSingleValue()
                 }
                 when (val result = defer.await()) {
                     is RealtimeDatabaseValueResult.Success -> {
                         val dataSnapshot: DataSnapshot = result.dataSnapshot
                         for (childSnapshot in dataSnapshot.children) {
                             val pupil = childSnapshot.getValue(
                                 PupilEntry::class.java
                             )
                             if (pupil != null) {
                                 Log.d("ILYA", "name - ${pupil.name}")
                                 curPupil = pupil
                             }
                         }
                         Log.d("ILYA", "Get value - $dataSnapshot")


                     }
                     is RealtimeDatabaseValueResult.Error -> {
                         val error: DatabaseError = result.error
                         Log.d("ILYA", "Error - ${error.message}")
                     }
                 }
             }
         }*/
}


/*
sealed class RealtimeDatabaseValueResult {
    class Success(val dataSnapshot: DataSnapshot) : RealtimeDatabaseValueResult()
    class Error(val error: DatabaseError) : RealtimeDatabaseValueResult()
}

*/
/**
 * Perform a addListenerForSingleValueEvent call on a databaseReference in a suspend function way
 * @param onCancellation action to perform if there is a cancellation
 *//*

@ExperimentalCoroutinesApi
suspend fun Query.awaitSingleValue(onCancellation: ((cause: Throwable) -> Unit)? = null) =
    suspendCancellableCoroutine<RealtimeDatabaseValueResult> { continuation ->

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                continuation.resume(
                    RealtimeDatabaseValueResult.Error(error = error),
                    onCancellation
                )
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resume(
                    RealtimeDatabaseValueResult.Success(snapshot),
                    onCancellation
                )
            }
        }

        // add listener like you normally do
        addListenerForSingleValueEvent(valueEventListener)

        // in case the job, coroutine, etc. is cancelled, we remove the current event listener
        continuation.invokeOnCancellation { removeEventListener(valueEventListener) }
    }
*/



