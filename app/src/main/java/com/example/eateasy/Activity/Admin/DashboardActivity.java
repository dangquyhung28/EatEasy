package com.example.eateasy.Activity.Admin;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eateasy.Activity.Login_Activity;
import com.example.eateasy.Fragments.Admin.HomeFragment;
import com.example.eateasy.Fragments.Admin.OrderFragment;
import com.example.eateasy.R;
import com.example.eateasy.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
     ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnClickListener(item->{
            switch (item.getId()){
                case R.id.trangchu_admin:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.donhang_admin:
                    replaceFragment(new OrderFragment());
                    break;

            }
            return true;
        });


    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentTransaction replace = fragmentTransaction.replace(R.id.fragment_layout_admin, fragment);
        fragmentTransaction.commit();
    }

}