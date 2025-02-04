package com.geeks.noteapp.presenter.noteList

import com.geeks.noteapp.App
import com.geeks.noteapp.data.models.NoteModel

class NotePresenter(private val view: NoteContract.View) : NoteContract.Presenter {
    override fun loadNotes() {
        App.appDataBase?.noteDao()?.getAll()?.observeForever { notes ->
            view.showNotes(notes)
        }
    }

    override fun deleteNote(note: NoteModel) {
        App.appDataBase?.noteDao()?.deleteNote(note)
        loadNotes()
    }
}