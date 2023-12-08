package com.korchagin.breaking.domain.common

import android.content.Context
import com.korchagin.domain.R
import java.util.*
import javax.inject.Inject

class Preferences @Inject constructor(private val context: Context) {

    companion object {
        private const val DEVICE_ID = "deviceUUID"
    }

    private val path = context.getString(R.string.appPreference)
    private val sharedPreferences = context.getSharedPreferences(path, Context.MODE_PRIVATE)

    // Public Methods

    fun checkFirstRun(): Boolean {
        val key = context.getString(R.string.isFirstRun)
        if (!sharedPreferences.getBoolean(key, true)) {
            return false
        }
        with (sharedPreferences.edit()) {
            putBoolean(key, false)
            commit()
        }
        return true
    }
    fun storeCurrentPupilId(currentPupilId: String) {
        putString(context.getString(R.string.currentPupilId), currentPupilId)
    }

    fun getCurrentPupilId(): String {
        return getString(context.getString(R.string.currentPupilId))
    }


    fun getDeviceId(): String {
        val currentUUID = sharedPreferences.getString(DEVICE_ID, "")
        if (currentUUID.isNullOrEmpty()) {
            val deviceId = UUID.randomUUID().toString()
            putString(DEVICE_ID, deviceId)
            return deviceId
        }
        return currentUUID
    }

    // Private Methods

    private fun putString(key: String, value: String) {
        with (sharedPreferences.edit()) {
            putString(key, value)
            commit()
        }
    }

    private fun getString(key: String): String {
        sharedPreferences.getString(key,"")?.let {
            return it
        }
        return ""
    }
}