package com.example.passlock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passlock.data.AppDatabase;
import com.example.passlock.data.User;
import com.example.passlock.data.UserDao;

import java.util.ArrayList;
import java.util.List;

public class EditSuggestedPasslocksActivity extends AppCompatActivity {

    private UserDao userDao;
    private UserSuggestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_suggested_passlocks);

        userDao = AppDatabase.getInstance(this).userDao();

        RecyclerView recyclerView = findViewById(R.id.recycler_suggestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserSuggestionAdapter((user, enabled) -> {
            user.setSuggestionsEnabled(enabled);
            userDao.updateUser(user);
        });
        recyclerView.setAdapter(adapter);

        reloadUsers();

        Button toggleAllButton = findViewById(R.id.btn_toggle_all_suggestions);
        toggleAllButton.setOnClickListener(v -> toggleAllSuggestions());

        Button backButton = findViewById(R.id.btn_back_from_edit_suggestions);
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

    private void toggleAllSuggestions() {
        List<User> users = adapter.getUsers();
        if (users.isEmpty()) return;

        boolean anyDisabled = false;
        for (User u : users) {
            if (!u.isSuggestionsEnabled()) {
                anyDisabled = true;
                break;
            }
        }

        boolean newValue = anyDisabled; // if any disabled, enable all; otherwise disable all
        for (User u : users) {
            u.setSuggestionsEnabled(newValue);
            userDao.updateUser(u);
        }

        reloadUsers();
    }
}
