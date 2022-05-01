package com.example.dhktpm15a_nhom20_toan_tai_trong.entity;

import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "Note")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int idNote;
    private String name;
    private String content;
    private String trangThai;
    private int idUser;


    public Note(int idNote, String name, String content, String trangThai, int idUser) {
        this.idNote = idNote;
        this.name = name;
        this.content = content;
        this.trangThai = trangThai;
        this.idUser = idUser;
    }



    public Note(String name, String content, String trangThai, int idUser) {
        this.name = name;
        this.content = content;
        this.trangThai = trangThai;
        this.idUser = idUser;
    }

    public Note() {
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
