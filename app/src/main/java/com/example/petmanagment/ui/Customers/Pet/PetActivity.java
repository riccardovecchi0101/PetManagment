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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.petmanagment.R;
import com.example.petmanagment.ui.Customers.Customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import io.grpc.Context;

public class PetActivity extends AppCompatActivity {
    //Customer customer;
    FirebaseUser user;
    FirebaseFirestore db;
    String id;
    FirebaseStorage storage;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        TextView customerName = (TextView) findViewById(R.id.customer_name);
        ImageView icon = (ImageView) findViewById(R.id.imageView3);


        Bundle extras = getIntent().getExtras();
        String info = extras.getString("NameLastName");
        customerName.setText(info);

        user  = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        db = FirebaseFirestore.getInstance();
        assert user != null;
        db.collection(user.getEmail().toString()).document(info).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                id = documentSnapshot.getString("uuid");
                assert id != null;
                storageRef.child(id).getDownloadUrl().addOnSuccessListener(uri -> {
                    ImageView icon1 = (ImageView)  findViewById(R.id.imageView3);
                    ImageView smallCamera = (ImageView) findViewById(R.id.imcamera);
                    smallCamera.setVisibility(View.INVISIBLE);
                    Glide
                            .with(getApplicationContext())
                            .load(uri)
                            .into(icon1);
                }).addOnFailureListener(e -> {
                    System.out.println("no image");
                });
            }
        }
        );

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
            StorageReference imageRef = storageRef.child(id);
            UploadTask imageLoader = (UploadTask) imageRef.putFile(selectedImage).addOnSuccessListener(taskSnapshot -> Toast.makeText(PetActivity.this, "Upload ok", Toast.LENGTH_LONG)).addOnFailureListener(e -> Toast.makeText(PetActivity.this, "No upload", Toast.LENGTH_LONG));
        }
    }
}