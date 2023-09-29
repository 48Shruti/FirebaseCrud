package com.shruti.firebasecrud

interface NotesInterface {
    fun update(notesDataClass: NotesDataClass,position:Int)
    fun delete(notesDataClass: NotesDataClass,position: Int)
}