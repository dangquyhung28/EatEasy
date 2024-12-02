package com.example.eateasy.Adapter.Admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eateasy.Fragments.Admin.BaoCaoThongKe.BanHangFragment;
import com.example.eateasy.Fragments.Admin.BaoCaoThongKe.KhoHangFragment;
import com.example.eateasy.Fragments.Admin.BaoCaoThongKe.LaiLoFragment;
import com.example.eateasy.Fragments.Admin.BaoCaoThongKe.ThuChiFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new BanHangFragment();
            case 1: return new LaiLoFragment();
            case 2: return new KhoHangFragment();
            case 3: return new ThuChiFragment();
            default: return new BanHangFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Số lượng tab
    }
}
