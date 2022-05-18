package com.example.petmanagment.ui.Customers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmanagment.R;
import com.example.petmanagment.databinding.FragmentCustomersBinding;

public class CustomersFragment extends Fragment {

    String[] customers = {"cliente1", "cliente2", "cliente3", "cliente4", "cliente5", "cliente6", "cliente7", "cliente8", "cliente9", "cliente10"};


    private FragmentCustomersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomersViewModel customersViewModel =
                new ViewModelProvider(this).get(CustomersViewModel.class);

        binding = FragmentCustomersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new ListAdapter(customers));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}