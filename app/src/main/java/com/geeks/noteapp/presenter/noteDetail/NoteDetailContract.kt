package com.geeks.noteapp.presenter.noteDetail

import android.os.Bundle
import com.geeks.noteapp.data.models.NoteModel

interface NoteDetailContract {
    interface View {
        fun showSaved(note: NoteModel)
        fun showError(message: String)
        fun noteUpdated()
        fun showNote(note: NoteModel)
        fun showDateTime(date: String, time: String)
        fun showMessage(message: String)
        fun navigateBack()
    }

    interface Presenter {
        fun saveNote(note: NoteModel)
        fun updateNote(note: NoteModel)
        fun getNoteById(id: Int)
        fun getCurrentDateTime()
        fun onSaveClicked(title: String, description: String, date: String, time: String, color: Int)
        fun loadNote(arguments: Bundle?)
        fun onBackClicked()
    }
}