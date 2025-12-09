package com.example.passlock;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passlock.data.AppDatabase;
import com.example.passlock.data.User;
import com.example.passlock.data.UserDao;

import java.util.ArrayList;
import java.util.List;

public class ViewUserPasslocksActivity extends AppCompatActivity {

    private UserDao userDao;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_passlocks);

        userDao = AppDatabase.getInstance(this).userDao();

        RecyclerView recyclerView = findViewById(R.id.recycler_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter(user -> confirmAndDeleteUser(user));
        recyclerView.setAdapter(adapter);

        reloadUsers();

        EditText searchEditText = findViewById(R.id.et_search_users);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s != null ? s.toString() : "");
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        Button backButton = findViewById(R.id.btn_back_from_view_users);
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LandingPage.class));
            finish();
        });
    }

    private void reloadUsers() {
        List<User> allUsers = userDao.getAllUsers();
        List<User> nonAdminUsers = new ArrayList<>();
        for (User u : allUsers) {
            if (!u.isAdmin()) {
                nonAdminUsers.add(u);
            }
        }
        adapter.setUsers(nonAdminUsers);
    }

    private void confirmAndDeleteUser(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Delete user")
                .setMessage("Are you sure you want to delete " + user.getUsername() + " and all their passlocks?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    userDao.deleteUser(user);
                    reloadUsers();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
