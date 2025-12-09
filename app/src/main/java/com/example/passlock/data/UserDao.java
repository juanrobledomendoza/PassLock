package com.example.passlock.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User getUserByUsernameAndPassword(String username, String password);
}

