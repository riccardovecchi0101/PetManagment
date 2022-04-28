package com.example.petmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedIstanceSate) {
        super.onCreate(savedIstanceSate);
        setContentView(R.layout.activity_registration);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        TextView email = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        MaterialButton register = (MaterialButton) findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(view -> {
            String em = email.getText().toString();
            String pw = password.getText().toString();
            if (em.isEmpty()) {
                email.setError("email is required");
                email.requestFocus();
                return;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
                email.setError("Insert a valid email");
                email.requestFocus();
                return;
            }
            if (pw.isEmpty()) {
                password.setError("password is required");
                password.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(em, pw)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User user = new User(em, pw);
                            progressBar.setVisibility(View.VISIBLE);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(task1 -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "User has been registered succesfully", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.VISIBLE);
                                    Intent returnback = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(returnback);
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Failed to register the user", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }


                            });
                        } else {
                            Toast.makeText(RegistrationActivity.this, "failed to register the user", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        });
    }
}


