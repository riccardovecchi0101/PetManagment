package com.example.petmanagment.ui.Customers.Pet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petmanagment.R;
import com.example.petmanagment.ui.Customers.Customer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class PetActivity extends AppCompatActivity {
    //Customer customer;
    FirebaseUser user;
    FirebaseFirestore db;
    String id;
    FirebaseStorage storage;
    StorageReference storageRef;
    RecyclerView recyclerView;
    ArrayList<String> pets = new ArrayList<>();
    ListAdapterPet listAdapterPet;
    //bisogna impostare customer al cliente corrente
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

        user = FirebaseAuth.getInstance().getCurrentUser();
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
                    ImageView icon1 = (ImageView) findViewById(R.id.imageView3);
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
        });

        icon.setOnClickListener(view -> {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, 3);
            ImageView camera_icon = findViewById(R.id.imcamera);
            camera_icon.setVisibility(View.INVISIBLE);
        });

        ListAdapterPet listAdapter = new ListAdapterPet(pets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ImageView icon = (ImageView) findViewById(R.id.imageView3);
            icon.setImageURI(selectedImage);
            StorageReference imageRef = storageRef.child(id);
            UploadTask imageLoader = (UploadTask) imageRef.putFile(selectedImage).addOnSuccessListener(taskSnapshot -> Toast.makeText(PetActivity.this, "Upload ok", Toast.LENGTH_LONG)).addOnFailureListener(e -> Toast.makeText(PetActivity.this, "No upload", Toast.LENGTH_LONG));
        }
    }

    public void getPets(ArrayList<String> c) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection(user.getEmail().toString()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (!c.contains(document.getId()))
                            c.add(document.getId());
                        listAdapterPet.notifyDataSetChanged();
                    }
                });
    }

    public void addNewPet(Pet pet) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        ArrayList<Pet> current_pet = new ArrayList<>();
        current_pet.add(pet);
        db.collection(user.getEmail().toString())
                .document(customer.getName().toString() + '\t' + customer.getLastName().toString())
                .collection(customer.getName().toString() + customer.getLastName().toString())
                .document(pet.getName().toString() + '\t' + pet.getRace().toString() + '\t' + pet.getTypology().toString())
                .set(pet, SetOptions.merge()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Pet added successfully", Toast.LENGTH_LONG).show();
                    }
                });
    }
}