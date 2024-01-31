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
}






