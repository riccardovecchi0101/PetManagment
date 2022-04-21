package com.example.petmanagment;

import android.os.*;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        MaterialButton register = (MaterialButton) findViewById(R.id.registerbtn);
        register.setOnClickListener(view -> {
            Credenziali crHelper = new Credenziali(RegistrationActivity.this);
            try {
                crHelper.addUser(username.getText().toString().trim(), password.getText().toString().trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}


