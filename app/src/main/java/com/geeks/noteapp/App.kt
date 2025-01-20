package com.geeks.noteapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.geeks.notapp.data.db.AppDataBase
import com.geeks.noteapp.utils.PreferenceHelper

class App: Application() {

    companion object{
        var appDataBase: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
        getInstance()
    }

    private fun getInstance(): AppDataBase? {
        if (appDataBase == null){
            appDataBase = applicationContext?.let { context: Context ->
                Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    "note.database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
        return appDataBase
    }
}