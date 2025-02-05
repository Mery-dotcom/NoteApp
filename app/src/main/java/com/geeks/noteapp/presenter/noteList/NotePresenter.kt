package com.geeks.noteapp.presenter.noteList

import com.geeks.noteapp.App
import com.geeks.noteapp.R
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

    override fun onAddClicked() {
        view.navigateToNoteDetail(-1)
    }

    override fun onStoreClicked() {
        view.navigateToStore()
    }

    override fun onDrawerItemClicked(itemId: Int) {
        when (itemId) {
            R.id.nav_first_folder -> view.navigateToFragment(R.id.firstFolderFragment)
            R.id.nav_store -> view.navigateToFragment(R.id.storeFragment)
            R.id.nav_second_folder -> view.navigateToFragment(R.id.secondFolderFragment)
        }
    }

    override fun onNoteClicked(note: NoteModel) {
        view.navigateToNoteDetail(note.id)
    }

    override fun onNoteLongClicked(note: NoteModel) {
        view.showDeleteDialog(note)
    }

}