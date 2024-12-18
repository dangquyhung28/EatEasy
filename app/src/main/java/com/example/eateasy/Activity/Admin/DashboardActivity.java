package com.example.eateasy.Activity.Admin;

//import android.app.Fragment;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eateasy.Fragments.Admin.HomeFragment;
import com.example.eateasy.Fragments.Admin.OrderFragment;
import com.example.eateasy.Fragments.Admin.SanPham.ProductFragment;
import com.example.eateasy.Fragments.Admin.SettingFragment;
import com.example.eateasy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initwiget();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        Fragment selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout_admin, selectedFragment).commit();


    }
    private NavigationBarView.OnItemSelectedListener navListener = item ->{
        int itemId = item.getItemId();
        Fragment selectedFragment = null;
        if(itemId ==R.id.nav_home){
            selectedFragment = new HomeFragment();


        } else if (itemId==R.id.nav_order) {
            selectedFragment = new OrderFragment();
        } else if (itemId==R.id.nav_product) {
            selectedFragment = new ProductFragment();


        } else {
            selectedFragment = new SettingFragment();

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout_admin,selectedFragment).commit();

        return true;
    };

    private void initwiget() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.toolbarAdmin);
    }


}