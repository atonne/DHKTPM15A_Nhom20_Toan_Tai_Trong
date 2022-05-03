package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.ActiveDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.NoteDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.UserDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.database.NoteDatabase;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Active;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.User;

import java.io.IOException;

import gun0912.tedbottompicker.TedBottomPicker;



public class ChiTietTaiKhoan extends AppCompatActivity  implements View.OnClickListener{
    private ImageButton imgB;
    private TextView tvName;
    private NoteDAO noteDAO;
    private UserDAO userDAO;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPass;
    private String emailUser;
    private User userLogin;
    private ActiveDAO userActiveDAO;
    private ImageButton btnEdtName, btnEdtEmail, btnEdtPass;
    private Button btnLuu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiettaikhoan);
        TextView tvQuayLai = findViewById(R.id.tv_quaylai_chitietnode);
        tvName = findViewById(R.id.tv_name);
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_matkhau);
        btnEdtEmail = findViewById(R.id.imgB_editemail);
        btnEdtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtEmail.setEnabled(true);
            }
        });
        

        btnEdtName = findViewById(R.id.imgB_editname);
        btnEdtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setEnabled(true);
            }
        });

        edtName.setEnabled(false);
        edtEmail.setEnabled(false);
        edtPass.setEnabled(false);
        //noteDAO = NoteDatabase.getInstance(this).getNoteDAO();
        //userDAO = NoteDatabase.getInstance(this).getUserDAO();
        //userActiveDAO = NoteDatabase.getInstance(this).getUserActive();
        //Active userActive = userActiveDAO.getUserActive(true);
        //emailUser = userActive.getEmail();
        //User u = new User("tp",emailUser,0);

        //userDAO.addUser(u);

        //userLogin = userDAO.getUserFromEmail(emailUser);
        //tvName.setText(userLogin.getTenUser());
        //edtName.setText(userLogin.getTenUser());
        //edtEmail.setText(userLogin.getEmail());

        tvQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChiTietTaiKhoan.this,TrangChu.class);
                startActivity(intent);
            }
        });
        imgB = findViewById(R.id.imgB_avt);
        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        //edtPass.setText(userLogin.);

        btnLuu = findViewById(R.id.btnLuu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkThongTin()){
                   String tenUser = edtName.getText().toString();
                   String email = edtEmail.getText().toString();
                   String pass = edtPass.getText().toString();
                  //User user = new User()
                }
            }
        });


    }
    private void openImagePicker(){
        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(Uri uri) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                    Bitmap originalBitmap = bitmap;
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                            originalBitmap, 450, 350, false);
                    imgB.setImageBitmap(resizedBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(ChiTietTaiKhoan.this)
                .setOnImageSelectedListener(listener)
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }

    private boolean checkThongTin(){
        String tenUser = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String pass = edtPass.getText().toString();

        if(TextUtils.isEmpty(tenUser)){
            Toast.makeText(this, "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(email) && email.matches("^[a-zA-Z]{1}[a-zA-Z0-9\\\\.\\\\,\\\\_]{0,}@(Yahoo|gmail)$")){
            Toast.makeText(this, "Email nhập không chính xác.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
        @Override
    public void onClick(View view) {

    }
}
