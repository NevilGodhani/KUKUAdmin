package com.example.kukuadmin;

import android.content.Context;
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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private final Context context;
    private final List<Home> home;

    public HomeAdapter(Context context, List<Home> home) {
        this.context = context;
        this.home = home;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_home, parent, false);
        return new HomeAdapter.HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        Home home1 = home.get(position);
        holder.textViewTitle.setText(home1.getTitle());
        String title=home1.getTitle();
        Picasso.get().load(home1.getImageUrl()).fit().centerCrop().into(holder.imageView);
        holder.remove.setOnClickListener(v -> {
            DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference("home").child(title);
            itemRef.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Item removed successfully, update UI if needed
                    Toast.makeText(v.getContext(), "Item removed", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle failure
                    Toast.makeText(v.getContext(), "Failed to remove item", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    @Override
    public int getItemCount() {
        return home.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewTitle;
        public ImageView imageView;
        public Button remove;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageView = itemView.findViewById(R.id.imageView);
            remove=itemView.findViewById(R.id.remove);
        }
    }
}
