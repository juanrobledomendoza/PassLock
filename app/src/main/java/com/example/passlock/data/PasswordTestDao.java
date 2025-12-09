package com.example.passlock.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PasswordTestDao {

    @Insert
    long insertTest(PasswordTest test);

    @Query("SELECT * FROM password_tests WHERE userId = :userId ORDER BY timestamp DESC")
    List<PasswordTest> getTestsForUser(int userId);
}
