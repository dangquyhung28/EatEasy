package com.example.eateasy.Fragments.Admin.BaoCaoThongKe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eateasy.Adapter.Admin.ViewPagerAdapter;
import com.example.eateasy.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import javax.annotation.Nullable;


public class BaoCaoThongKeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bao_cao_thong_ke, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        // Set up Adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity());
        viewPager.setAdapter(adapter);

        // Liên kết ViewPager với TabLayout
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Bán hàng"); break;
                case 1: tab.setText("Lãi lỗ"); break;
                case 2: tab.setText("Kho hàng"); break;
                case 3: tab.setText("Thu chi"); break;
            }
        }).attach();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            TextView toolbarTitle = getActivity().findViewById(R.id.toolbar_title);
            ImageView iconNotification = getActivity().findViewById(R.id.icon_notification);

            toolbarTitle.setText("Báo cáo"); // Change title for ProductFragment
            iconNotification.setImageResource(R.drawable.ic_reports); // Set a different icon if needed
        }
    }



}