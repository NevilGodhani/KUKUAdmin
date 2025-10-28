package com.example.kukuadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends BaseAdapter {

    private List<Food> originalProductList;
    private List<Food> filteredProductList;
    private final LayoutInflater inflater;
    private ProductFilter productFilter;
    private DatabaseReference databaseReference;
    Integer q;

    public FoodAdapter(Context context, List<Food> productList) {
        this.originalProductList = productList;
        this.filteredProductList = productList;
        this.inflater = LayoutInflater.from(context);
        getFilter();
    }
    public Filter getFilter() {
        if (productFilter == null) {
            productFilter = new ProductFilter();
        }
        return productFilter;
    }
    private class ProductFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Food> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(originalProductList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Food product : originalProductList) {
                    if (product.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(product);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredProductList = (List<Food>) results.values;
            notifyDataSetChanged();
        }


    }
    public void updateProductList(List<Food> newProductList) {
        this.originalProductList = newProductList;
        this.filteredProductList = newProductList; // Update both lists
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return filteredProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_food, parent, false);
            holder = new ViewHolder();
            holder.productImage = convertView.findViewById(R.id.product_image);
            holder.productTitle = convertView.findViewById(R.id.product_title);
            holder.productPrice = convertView.findViewById(R.id.product_price);
            holder.buttonAddToCart = convertView.findViewById(R.id.button_add_to_cart);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Food food=filteredProductList.get(position);

        holder.productTitle.setText(food.getTitle());
        String title=food.getTitle();
        holder.productPrice.setText(String.format("₹%s", food.getPrice()));
        Picasso.get().load(food.getImageUrl()).into(holder.productImage);

        // Handle add to cart button click
        View finalConvertView = convertView;
        holder.buttonAddToCart.setOnClickListener(v -> {
            DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference("food").child(title);
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
        return convertView;
    }
    private static class ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        Button buttonAddToCart;
    }
}
