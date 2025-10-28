package com.example.kukuadmin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final Context context;
    private final List<Order> order;


    public OrderAdapter(Context context, List<Order> order) {
        this.context = context;
        this.order = order;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_order, parent, false);
        return new OrderAdapter.OrderViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        Order orderHistory = order.get(position);
        holder.textViewTitle.setText(orderHistory.getTitle());
        String title=orderHistory.getTitle();
        holder.textViewPrice.setText("₹" + orderHistory.getPrice());
        Picasso.get().load(orderHistory.getImageUrl()).into(holder.imageView);
        holder.textViewQuantity.setText("Quantity : " + orderHistory.getQty().toString());


    }

    private void changeStatus() {

    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTitle,textViewPrice,textViewQuantity;
        public ImageView imageView;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.order_product_title);
            imageView = itemView.findViewById(R.id.order_product_image);
            textViewPrice=itemView.findViewById(R.id.order_product_price);
            textViewQuantity=itemView.findViewById(R.id.order_product_quantity);
        }
    }
}

