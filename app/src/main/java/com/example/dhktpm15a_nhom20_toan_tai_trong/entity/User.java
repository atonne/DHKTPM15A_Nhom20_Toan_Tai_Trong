package com.example.dhktpm15a_nhom20_toan_tai_trong.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int idUser;
    private String tenUser;


    private String email;
    @Ignore
    private int img;

    public User(int idUser, String tenUser, String email, int img) {
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.email = email;
        this.img = img;
    }

    public User(String tenUser, String email, int img) {
        this.tenUser = tenUser;
        this.email = email;
        this.img = img;
    }

    public User() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
