package com.korchagin.breaking.data.helper

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError


interface ValueEventListener {
    fun onDataChange(snapshot: DataSnapshot)
    fun onCancelled(error: DatabaseError)
}