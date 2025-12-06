package com.example.passlock.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PassLockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPassLock(PassLock passLock);

    @Update
    void updatePassLock(PassLock passLock);

    @Query("SELECT * FROM passlocks WHERE user_id = :userId ORDER BY service_name ASC")
    List<PassLock> getPassLocksForUser(int userId);

    @Query("SELECT * FROM passlocks")
    List<PassLock> getAllPassLocks();

    @Query("SELECT * FROM passlocks WHERE passlock_id = :passLockId")
    PassLock getPassLockById(int passLockId);

}
