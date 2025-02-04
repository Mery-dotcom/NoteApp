package com.geeks.noteapp.presenter.noteDetail

import com.geeks.noteapp.data.models.NoteModel

interface NoteDetailContract {
    interface View {
        fun showSaved(note: NoteModel)
        fun showError(message: String)
        fun noteUpdated()
    }

    interface Presenter {
        fun saveNote(note: NoteModel)
        fun updateNote(note: NoteModel)
    }
}