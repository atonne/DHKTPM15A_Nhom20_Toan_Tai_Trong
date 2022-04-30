package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.NoteDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.UserDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.database.NoteDatabase;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TrangChu extends AppCompatActivity {
    private  List<Note> lsNote;

    private NoteDAO noteDAO;
    private UserDAO userDAO;
    private String emailUser;
    private User userLogin;

    private Button btnAccount,btnAddNote,btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu);

        noteDAO = NoteDatabase.getInstance(this).getNoteDAO();
        userDAO = NoteDatabase.getInstance(this).getUserDAO();

        btnAccount = findViewById(R.id.btnAccount);
        btnAddNote = findViewById(R.id.btnAddNote);
        btnLogOut = findViewById(R.id.btnLogOut);


        Intent intent = getIntent();
        emailUser = intent.getStringExtra("emailUser");

        //String tenUser, String email, int img

        User  u = new User("tp",emailUser,0);

        userDAO.addUser(u);

        userLogin = userDAO.getUserFromEmail(emailUser);



        //String name, String content, String trangThai, int idUser

        noteDAO.addNote(new Note("Thời khoá biểu1","thứ 2 học, thứ 3 đi chơi, thứ 4 học, thứ 5 học","note",userLogin.getIdUser()));
        noteDAO.addNote(new Note("Thời khoá biểu2","thứ 3 học, thứ 3 đi chơi, thứ 4 học, thứ 5 học","note",userLogin.getIdUser()));
        noteDAO.addNote(new Note("Thời khoá biểu3","thứ 4 học, thứ 3 đi chơi, thứ 4 học, thứ 5 học","note",userLogin.getIdUser()));
        noteDAO.addNote(new Note("Thời khoá biểu4","thứ 5 học, thứ 3 đi chơi, thứ 4 học, thứ 5 học","note",userLogin.getIdUser()));

        lsNote = new ArrayList<Note>();

        showListNote();

        clickAccount(userLogin);
        clickbtnLogOut();
    }

    private void showListNote() {



        lsNote = noteDAO.getListNoteFromIdUser(userLogin.getIdUser());
        if(lsNote != null && lsNote.size() != 0){
            ListView listView = findViewById(R.id.lvNote);

            NoteAdapter adapter = new NoteAdapter(this,lsNote,R.layout.item_note);
            listView.setAdapter(adapter);


        }
    }


    public void clickbtnLogOut(){
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Dangnhap.class);
                startActivity(intent);
            }
        });
    }

    public void clickAccount(User user){

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChiTietTaiKhoan.class);
                Gson gson = new Gson();
                String stringUser = gson.toJson(user);
                intent.putExtra("userLogin",stringUser);
                startActivity(intent);
            }
        });

    }



}