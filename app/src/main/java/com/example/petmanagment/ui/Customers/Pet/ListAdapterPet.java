package com.example.petmanagment.ui.Customers.Pet;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmanagment.R;
import com.example.petmanagment.ui.Customers.Customer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ListAdapterPet extends RecyclerView.Adapter<ListAdapterPet.MyViewHolder> {
    //creo un adapter per la recycle view
    FirebaseUser user;
    Customer customer;
    Pet pet;
    FirebaseFirestore db;
    ArrayList<String> list;
    String id;

    public ListAdapterPet(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pets_list_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvPet.setText(list.get(position));
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection(user.getEmail())
                .document(holder.tvPet.getText().toString())
                .collection(customer.getName().toString() + customer.getLastName().toString())
                .document(pet.getName().toString())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                holder.tvRace.setText(documentSnapshot.getString("phone"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPet;
        TextView tvRace;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPet = itemView.findViewById(R.id.tvpetname);
            tvRace = itemView.findViewById(R.id.tvpetrace);
            itemView.setOnClickListener(view -> {
                //  System.out.println(tvNameSurname.getText());
                Intent clientWindow = new Intent(view.getContext(), PetActivity.class);
                clientWindow.putExtra("ID", tvRace.getText());
                clientWindow.putExtra("NameLastName", tvPet.getText());
                view.getContext().startActivity(clientWindow);
            });

        }
    }

}
