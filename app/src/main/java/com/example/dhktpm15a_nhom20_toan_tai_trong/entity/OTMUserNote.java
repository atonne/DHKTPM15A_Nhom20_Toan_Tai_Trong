package com.example.dhktpm15a_nhom20_toan_tai_trong.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class OTMUserNote {
    @Embedded
    private User user;

    @Relation(
            parentColumn = "idUser",
            entityColumn = "idUser"
    )
    private List<Note> lsNote;

    public OTMUserNote(User user, List<Note> lsNote) {
        this.user = user;
        this.lsNote = lsNote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Note> getLsNote() {
        return lsNote;
    }

    public void setLsNote(List<Note> lsNote) {
        this.lsNote = lsNote;
    }
}
