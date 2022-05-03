package com.example.dhktpm15a_nhom20_toan_tai_trong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dhktpm15a_nhom20_toan_tai_trong.R;
import com.example.dhktpm15a_nhom20_toan_tai_trong.app.Dangnhap;
import com.example.dhktpm15a_nhom20_toan_tai_trong.dao.ActiveDAO;
import com.example.dhktpm15a_nhom20_toan_tai_trong.database.NoteDatabase;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Active;
import com.google.firebase.auth.FirebaseAuth;

public class DialogLogOut extends Dialog {

    public interface Logout {
        public void logOut();
    }


    private DialogLogOut.Logout logout;




    public Context context;


    private Button btnYes;
    private Button btnNo;

    public DialogLogOut(@NonNull Context context,DialogLogOut.Logout logout) {
        super(context);

        this.logout = logout;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_log_out);


        this.btnYes = (Button) findViewById(R.id.btnDlgLogoutCo);
        this.btnNo  = (Button) findViewById(R.id.btnDlgLogoutHuy);

        this.btnYes .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnYes();
            }
        });
        this.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnHuy();
            }
        });
    }


    private void clickBtnYes()  {
        this.dismiss();
        if(logout!= null){
            this.logout.logOut();
        }







    }


    private void clickBtnHuy()  {
        this.dismiss();
    }
}
