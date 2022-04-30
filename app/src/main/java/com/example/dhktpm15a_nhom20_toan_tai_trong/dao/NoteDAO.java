package com.example.dhktpm15a_nhom20_toan_tai_trong.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;

import java.util.List;

@Dao
public interface NoteDAO {
    @Query("select * from Note where idNote = :idNote ")
    public Note getNote(int idNote);

    @Query("select * from Note where idUser = :iduser ")
    public List<Note> getListNoteFromIdUser(int iduser);

    @Query("select * from Note where name match :search ")
    public List<Note> getListNoteFromSearch(String search);


    @Insert
    public void addNote(Note note);

    @Delete
    public void deleteNote(Note note);


}
