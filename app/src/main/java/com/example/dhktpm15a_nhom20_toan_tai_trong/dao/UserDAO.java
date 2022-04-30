package com.example.dhktpm15a_nhom20_toan_tai_trong.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dhktpm15a_nhom20_toan_tai_trong.entity.User;

@Dao
public interface UserDAO {
    @Query("select * from User where email= :email")
    public User getUserFromEmail(String email);

    @Query("select * from User where idUser = :id")
    public User getUserFromId(int id);

    @Insert
    public void addUser(User user);


}
