package com.example.dhktpm15a_nhom20_toan_tai_trong.realtimeDAO.userDAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RealtimeUser {
    private FirebaseDatabase database ;
    private DatabaseReference myRef;
    private String childName = "Users";
    private Context context;

    public RealtimeUser(Context context) {
        this.context  = context;
        this.database = FirebaseDatabase.getInstance();
        this.myRef  = database.getReference(childName);
    }

    public void addUser(User user){


        myRef.child(user.getIdUser()+"").setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(context,"Lưu thành công",Toast.LENGTH_SHORT);
            }
        });

    }

    public Query getUserByEmail(String email){

        User u;

        return myRef.child(childName).orderByChild("email").equalTo(email);
    }







}
