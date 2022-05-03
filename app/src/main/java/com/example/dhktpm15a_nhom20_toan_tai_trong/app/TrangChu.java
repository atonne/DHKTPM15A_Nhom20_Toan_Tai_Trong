package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.dhktpm15a_nhom20_toan_tai_trong.realtimeDAO.userDAO.RealtimeNote;
import com.example.dhktpm15a_nhom20_toan_tai_trong.realtimeDAO.userDAO.RealtimeUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TrangChu extends AppCompatActivity implements View.OnKeyListener {
    private  List<Note> lsNote;

    private NoteDAO noteDAO;
    private UserDAO userDAO;
    private ActiveDAO userActiveDAO;
    private RealtimeUser realtimeUser;
    private RealtimeNote realtimeNote;

    private Context context;

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

        realtimeNote = new RealtimeNote(this);
        realtimeUser = new RealtimeUser(this);

        context = this;

        btnAccount = findViewById(R.id.btnAccount);
        btnAddNote = findViewById(R.id.btnAddNote);
        btnLogOut = findViewById(R.id.btnLogOut);

        txtSearch =  findViewById(R.id.txtSearch);
        txtSearch.setOnKeyListener(this);



        Active userActive = userActiveDAO.getUserActive(true);
        emailUser = userActive.getEmail();




        if(addUserFromRealtime()){


            TextView tvAcc = findViewById(R.id.tvUserLogin);
            tvAcc.setText(emailUser);

            lsNote = new ArrayList<Note>();

            userLogin = userDAO.getUserFromEmail(emailUser);
            addNoteFromRealtime();

            showListNote("");

            clickAccount(userLogin);
            clickbtnLogOut();
            handleBtnAddNote();
        }


    }

    public void addNoteFromRealtime(){


        Query myRef = realtimeNote.getRef();
        myRef.orderByChild("idUser").equalTo(userLogin.getIdUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot s : snapshot.getChildren()){
                    Note n = s.getValue(Note.class);
                    if(n!= null){
                       if( !timNote(n)){
                           noteDAO.addNote(n);
                       }

                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    public boolean timNote(Note note){
        List<Note> ls = noteDAO.getListNoteFromIdUser(userLogin.getIdUser());
        for(Note nDao : ls){
            if(nDao.getIdNote() == note.getIdNote()){
                return true;
            }
        }
        return false;
    }

    NoteAdapter.ResetList reset = new NoteAdapter.ResetList() {
        @Override
        public void resetList() {
            showListNote("");
        }
    };
    public void showListNote(String s) {


        if(s.length() == 0)
            lsNote = noteDAO.getListNoteFromIdUser(userLogin.getIdUser());
        else if(s.equalsIgnoreCase("SEARCH"))
            lsNote = getListSearch();

        if(lsNote != null){
            ListView listView = findViewById(R.id.lvNote);


            NoteAdapter adapter = new NoteAdapter(this,lsNote,R.layout.item_note,reset);
            listView.setAdapter(adapter);




        }
    }

    public List<Note> getListSearch(){

        String textSearch = txtSearch.getText().toString().trim();
       List<Note> ls = noteDAO.getListNoteFromIdUser(userLogin.getIdUser());
        List<Note> lsTemp = new ArrayList<>();
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

                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc muốn đăng xuất?")
                        .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                handleUpdateAllNote();
                                removeAllNoteLocal();
                                showDialogLogOut();

                            }
                        })
                        .setNegativeButton("Huỷ",null)
                        .show();

            }
        });
    }
    public void removeAllNoteLocal(){
        List<Note> lsTemp = noteDAO.getListNoteFromIdUser(userLogin.getIdUser());
        for(Note n : lsTemp){
            noteDAO.deleteNote(n);
        }
    }
    public void showDialogLogOut(){

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
        showListNote("");

        Intent intent = new Intent(getApplicationContext(),ChiTietNote.class);
        Gson gson = new Gson();
        String newNoteString = gson.toJson(newNote);
        intent.putExtra("note",newNoteString);

        startActivity(intent);

    }



    public boolean addUserFromRealtime(){
        realtimeUser.getUserByEmail(emailUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User uActive = userDAO.getUserFromEmail(emailUser);

                for (DataSnapshot s : snapshot.getChildren()){
                    User u = s.getValue(User.class);
                    if(u!= null&&u.getEmail() == emailUser) {

                        if (uActive==null) {
                            userDAO.addUser(u);

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(userDAO.getUserFromEmail(emailUser) == null){
            Toast.makeText(this,"user chua nhap",Toast.LENGTH_SHORT);
            return false;
        }

        return true;

    }

    public void handleUpdateAllNote(){
        List<Note> lsNote = noteDAO.getListNoteFromIdUser(userLogin.getIdUser());
        for (Note n : lsNote){
            realtimeNote.addNote(n);
        }
    }


}