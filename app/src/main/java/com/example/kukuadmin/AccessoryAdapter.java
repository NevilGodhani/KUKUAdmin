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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AccessoryAdapter extends BaseAdapter {

    private List<Accessory> originalProductList;
    private List<Accessory> filteredProductList;
    private final LayoutInflater inflater;
    private ProductFilter productFilter;
    private DatabaseReference databaseReference;
    Integer q;

    public AccessoryAdapter(Context context, List<Accessory> productList) {
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
            List<Accessory> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(originalProductList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Accessory product : originalProductList) {
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
            filteredProductList = (List<Accessory>) results.values;
            notifyDataSetChanged();
        }


    }
    public void updateProductList(List<Accessory> newProductList) {
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
            convertView = inflater.inflate(R.layout.layout_accessory, parent, false);
            holder = new ViewHolder();
            holder.productImage = convertView.findViewById(R.id.Product_image);
            holder.productTitle = convertView.findViewById(R.id.Product_title);
            holder.productPrice = convertView.findViewById(R.id.Product_price);
            holder.buttonAddToCart = convertView.findViewById(R.id.delete_accessory);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Accessory accessory=filteredProductList.get(position);

        holder.productTitle.setText(accessory.getTitle());
        String title=accessory.getTitle();
        holder.productPrice.setText(String.format("₹%s", accessory.getPrice()));
        Picasso.get().load(accessory.getImageUrl()).into(holder.productImage);

        // Handle add to cart button click
        View finalConvertView = convertView;
        holder.buttonAddToCart.setOnClickListener(v -> {
            DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference("accessory").child(title);
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
