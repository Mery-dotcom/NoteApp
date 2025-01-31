package com.geeks.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    var text: String?
    get() = sharedPreferences.getString("text", "")
    set(value) = sharedPreferences.edit().putString("text", value)!!.apply()

    var onBoard: Boolean
        get() = sharedPreferences.getBoolean("onBoard", false)
        set(value) = sharedPreferences.edit().putBoolean("onBoard", value).apply()

    var isRegistered: Boolean
        get() = sharedPreferences.getBoolean("registered", false)
        set(value) = sharedPreferences.edit().putBoolean("registered", value).apply()
}