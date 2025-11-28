package com.example.passlock.data;

import android.content.Context;

public class UserRepository {

    private final UserDao userDao;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.userDao = db.userDao();
    }

    public User login(String username, String password) {
        return userDao.getUserByUsernameAndPassword(username, password);
    }

    public long createUser(String username, String password, boolean isAdmin) {
        User user = new User(username, password, isAdmin);
        return userDao.insertUser(user);
    }
}

