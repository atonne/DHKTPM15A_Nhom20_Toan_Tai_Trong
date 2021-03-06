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

import java.util.List;

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
    private RealtimeUser realtimeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        mauth= FirebaseAuth.getInstance();
        realtimeUser = new RealtimeUser(this);

        addAllUserFromRealtime();
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

            mauth.createUserWithEmailAndPassword(account.getEmail(),"123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){


                    }
                }
            });
            Active atv = new Active(account.getEmail().toString(),"123456",true);
            activeDAO.addUserActive(atv);
            User user = new User(account.getDisplayName().toString(),account.getEmail().toString(),-1);

            UserDAO userDAO = NoteDatabase.getInstance(getApplicationContext()).getUserDAO();
            RealtimeUser realtimeUser = new RealtimeUser(getApplicationContext());

            userDAO.addUser(user);
            realtimeUser.addUser(userDAO.getUserFromEmail(user.getEmail()));
            Toast.makeText(Dangnhap.this, "thanh cong", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Dangnhap.this, TrangChu.class);
            startActivity(intent);
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {

        }
    }
    private void login(){
        String emails,passw;
        emails=txtemail.getText().toString().trim();
        passw=txtpass.getText().toString().trim();

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
                    Toast.makeText(Dangnhap.this, "T??n ho???c m???t kh???u kh??ng ????ng", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Dangnhap.this, "L???i ????ng nh???p", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }




}