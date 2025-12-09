package com.example.passlock.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);
}

