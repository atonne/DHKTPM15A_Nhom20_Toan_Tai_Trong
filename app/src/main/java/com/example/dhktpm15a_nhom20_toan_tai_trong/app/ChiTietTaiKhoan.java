package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;

public class ChiTietTaiKhoan extends AppCompatActivity  implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiettaikhoan);
        TextView tvQuayLai = findViewById(R.id.tv_quaylai_chitietnode);
        tvQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent=new Intent(ChiTietTaiKhoan.this,trang.class);
//                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}