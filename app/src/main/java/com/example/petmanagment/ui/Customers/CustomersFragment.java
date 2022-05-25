package com.example.petmanagment.ui.Customers;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmanagment.R;
import com.example.petmanagment.databinding.FragmentCustomersBinding;

public class CustomersFragment extends Fragment {

    String[] customers = {"gianni", "luca", "frovio", "riccardomerda", "succo", "fruttolo", "cliente7", "cliente8", "cliente9", "cliente10"};



    private FragmentCustomersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomersViewModel customersViewModel =
                new ViewModelProvider(this).get(CustomersViewModel.class);

        binding = FragmentCustomersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText searchCustomer=(EditText)root.findViewById(R.id.search_customer_editText);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        final Handler handler = new Handler();
        final Runnable runnable= () -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(new ListAdapter(customers));
        };


        searchCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.postDelayed(runnable,500);
            }

            @Override
            public void afterTextChanged(Editable editable) {

                handler.removeCallbacksAndMessages(runnable);
                //handler.removeCallbacks(runnable);
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}