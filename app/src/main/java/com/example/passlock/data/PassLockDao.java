package com.example.passlock.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PassLockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPassLock(PassLock passLock);

    @Query("SELECT * FROM passlocks WHERE user_id = :userId ORDER BY service_name ASC")
    List<PassLock> getPassLocksForUser(int userId);

}
