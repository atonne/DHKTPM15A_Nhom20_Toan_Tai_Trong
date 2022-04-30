package com.example.dhktpm15a_nhom20_toan_tai_trong.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.NoteDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.UserDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.User;

@Database(entities = {User.class, Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static final String dbName = "noteDB.db";
    private static NoteDatabase instance;

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,dbName)
                    .allowMainThreadQueries().build();

        }
        return instance;
    }

    public abstract NoteDAO getNoteDAO();
    public abstract UserDAO getUserDAO();

}
