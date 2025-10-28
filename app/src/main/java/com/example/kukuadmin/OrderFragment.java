package com.example.kukuadmin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private OrderAdapter adapter;
    private List<Order> orderHistories;
    private String userId;

    public static OrderFragment newInstance(String userId) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString("USER_ID", userId);  // Pass userId to the fragment
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_order, container, false);
        Context context=getContext();
        RecyclerView recyclerView = view.findViewById(R.id.order_recycler_view);
        int spacingInDp = 8;
        int spacingInPx = (int) (spacingInDp * getContext().getResources().getDisplayMetrics().density);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPx));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderHistories = new ArrayList<>();
        adapter = new OrderAdapter(getContext(), orderHistories);
        recyclerView.setAdapter(adapter);
        if (getArguments() != null) {
            userId = getArguments().getString("USER_ID");  // Retrieve userId from arguments
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orderHistory").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderHistories.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    orderHistories.add(order);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });

        return view;
    }
}