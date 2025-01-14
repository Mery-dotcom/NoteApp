package com.geeks.noteapp

import android.app.Application
import com.geeks.noteapp.utils.PreferenceHelper

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
    }
}