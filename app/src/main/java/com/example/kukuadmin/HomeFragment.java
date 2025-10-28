package com.example.kukuadmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeAdapter adapter;
    private List<Home> home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        Button homeButton=view.findViewById(R.id.home);

        Context context=getContext();


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        int spacingInDp = 8;
        int spacingInPx = (int) (spacingInDp * getContext().getResources().getDisplayMetrics().density);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPx));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        home = new ArrayList<>();
        adapter = new HomeAdapter(getContext(), home);
        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("home");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                home.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Home home1 = postSnapshot.getValue(Home.class);
                    home.add(home1);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Not", Toast.LENGTH_SHORT).show();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity2.class);
                intent.putExtra("home","home");
                startActivity(intent);
            }
        });
        return view;
    }
}