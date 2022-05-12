package com.example.petmanagment.ui.Customers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.petmanagment.R;
import com.example.petmanagment.databinding.FragmentCustomersBinding;

public class CustomersFragment extends Fragment {

    private FragmentCustomersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomersViewModel customersViewModel =
                new ViewModelProvider(this).get(CustomersViewModel.class);

        binding = FragmentCustomersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView text_customers = (TextView) root.findViewById(R.id.text_customers);
        customersViewModel.getText().observe(getViewLifecycleOwner(), text_customers::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}