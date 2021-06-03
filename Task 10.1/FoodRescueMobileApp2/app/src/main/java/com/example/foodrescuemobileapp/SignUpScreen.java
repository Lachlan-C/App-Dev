package com.example.foodrescuemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodrescuemobileapp.data.DatabaseHelper;
import com.example.foodrescuemobileapp.model.User;

public class SignUpScreen extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        EditText usernameText = findViewById(R.id.editTextTextPersonName);
        EditText passwordText = findViewById(R.id.editTextTextPassword);
        EditText confirmpasswordText = findViewById(R.id.editTextTextPassword2);
        EditText emailText = findViewById(R.id.editTextTextEmailAddress);
        EditText phoneText = findViewById(R.id.editTextPhone);
        EditText addressText = findViewById(R.id.editTextTextPostalAddress);
        Button saveButton = findViewById(R.id.SaveButton);
        db = new DatabaseHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmPassword = confirmpasswordText.getText().toString();
                String email = emailText.getText().toString();
                Integer phone = Integer.parseInt(phoneText.getText().toString());
                String address = addressText.getText().toString();

                if (db.IfUserExists(username))
                {
                    Toast.makeText(SignUpScreen.this, "Name already in use", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.equals(confirmPassword)) {
                        long result = db.insertUser(new User(username, password, email, phone, address));
                        if (result > 0) {
                            Toast.makeText(SignUpScreen.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            usernameText.setText("");
                            passwordText.setText("");
                            confirmpasswordText.setText("");
                            emailText.setText("");
                            phoneText.setText("");
                            addressText.setText("");
                        } else {
                            Toast.makeText(SignUpScreen.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpScreen.this, "Password Doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}