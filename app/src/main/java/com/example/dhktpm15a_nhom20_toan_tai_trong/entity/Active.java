package com.example.dhktpm15a_nhom20_toan_tai_trong.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

@Entity(tableName = "Active")
public class Active {
    @PrimaryKey
    @NonNull
    private String email;
    private String pass;
    private boolean trangThai;

    public Active(String email, String pass, boolean trangThai) {
        this.email = email;
        this.pass = pass;
        this.trangThai = trangThai;
    }

    public Active(@NonNull String email) {
        this.email = email;
    }

    public Active() {

    }

    public Active(GoogleSignInAccount account, boolean b) {
        this.email=account.getEmail();
        this.trangThai=b;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
