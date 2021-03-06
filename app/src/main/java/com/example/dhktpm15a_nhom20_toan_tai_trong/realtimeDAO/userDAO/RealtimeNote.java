package com.example.dhktpm15a_nhom20_toan_tai_trong.realtimeDAO.userDAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RealtimeNote {
    private FirebaseDatabase database ;
    private DatabaseReference myRef;
    private String childName = "Note";
    private Context context;

    public RealtimeNote(Context context) {
        this.context  = context;
        String linkDB = "https://appnote-85a9b-default-rtdb.asia-southeast1.firebasedatabase.app/";
        this.database = FirebaseDatabase.getInstance(linkDB);
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


        return myRef.orderByChild("idNote").startAt(id);
    }

    public List<Note> getAllNote(int idUser){
        List<Note> lsNote = new ArrayList<>();
        myRef.orderByChild("idUser").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()){
                    Note n = s.getValue(Note.class);
                    if(n!= null)
                        lsNote.add(n);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return lsNote;
    }

    public void deleteNoteById(int idNote){
        myRef.child(idNote+"").removeValue();
    }


    public Query getRef(){
        return myRef;
    }
}
