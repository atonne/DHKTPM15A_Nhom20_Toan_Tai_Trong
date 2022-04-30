package com.example.dhktpm15a_nhom20_toan_tai_trong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChiTietNode extends AppCompatActivity {
    TextView tvquaylai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_node);
        tvquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChiTietNode.this,trangchu.class);
                startActivity(intent);

            }
        });
    }
    public void anhxa(){
        tvquaylai=findViewById(R.id.tv_quaylai_chitietnode);
    }
}