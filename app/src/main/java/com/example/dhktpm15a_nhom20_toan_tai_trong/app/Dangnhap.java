package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.ActiveDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.UserDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.database.NoteDatabase;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Active;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Dangnhap extends AppCompatActivity {
    TextView tvdangky,tvquenmk;
    ImageView imggoole;
    EditText txtemail,txtpass;
    Button btnlogin;
    private UserDAO userDAO;
    private ActiveDAO activeDAO;
    private FirebaseAuth mauth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        mauth= FirebaseAuth.getInstance();
        anhxa();

        checkLogin();


    }
    public void anhxa(){
        tvdangky=findViewById(R.id.tvdangkyngay);
        imggoole=findViewById(R.id.imggoole);
        txtemail=findViewById(R.id.txtmail);
        txtpass=findViewById(R.id.txtpass);
        btnlogin=findViewById(R.id.btndangnhap);
        tvquenmk=findViewById(R.id.tvquenmk);

        activeDAO = NoteDatabase.getInstance(this).getUserActive();
        userDAO = NoteDatabase.getInstance(this).getUserDAO();

    }
    private void createrequest() {
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, account.getEmail().toString(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, ChiTietNote.class);
            startActivity(intent);
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {

        }
    }
    private void login(){
        String emails,passw;
        emails=txtemail.getText().toString().trim();
        passw=txtpass.getText().toString().trim();
        System.out.println(emails + passw);
        mauth.signInWithEmailAndPassword(emails,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Active atv = new Active(emails,passw,true);
                    activeDAO.addUserActive(atv);

                    Intent intent=new Intent(Dangnhap.this, TrangChu.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(Dangnhap.this, "Tên hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void checkLogin(){
        Active userActive = activeDAO.getUserActive(true);
        if(userActive == null){
            createrequest();

            //dangki
            tvdangky.setPaintFlags(tvdangky.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvdangky.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Dangnhap.this, Dangky.class);
                    startActivity(intent);
                }
            });
            //tvquenmk
            tvquenmk.setPaintFlags(tvdangky.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            imggoole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });
        }

        else {
            String emails = userActive.getEmail();
            String passw = userActive.getPass();
            mauth.signInWithEmailAndPassword(emails,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent=new Intent(Dangnhap.this, TrangChu.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(Dangnhap.this, "Lỗi Đăng nhập", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    public void getUserFromRoom(){

    }

}