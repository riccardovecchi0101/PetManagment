package com.example.petmanagment.ui.Customers.Pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petmanagment.R;
import com.example.petmanagment.ui.Customers.Customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PetActivity extends AppCompatActivity {
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        TextView customerName = (TextView) findViewById(R.id.customer_name);
        ImageView icon = (ImageView) findViewById(R.id.imageView3);


        Bundle extras = getIntent().getExtras();
        String info = extras.getString("NameLastName");
        customerName.setText(info);

        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        db.collection(user.getEmail()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if(document.getId() == info){
                            System.out.println(document.getData());
                        }
                    }
                });

        icon.setOnClickListener(view -> {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, 3);
            ImageView camera_icon=findViewById(R.id.imcamera);
            camera_icon.setVisibility(View.INVISIBLE);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            ImageView icon = (ImageView)  findViewById(R.id.imageView3);
            icon.setImageURI(selectedImage);
        }
    }
}