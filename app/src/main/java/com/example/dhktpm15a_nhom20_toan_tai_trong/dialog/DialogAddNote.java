package com.example.dhktpm15a_nhom20_toan_tai_trong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.dhktpm15a_nhom20_toan_tai_trong.R;

public class DialogAddNote extends Dialog {

    public interface FullNameListener {
        public void getNameNote(String nameNote);
    }

    public Context context;

    private EditText txtName;
    private Button btnTao;
    private Button btnHuy;

    private DialogAddNote.FullNameListener listener;

    public DialogAddNote(Context context, DialogAddNote.FullNameListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_note);

        this.txtName = (EditText) findViewById(R.id.txtDlgAddNote);
        this.btnTao = (Button) findViewById(R.id.btnDlgLogoutCo);
        this.btnHuy  = (Button) findViewById(R.id.btnDlgLogoutHuy);

        this.btnTao .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnTao();
            }
        });
        this.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnHuy();
            }
        });
    }


    private void clickBtnTao()  {
        String fullName = this.txtName.getText().toString();

        if(fullName== null || fullName.isEmpty())  {
            Toast.makeText(this.context, "Vui lòng nhập tên cho ghi chú mới", Toast.LENGTH_LONG).show();
            return;
        }
        this.dismiss();

        if(this.listener!= null)  {
            this.listener.getNameNote(fullName);
        }
    }


    private void clickBtnHuy()  {
        this.dismiss();
    }
}
