package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.NoteDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.database.NoteDatabase;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.example.dhktpm15a_nhom20_toan_tai_trong.realtimeDAO.userDAO.RealtimeNote;
import com.google.gson.Gson;

public class ChiTietNote extends AppCompatActivity {
    private TextView tvquaylai,tvluu,nameNote;
    private NoteDAO noteDAO;
    private Gson gson;
    private EditText contentNote;
    private RealtimeNote realtimeNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_node);
        noteDAO = NoteDatabase.getInstance(this).getNoteDAO();
        realtimeNote = new RealtimeNote(this);
        nameNote = findViewById(R.id.tvDetailName);
        contentNote = findViewById(R.id.editTextTextMultiLine3);
        Intent intent = getIntent();
        String textNote = intent.getStringExtra("note");
        gson =  new Gson();
        Note note = gson.fromJson(textNote, Note.class);


        nameNote.setText(note.getName());
        contentNote.setText(note.getContent());
        anhxa();

    }



    public void anhxa(){
        tvquaylai=findViewById(R.id.tv_quaylai_chitietnode);
        tvluu=findViewById(R.id.tvluu);


        tvluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = contentNote.getText().toString();
                Intent intent = getIntent();
                String textNote = intent.getStringExtra("note");
                gson =  new Gson();
                Note note = gson.fromJson(textNote, Note.class);
                note.setContent(content);
                noteDAO.upadteNote(note);

                realtimeNote.addNote(note);




                //NoteDatabase.getInstance(view.getContext()).getNoteDAO().upadteNote(note);
                Intent intent1=new Intent(ChiTietNote.this,TrangChu.class);
                startActivity(intent1);


            }
        });
        quayLai();
    }

    public void quayLai(){
        tvquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChiTietNote.this,TrangChu.class);
                startActivity(intent);

            }
        });
    }
}