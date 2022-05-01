package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.google.gson.Gson;

public class ChiTietNote extends AppCompatActivity {
    private TextView tvquaylai,tvluu,nameNote;

    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_node);
        nameNote = findViewById(R.id.tvDetailName);

        Intent intent = getIntent();
        String textNote = intent.getStringExtra("note");
        gson =  new Gson();
        Note note = gson.fromJson(textNote, Note.class);


        nameNote.setText(note.getName());

        anhxa();

    }



    public void anhxa(){
        tvquaylai=findViewById(R.id.tv_quaylai_chitietnode);
        tvluu=findViewById(R.id.tvluu);
        tvquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(ChiTietNode.this,trangchu.class);
//                startActivity(intent);

            }
        });
    }
}