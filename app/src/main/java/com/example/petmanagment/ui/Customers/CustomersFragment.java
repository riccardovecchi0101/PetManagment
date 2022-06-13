package com.example.petmanagment.ui.Customers;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petmanagment.R;
import com.example.petmanagment.databinding.FragmentCustomersBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CustomersFragment extends Fragment {


    private FragmentCustomersBinding binding;


    ArrayList<String> customers = new ArrayList<>();
    ArrayList<String> flag = new ArrayList<>();
    RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomersViewModel customersViewModel =
                new ViewModelProvider(this).get(CustomersViewModel.class);


        customers.add("ciao");
        customers.add("luca");
        customers.add("riccardogay");
        customers.add("francesco");
        customers.add("marco");


        binding = FragmentCustomersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText searchCustomer = (EditText) root.findViewById(R.id.search_customer_editText);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new ListAdapter(customers));
        final Handler handler = new Handler();
        final Runnable runnable = () -> {
            if (!searchCustomer.getText().toString().isEmpty()) {
                for (String l : customers) {
                    if (l.startsWith(searchCustomer.getText().toString())) {
                        flag.add(l);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                recyclerView.setAdapter(new ListAdapter(flag));
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                recyclerView.setAdapter(new ListAdapter(customers));
            }
        };


        searchCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.post(runnable);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                handler.removeCallbacksAndMessages(runnable);
                flag.clear();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;
    }

    String deletedCustomer = null;

    //ItemTouchHelper serve per implementare l'eliminazione dalla lista scorrendo verso sinistra
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


        //onMove probabilmente non serve
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                deletedCustomer = customers.get(position);

                customers.remove(position);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                //la snackbar serve per tornare indietro in caso di errore nella cancellazione
                Snackbar.make(recyclerView, "Customer " + deletedCustomer + " deleted", Snackbar.LENGTH_LONG).setAction("Undo", view -> {
                            customers.add(position, deletedCustomer);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        }).setActionTextColor(getResources().getColor(R.color.orange))
                        .setTextColor(getResources().getColor(R.color.black))
                        .setBackgroundTint(getResources().getColor(R.color.white))
                        .show();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete_icon)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}