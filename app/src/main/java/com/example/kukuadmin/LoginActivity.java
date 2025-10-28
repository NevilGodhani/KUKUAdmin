package com.example.kukuadmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText adminIdEditText, passwordEditText;
    private Button submitBtn;
    private DatabaseReference adminRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        View decoreview= getWindow().getDecorView();
        decoreview.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsets onApplyWindowInsets(@NonNull View v, @NonNull WindowInsets insets) {
                int left=insets.getSystemWindowInsetLeft();
                int top=insets.getSystemWindowInsetTop();
                int right=insets.getSystemWindowInsetRight();
                int bottom=insets.getSystemWindowInsetBottom();
                v.setPadding(left,top,right,bottom);
                return insets.consumeSystemWindowInsets();
            }
        });
        adminIdEditText = findViewById(R.id.adminIdEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        submitBtn = findViewById(R.id.submitBtn);

        // Reference to the admin node in Firebase Realtime Database
        adminRef = FirebaseDatabase.getInstance().getReference("admin");

        // Set onClickListener to handle form submission
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAdminCredentials();
            }
        });
    }
    // Method to validate admin credentials
    private void validateAdminCredentials() {
        String enteredAdminId = adminIdEditText.getText().toString().trim();
        String enteredPassword = passwordEditText.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(enteredAdminId)) {
            adminIdEditText.setError("Admin ID is required");
            adminIdEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(enteredPassword)) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        // Retrieve data from Firebase
        adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isValid = false;

                // Iterate through all admin records
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve admin ID and password from each record
                    String adminId = snapshot.child("adminId").getValue(String.class);
                    String password = snapshot.child("password").getValue(String.class);

                    // Check if the entered credentials match the database record
                    if (enteredAdminId.equals(adminId) && enteredPassword.equals(password)) {
                        isValid = true;
                        break;
                    }
                }

                // Show result based on validation
                if (isValid) {
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Log.e("Database Error", "Failed to read admin data: " + databaseError.getMessage());
                Toast.makeText(LoginActivity.this, "Error accessing database", Toast.LENGTH_SHORT).show();
            }
        });
    }
}