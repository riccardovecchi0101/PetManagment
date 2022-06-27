package com.example.petmanagment.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.petmanagment.R;
import com.example.petmanagment.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    //contano il numero totale di clienti e animali
    private int totalCustomers = 0;
    private int totalPets = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //la text_home non viene usata ma se tolta da problemi con la classe HomeViewModel, text_customers e text_pets servono per controllare i rispettivi contatori
        final TextView text_home = root.findViewById(R.id.text_home);

        final TextView text_customers = root.findViewById(R.id.tv_clients_number);
        final TextView text_pets = root.findViewById(R.id.tv_pets_number);

        //le due progressbar servono per controllare il progresso delle due barre
        CircularProgressBar customersProgressBar = root.findViewById(R.id.progress_circular);
        CircularProgressBar petsProgressBar = root.findViewById(R.id.circularProgressBar);

        customersProgressBar.setProgressWithAnimation(totalCustomers, 2000L);
        petsProgressBar.setProgressWithAnimation(totalPets, 2000L);

        //setto il massimo di elementi che servono per riempire la barra
        customersProgressBar.setProgressMax(100f);
        petsProgressBar.setProgressMax(100f);

        //conto il numero di utenti e animali da firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(user.getEmail()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        totalCustomers++;
                    }
                });


        //setto le variabili text_customers e text_pets
        text_customers.setText(String.valueOf(totalCustomers));
        text_pets.setText(String.valueOf(totalPets));


        //questa riga sotto potrebbe essere eliminata, cosi come riga 31 ma se eliminate da problemi
        homeViewModel.getText().observe(getViewLifecycleOwner(), text_home::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}