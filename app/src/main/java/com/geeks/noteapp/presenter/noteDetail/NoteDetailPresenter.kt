package com.geeks.noteapp.presenter.noteDetail

import android.os.Bundle
import com.geeks.noteapp.App
import com.geeks.noteapp.data.models.NoteModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailPresenter (
    private val view: NoteDetailContract.View
) : NoteDetailContract.Presenter {

    private var noteId: Int = -1

    override fun loadNote(arguments: Bundle?) {
        noteId = arguments?.getInt("noteId", -1) ?: -1
        if (noteId != -1) {
            getNoteById(noteId)
        }
    }

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

    override fun getCurrentDateTime() {
        val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        view.showDateTime(date, time)
    }

    override fun onSaveClicked(title: String, description: String, date: String, time: String, color: Int) {
        if (title.isEmpty() && description.isEmpty()){
            view.showMessage("Заполните все поля")
            return
        }

        val note = NoteModel(title, description, date, time, color).apply { this.id = noteId }
        if (noteId != -1){
            updateNote(note)
            view.showMessage("Заметка обновлена")
        } else {
            saveNote(note)
            view.showMessage("Заметка добавлена")
        }

        view.navigateBack()
    }

    override fun onBackClicked() {
        view.navigateBack()
    }
}