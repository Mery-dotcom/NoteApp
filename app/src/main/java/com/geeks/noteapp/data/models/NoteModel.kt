package com.geeks.noteapp.data.models

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteModel")
data class NoteModel(
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    var color: Int ?= Color.WHITE
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
