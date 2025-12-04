package com.example.passlock.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface PassLockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPassLock(PassLock passLock);

    // You can add other queries here later, like getting all PassLocks for a user
}
