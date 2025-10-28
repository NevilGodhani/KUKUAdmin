package com.example.kukuadmin;

import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

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
        Toolbar toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        String userId = "";
        userId = getIntent().getStringExtra("USER_ID");

        if ("".equals(userId)) {
            // Handle case where userId is empty (do nothing or show error)
        } else {
            // Pass the userId to the OrderFragment
            OrderFragment orderFragment = OrderFragment.newInstance(userId);

            // Load the fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ActionFramlayout3, orderFragment)  // Replace with your fragment container ID
                    .commit();
        }
    }
}