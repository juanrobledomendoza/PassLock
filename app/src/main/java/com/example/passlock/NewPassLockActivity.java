package com.example.passlock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.passlock.data.AppDatabase;
import com.example.passlock.data.PassLock;
import com.example.passlock.data.PassLockDao;

public class NewPassLockActivity extends AppCompatActivity {
     private EditText serviceNameEditText;
     private EditText usernameEditText;
     private EditText passwordEditText;
    private PassLockDao passLockDao;

     private int currentUserId;
     
     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_new_passlock);

          // --- IMPORTANT SAFETY CHECK ---
          // If we can't find a valid user ID, show an error and close this screen.
          if (currentUserId == -1) {
               Toast.makeText(this, "Error: Could not identify user.", Toast.LENGTH_LONG).show();
               finish();
               return; // Stop further execution
          }

          // Get the DAO from our database
          passLockDao = AppDatabase.getInstance(getApplicationContext()).passLockDao();

          //Get the current user's ID from the sharedPreferences
          SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);

          serviceNameEditText = findViewById(R.id.editTextServiceName);
          usernameEditText = findViewById(R.id.editTextUsername);
          passwordEditText = findViewById(R.id.editTextPassword);
         Button saveButton = findViewById(R.id.saveButton);
          currentUserId = prefs.getInt("userId", -1);

          saveButton.setOnClickListener(v -> {
               String serviceName = serviceNameEditText.getText().toString().trim();
               String username = usernameEditText.getText().toString().trim();
               String password = passwordEditText.getText().toString().trim();

               if (serviceName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(NewPassLockActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
               }
               
               PassLock passLock = new PassLock(currentUserId, serviceName, username, password);
               long result = passLockDao.insertPassLock(passLock);
               if (result != -1) {
                    Toast.makeText(NewPassLockActivity.this, "PassLock saved for " + serviceName, Toast.LENGTH_SHORT).show();
                    finish(); // Go back to the previous screen after saving
               } else {
                    Toast.makeText(NewPassLockActivity.this, "Error saving PassLock", Toast.LENGTH_SHORT).show();
               }
          });
     }
     
     
}