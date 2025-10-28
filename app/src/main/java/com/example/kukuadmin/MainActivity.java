package com.example.kukuadmin;

import android.os.Bundle;
import android.view.MenuItem;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Toolbar toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId= item.getItemId();
                if(itemId == R.id.home){
                    loadFragment(new HomeFragment(), false);
                } else if (itemId == R.id.food) {
                    loadFragment(new FoodFragment(),false);
                } else if (itemId == R.id.accessory) {
                    loadFragment(new AccessoryFragment(),false);
                }else {
                    loadFragment(new AccountFragment(),false);
                }
                return true;
            }
        });
        loadFragment(new HomeFragment(),true);
    }
    private void loadFragment(Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if(isAppInitialized){
            fragmentTransaction.add(R.id.frameLayout,fragment);
        }
        else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction.commit();
    }
}