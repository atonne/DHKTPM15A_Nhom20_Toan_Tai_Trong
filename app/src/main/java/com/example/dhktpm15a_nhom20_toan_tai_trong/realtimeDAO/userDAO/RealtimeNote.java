package com.example.dhktpm15a_nhom20_toan_tai_trong.realtimeDAO.userDAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RealtimeNote {
    private FirebaseDatabase database ;
    private DatabaseReference myRef;
    private String childName = "Note";
    private Context context;

    public RealtimeNote(Context context) {
        this.context  = context;
        this.database = FirebaseDatabase.getInstance();
        this.myRef  = database.getReference(childName);
    }

    public void addNote(Note note){


        myRef.child(note.getIdNote()+"").setValue(note, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(context,"Lưu thành công",Toast.LENGTH_SHORT);
            }
        });

    }

    public Query getNoteById(int id){


        return myRef.child(id+"").orderByChild("id").equalTo(id);
    }

    public Query getAllNote(){
        return myRef;
    }

    public void deleteNoteById(int idNote){
        myRef.child(idNote+"").removeValue();
    }



}
