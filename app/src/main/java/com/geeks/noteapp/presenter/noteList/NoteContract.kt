package com.geeks.noteapp.presenter.noteList

import com.geeks.noteapp.data.models.NoteModel

interface NoteContract {
    interface View {
        fun showNotes(notes: List<NoteModel>)
        fun showError(message: String)
        fun navigateToNoteDetail(noteId: Int?)
        fun navigateToStore()
        fun navigateToFragment(fragmentId: Int)
        fun showDeleteDialog(note: NoteModel)
    }

    interface Presenter {
        fun loadNotes()
        fun deleteNote(note: NoteModel)
        fun onAddClicked()
        fun onStoreClicked()
        fun onDrawerItemClicked(itemId: Int)
        fun onNoteClicked(note: NoteModel)
        fun onNoteLongClicked(note: NoteModel)
    }
}