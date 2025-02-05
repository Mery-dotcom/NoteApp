package com.geeks.noteapp.sealed

import com.geeks.noteapp.data.models.NoteModel

sealed class NoteState {
    object Loading : NoteState()
    data class Success(val notes: List<NoteModel>) : NoteState()
    data class Error(val message: String) : NoteState()
}
