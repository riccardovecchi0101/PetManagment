package com.example.petmanagment;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;

public class ForgotPWActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwactivity);
        MaterialButton submit=(MaterialButton) findViewById(R.id.submitBtn);
        submit.setOnClickListener(view -> {
            Toast.makeText(ForgotPWActivity.this, "You will receive a new reset password", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}