package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.ActiveDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.NoteDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.UserDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.database.NoteDatabase;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dialog.DialogAddNote;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dialog.DialogLogOut;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Active;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TrangChu extends AppCompatActivity implements View.OnKeyListener {
    private  List<Note> lsNote;

    private NoteDAO noteDAO;
    private UserDAO userDAO;
    private ActiveDAO userActiveDAO;
    private String emailUser;
    private User userLogin;
    private EditText txtSearch;

    private Button btnAccount,btnAddNote,btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu);

        noteDAO = NoteDatabase.getInstance(this).getNoteDAO();
        userDAO = NoteDatabase.getInstance(this).getUserDAO();
        userActiveDAO = NoteDatabase.getInstance(this).getUserActive();

        btnAccount = findViewById(R.id.btnAccount);
        btnAddNote = findViewById(R.id.btnAddNote);
        btnLogOut = findViewById(R.id.btnLogOut);

        txtSearch =  findViewById(R.id.txtSearch);
        txtSearch.setOnKeyListener(this);


        Active userActive = userActiveDAO.getUserActive(true);
        emailUser = userActive.getEmail();

        //String tenUser, String email, int img

        User  u = new User("tp",emailUser,0);

        userDAO.addUser(u);

        userLogin = userDAO.getUserFromEmail(emailUser);


//
//        //String name, String content, String trangThai, int idUser
//
//        noteDAO.addNote(new Note("Thời khoá biểu1","thứ 2 học, thứ 3 đi chơi, thứ 4 học, thứ 5 học","note",userLogin.getIdUser()));
//        noteDAO.addNote(new Note("Thời khoá biểu2","thứ 3 học, thứ 3 đi chơi, thứ 4 học, thứ 5 học","note",userLogin.getIdUser()));
//        noteDAO.addNote(new Note("Thời khoá biểu3","thứ 4 học, thứ 3 đi chơi, thứ 4 học, thứ 5 học","note",userLogin.getIdUser()));
//        noteDAO.addNote(new Note("Thời khoá biểu4","thứ 5 học, thứ 3 đi chơi, thứ 4 học, thứ 5 học","note",userLogin.getIdUser()));

        lsNote = new ArrayList<Note>();

        showListNote("");

        clickAccount(userLogin);
        clickbtnLogOut();
        handleBtnAddNote();
    }

    public void showListNote(String s) {


        if(s.length() == 0)
            lsNote = noteDAO.getListNoteFromIdUser(userLogin.getIdUser());
        else if(s.equalsIgnoreCase("SEARCH"))
            lsNote = getListSearch();

        if(lsNote != null && lsNote.size() != 0){
            ListView listView = findViewById(R.id.lvNote);

            NoteAdapter.ResetList reset = new NoteAdapter.ResetList() {
                @Override
                public void resetList() {
                    showListNote("");
                }
            };
            NoteAdapter adapter = new NoteAdapter(this,lsNote,R.layout.item_note,reset);
            listView.setAdapter(adapter);




        }
    }

    public List<Note> getListSearch(){

        String textSearch = txtSearch.getText().toString().trim();
       List<Note> ls = noteDAO.getListNoteFromIdUser(userLogin.getIdUser());;
        List<Note> lsTemp = new ArrayList<Note>();
        for (Note n : ls){
            if(n.getName().contains(textSearch)){
                lsTemp.add(n);
            }
        }

        return  lsTemp;



    }
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(view.getId() == R.id.txtSearch
                && keyEvent.getAction() == KeyEvent.ACTION_DOWN
                && i == KeyEvent.KEYCODE_ENTER
        ){
            showListNote("SEARCH");


        }
        return false;
    }


    public void clickbtnLogOut(){
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogLogOut();

            }
        });
    }
    public void showDialogLogOut(){
//        DialogLogOut.Logout handleDialog = new DialogLogOut.Logout() {
//            @Override
//            public void logOut() {
//                FirebaseAuth.getInstance().signOut();
//                Active userActive = userActiveDAO.getUserActive(true);
//                if (userActive != null)
//                    userActiveDAO.deleteUserActive(userActive);
//                Intent intent = new Intent(getApplicationContext(), Dangnhap.class);
//                getApplicationContext().startActivity(intent);
//            }
//        };
//
//        final DialogLogOut dialog = new DialogLogOut(getApplicationContext(),handleDialog);
//
//        dialog.show();

        FirebaseAuth.getInstance().signOut();
        Active userActive = userActiveDAO.getUserActive(true);
        if (userActive != null)
            userActiveDAO.deleteUserActive(userActive);
        Intent intent = new Intent(getApplicationContext(), Dangnhap.class);
        getApplicationContext().startActivity(intent);


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

    public void handleBtnAddNote(){
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBtnAddNote();
            }
        });
    }
    private void clickBtnAddNote()  {
        DialogAddNote.FullNameListener listener = new DialogAddNote.FullNameListener() {
            @Override
            public void getNameNote(String nameNote) {
               handleAddNote(nameNote);
            }
        };
        final DialogAddNote dialog = new DialogAddNote(this, listener);

        dialog.show();
    }

    private void handleAddNote(String name) {
        Note newNote = new Note(name,"","note",userLogin.getIdUser());
        noteDAO.addNote(newNote);

        Intent intent = new Intent(getApplicationContext(),ChiTietNote.class);
        Gson gson = new Gson();
        String newNoteString = gson.toJson(newNote);
        intent.putExtra("note",newNoteString);

        startActivity(intent);

    }


}