package com.example.kukuadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getFirstName() + " " + user.getLastName());
        String userId=user.getUserUid().toString();

        // Handle Add Balance button click
        holder.addBalanceButton.setOnClickListener(v -> showAddBalanceDialog(user));
        holder.order.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity3.class); // Create an Intent for MainActivity2
            intent.putExtra("USER_ID", userId); // Pass userId with the key "USER_ID"
            v.getContext().startActivity(intent); // Start the new activity
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Show a dialog to input balance
    private void showAddBalanceDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Balance");

        // Set up the input field
        final EditText input = new EditText(context);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String balanceInput = input.getText().toString();
            if (!balanceInput.isEmpty()) {
                double balance = Double.parseDouble(balanceInput);
                addBalanceToWallet(user.getUserUid(), balance);
            } else {
                Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Add balance to the wallet table in Firebase
    private void addBalanceToWallet(String userUid, double balance) {
        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference("wallet").child(userUid);
        walletRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    // Get the existing wallet and balance
                    Wallet wallet = snapshot.getValue(Wallet.class);
                    if (wallet != null) {
                        double existingBalance = wallet.getBalance(); // Assuming getBalance() method exists in the Wallet class
                        double updatedBalance = existingBalance + balance;

                        // Update the wallet with the new balance
                        wallet.setBalance(updatedBalance);
                        walletRef.setValue(wallet)
                                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Balance updated successfully", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(context, "Failed to update balance", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    // If the wallet doesn't exist, create a new one
                    Wallet wallet = new Wallet(userUid, balance);
                    walletRef.setValue(wallet)
                            .addOnSuccessListener(aVoid -> Toast.makeText(context, "Balance added successfully", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(context, "Failed to add balance", Toast.LENGTH_SHORT).show());
                }
            } else {
                Toast.makeText(context, "Failed to fetch balance", Toast.LENGTH_SHORT).show();
            }
        });
        // Store the balance in the wallet
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        Button addBalanceButton,order;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            addBalanceButton = itemView.findViewById(R.id.addBalanceButton);
            order=itemView.findViewById(R.id.order_button);
        }
    }
}

