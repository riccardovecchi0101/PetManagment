package com.example.petmanagment.ui.Customers.Pet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.petmanagment.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FinalPetActivity extends AppCompatActivity {

    StorageReference storageRef;
    String id;
    String info;
    String infocustomer;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;

    ImageView imPet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_pet);
        imPet=findViewById(R.id.impet);
        ImageView imCam=findViewById(R.id.imcam);
        TextView petName=findViewById(R.id.pet_name);
        TextView petInfoLink=findViewById(R.id.pet_info);
        EditText petInfo=findViewById(R.id.petdata);
        Button generatePdf=findViewById(R.id.pet_PDF_btn);

        Bundle extrasCustomer = getIntent().getExtras();
        infocustomer = extrasCustomer.getString("CustomerName");
        System.out.println(infocustomer);


        petInfo.setCursorVisible(false);
        petInfo.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        info = extras.getString("Name");
        petName.setText(info);

        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        db = FirebaseFirestore.getInstance();

        db.collection(user.getEmail()).document(infocustomer).collection(infocustomer).document(info).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name, race, typology;
                String content;

                id = documentSnapshot.getString("uuid");
                name = documentSnapshot.getString("name");
                race = documentSnapshot.getString("race");
                typology = documentSnapshot.getString("typology");

                content = String.format("Name: %s\nAnimal: %s\nRace: %s\n", name, typology, race);
                petInfo.setText(content);

                assert id != null;

                storageRef.child(id).getDownloadUrl().addOnSuccessListener(uri -> {
                    imCam.setVisibility(View.INVISIBLE);
                    Glide
                            .with(getApplicationContext())
                            .load(uri)
                            .into(imPet);
                }).addOnFailureListener(e -> System.out.println("no image"));
            }
        });

        imPet.setOnClickListener(view -> {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, 3);
            imCam.setVisibility(View.INVISIBLE);
        });

        petInfoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (petInfo.getVisibility() == View.VISIBLE)
                    petInfo.setVisibility(View.INVISIBLE);
                else
                    petInfo.setVisibility(View.VISIBLE);
            }
        });
        

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imPet.setImageURI(selectedImage);
            StorageReference imageRef = storageRef.child(id);
            UploadTask imageLoader = (UploadTask) imageRef.putFile(selectedImage).addOnSuccessListener(taskSnapshot -> Toast.makeText(FinalPetActivity.this, "Upload ok", Toast.LENGTH_LONG))
                    .addOnFailureListener(e -> Toast.makeText(FinalPetActivity.this, "No upload", Toast.LENGTH_LONG));
        }
    }
}