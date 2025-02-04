package com.geeks.noteapp.presenter.noteList

import com.geeks.noteapp.data.models.NoteModel

interface NoteContract {
    interface View {
        fun showNotes(notes: List<NoteModel>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadNotes()
        fun deleteNote(note: NoteModel)
    }
}