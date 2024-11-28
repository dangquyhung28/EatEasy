package com.example.eateasy.Adapter.Admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eateasy.Fragments.Admin.DonBan.QuanLyDonBanFragment;
import com.example.eateasy.Fragments.Admin.DonNhap.QuanLyDonNhapFragment;

public class OrderPagerAdapter extends FragmentStateAdapter {


    public OrderPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new QuanLyDonNhapFragment(); // Đơn nhập
            case 1:
                return new QuanLyDonBanFragment();  // Đơn bán
            default:
                return new QuanLyDonNhapFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // We have 2 tabs
    }
}
