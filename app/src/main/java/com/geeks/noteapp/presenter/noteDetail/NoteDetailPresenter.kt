package com.geeks.noteapp.presenter.noteDetail

import com.geeks.noteapp.App
import com.geeks.noteapp.data.models.NoteModel

class NoteDetailPresenter (
    private val view: NoteDetailContract.View
) : NoteDetailContract.Presenter {

    override fun getNoteById(id: Int) {
        val note = App.appDataBase?.noteDao()?.getById(id)
        note?.let {
            view.showNote(it)
        } ?: view.showError("Заметка не найдена")
    }

    override fun saveNote(note: NoteModel) {
        App.appDataBase?.noteDao()?.insertNote(note)
    }

    override fun updateNote(note: NoteModel) {
        App.appDataBase?.noteDao()?.updateNote(note)
    }
}