package com.example.kukuadmin;

import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

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

        String home=getIntent().getStringExtra("home");
        if("home".equals(home)){
            loadFragment(new HomeAddFragment());
        }
        String food=getIntent().getStringExtra("food");
        if("food".equals(food)){
            loadFragment(new FoodAddFragment());
        }
        String accessory=getIntent().getStringExtra("accessory");
        if("accessory".equals(accessory)){
            loadFragment(new AccessoryAddFragment());
        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ActionFramlayout, fragment);
        fragmentTransaction.commit();
    }
}