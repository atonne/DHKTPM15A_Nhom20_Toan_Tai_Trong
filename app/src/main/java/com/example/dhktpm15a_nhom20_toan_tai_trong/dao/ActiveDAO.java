package com.example.dhktpm15a_nhom20_toan_tai_trong.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Active;
import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.Note;

import java.util.List;

@Dao
public interface ActiveDAO {

    @Query("select * from Active where trangThai = :trangThai ")
    public Active getUserActive(boolean trangThai);



    @Insert
    public void addUserActive(Active note);

    @Delete
    public void deleteUserActive(Active note);
}
