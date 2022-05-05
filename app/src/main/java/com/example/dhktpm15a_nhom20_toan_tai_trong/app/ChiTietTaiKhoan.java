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
import com.example.dhktpm15a_nhom20_toan_tai_trong.realtimeDAO.userDAO.RealtimeUser;

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
    private Button btnLuu, btnDongBo;
    private RealtimeUser realtimeUser;
    private Uri mUri;
    private Uri dUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiettaikhoan);
        TextView tvQuayLai = findViewById(R.id.tv_quaylai_chitietnode);
        realtimeUser = new RealtimeUser(this);
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
        btnEdtPass = findViewById(R.id.imgB_editpass);
        btnEdtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPass.setEnabled(true);
            }
        });

        edtName.setEnabled(false);
        edtEmail.setEnabled(false);
        edtPass.setEnabled(false);
        noteDAO = NoteDatabase.getInstance(this).getNoteDAO();
        userDAO = NoteDatabase.getInstance(this).getUserDAO();
        userActiveDAO = NoteDatabase.getInstance(this).getUserActive();
        Active userActive = userActiveDAO.getUserActive(true);
        emailUser = userActive.getEmail();


        userLogin = userDAO.getUserFromEmail(emailUser);
        tvName.setText(userLogin.getTenUser());
        edtName.setText(userLogin.getTenUser());
        edtEmail.setText(userLogin.getEmail());
        edtPass.setText(userActive.getPass());
        //dUri = Uri.parse(userLogin.getImg());
        //loadImage(dUri);
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
                   String tenUser = edtName.getText().toString();
                   String email = edtEmail.getText().toString();
                   String pass = edtPass.getText().toString();
                   //String img = mUri.toString();
                    User u = new User(tenUser, email, 0);
                  // realtimeUser.addUser(u);
                    userDAO.updateUser(u);
                }

        });

        btnDongBo = findViewById(R.id.btnDongBo);
        btnDongBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkThongTin()){
                    String tenUser = edtName.getText().toString();
                    String email = edtEmail.getText().toString();
                    String pass = edtPass.getText().toString();
                    //String img = mUri.toString();
                    User u = new User(tenUser, email, 0);

                    realtimeUser.addUser(u);
                }
            }
        });

    }
    private void openImagePicker(){
        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(Uri uri) {
                try {
                    mUri = uri;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
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
    public void loadImage(Uri uri){
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
            if(TextUtils.isEmpty(pass)){
                Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            }

        return true;
    }
        @Override
    public void onClick(View view) {

    }
}
