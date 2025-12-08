package com.example.passlock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passlock.data.AppDatabase;
import com.example.passlock.data.UserDao;

public class ViewUserPasslocksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_passlocks);

        RecyclerView recyclerView = findViewById(R.id.recycler_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserAdapter adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        UserDao userDao = AppDatabase.getInstance(this).userDao();
        adapter.setUsers(userDao.getAllUsers());

        Button backButton = findViewById(R.id.btn_back_from_view_users);
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LandingPage.class));
            finish();
        });
    }
}
