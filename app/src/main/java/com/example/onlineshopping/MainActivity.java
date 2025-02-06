package com.example.onlineshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button signin;
    TextView forgotPassword, createAccount;

    String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database
        db = openOrCreateDatabase("AndroidOnlineShopping.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(50), EMAIL VARCHAR(50), CONTACT BIGINT(10), PASSWORD VARCHAR(20))";
        db.execSQL(tableQuery);

        // Initialize SharedPreferences
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        // Check if the user is already logged in
        if (!sp.getString(ConstantSp.USERID, "").isEmpty()) {
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        // Initialize views
        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);
        signin = findViewById(R.id.main_signin);
        forgotPassword = findViewById(R.id.main_forgot_password);
        createAccount = findViewById(R.id.main_create_account);

        // Set click listeners
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotpasswordActivity.class);
                startActivity(intent);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate input
                if (email.getText().toString().trim().equals("")) {
                    email.setError("Email ID Required");
                } else if (!email.getText().toString().trim().matches(EmailPattern)) {
                    email.setError("Valid Email ID Required");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                } else if (password.getText().toString().trim().length() < 6) {
                    password.setError("Min. 6 Char Password Required");
                } else {
                    // Check credentials in the database
                    String selectQuery = "SELECT * FROM USERS WHERE (EMAIL = '" + email.getText().toString() + "' OR CONTACT = '" + email.getText().toString() + "') AND PASSWORD = '" + password.getText().toString() + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);

                    if (cursor.moveToFirst()) {
                        // Get user details
                        String userId = cursor.getString(cursor.getColumnIndexOrThrow("USERID"));
                        String userName = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
                        String userEmail = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"));
                        String userContact = cursor.getString(cursor.getColumnIndexOrThrow("CONTACT"));

                        // Save user session in SharedPreferences
                        sp.edit().putString(ConstantSp.USERID, userId).apply();
                        sp.edit().putString(ConstantSp.NAME, userName).apply();
                        sp.edit().putString(ConstantSp.EMAIL, userEmail).apply();
                        sp.edit().putString(ConstantSp.CONTACT, userContact).apply();

                        // Display success message
                        Toast.makeText(MainActivity.this, "Welcome, " + userName + "!", Toast.LENGTH_SHORT).show();

                        // Navigate to dashboard
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Login failed
                        Toast.makeText(MainActivity.this, "Invalid Email/Password", Toast.LENGTH_SHORT).show();
                    }

                    // Close the cursor
                    cursor.close();
                }
            }
        });
    }
}
