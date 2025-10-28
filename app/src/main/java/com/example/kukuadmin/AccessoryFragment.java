package com.example.kukuadmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccessoryFragment extends Fragment {
    private GridView productGridView;
    private List<Accessory> productList = new ArrayList<>();
    private AccessoryAdapter productAdapter;
    private DatabaseReference databaseReference;
    private SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_accessory, container, false);

        Button accessoryButton=view.findViewById(R.id.accessoryButton);
        Context context=getContext();

        productGridView = view.findViewById(R.id.product_grid_view);
        searchView = view.findViewById(R.id.search_view);
        productAdapter = new AccessoryAdapter(getContext(), productList);
        productGridView.setAdapter(productAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("accessory");

        // Load products from Firebase
        loadProductsFromFirebase();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });
        accessoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity2.class);
                intent.putExtra("accessory","accessory");
                startActivity(intent);
            }
        });
        return view;
    }
    private void loadProductsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Accessory product = snapshot.getValue(Accessory.class);
                    productList.add(product);
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load products.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}