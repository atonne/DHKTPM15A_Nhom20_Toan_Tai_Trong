package com.example.dhktpm15a_nhom20_toan_tai_trong.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.UserDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.database.NoteDatabase;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.User;
import com.example.dhktpm15a_nhom20_toan_tai_trong.realtimeDAO.userDAO.RealtimeUser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Dangky extends AppCompatActivity {
    TextView tvlogin;
    ImageView imggoogle;
    Button btndangky;
    EditText txtemail,txtpass,txtrepass,txthoten;

    private  UserDAO userDAO;
    private RealtimeUser realtimeUser;

    private FirebaseAuth mauth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        mauth= FirebaseAuth.getInstance();

         userDAO= NoteDatabase.getInstance(getApplicationContext()).getUserDAO();
         realtimeUser = new RealtimeUser(getApplicationContext());

        addAllUserFromRealtime();

        anhxa();
        createrequest();

        tvlogin.setPaintFlags(tvlogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Dangky.this, Dangnhap.class);
                startActivity(intent);
            }
        });
        imggoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    public void anhxa(){
        tvlogin=findViewById(R.id.tvdangnhap);
        imggoogle=findViewById(R.id.imggoole_framedangky);
        btndangky=findViewById(R.id.btndangky);
        txtemail=findViewById(R.id.txtemail);
        txthoten=findViewById(R.id.txthoten);
        txtpass=findViewById(R.id.txtpassword);
        txtrepass=findViewById(R.id.txtrepassword);


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
            Intent intent=new Intent(this,TrangChu.class);
            startActivity(intent);
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {

        }
    }
    private void register(){
        String email,pass;
        email=txtemail.getText().toString();

        if(TextUtils.isEmpty(txthoten.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(txtpass.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(txtrepass.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtpass.getText().toString().equals(txtrepass.getText().toString())){
            pass=txtpass.getText().toString();
        }
        else{
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;}
        mauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Dangky.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();


                    User user = new User(txthoten.getText().toString().trim(),email,-1);



                    userDAO.addUser(user);
                    realtimeUser.addUser(userDAO.getUserFromEmail(user.getEmail()));

                    Intent intent=new Intent(Dangky.this, Dangnhap.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(Dangky.this, "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addAllUserFromRealtime(){

        realtimeUser.getAllUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()){
                    if(s!= null){
                        User u = s.getValue(User.class);

                            if(!timUser(u)){
                                userDAO.addUser(u);
                            }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public boolean timUser(User user){
        List<User> ls = userDAO.getAllUser();
        for(User u : ls){
            if(u.getIdUser() == user.getIdUser()){
                return true;
            }
        }
        return false;
    }

}