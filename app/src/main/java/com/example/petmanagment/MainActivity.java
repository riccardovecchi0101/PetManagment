package com.example.petmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        MaterialButton sigBtn = (MaterialButton) findViewById(R.id.signin);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.registerbtn);

        /*loginbtn.setOnClickListener(v -> {
            if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                Toast.makeText(MainActivity.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
            }
        });*/

        sigBtn.setOnClickListener(view -> {
            Intent sin = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(sin);

        });

    }
}