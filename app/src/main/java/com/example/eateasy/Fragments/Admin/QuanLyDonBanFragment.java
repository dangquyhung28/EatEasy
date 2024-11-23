package com.example.eateasy.Fragments.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eateasy.Adapter.Admin.HoaDonBanAdapter;
import com.example.eateasy.Adapter.Admin.HoaDonNhapAdapter;
import com.example.eateasy.Model.HoaDonBan;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.HoaDonBanInterface;
import com.example.eateasy.Retrofit.Utils.HoaDonBanUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 */
public class QuanLyDonBanFragment extends Fragment {
    FloatingActionButton btnAddHDBan;
    private ListView lvHoaDonBan;
    TextView tvSL;
    private HoaDonBanAdapter hoaDonBanAdapter;
    private ArrayList<HoaDonBan> hoaDonBanList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_don_ban, container, false);

        //anh xa
        lvHoaDonBan  = view.findViewById(R.id.list_products);
        tvSL = view.findViewById(R.id.tv_total_products);
        hoaDonBanList = new ArrayList<>();
        loadHoaDonBan();

        btnAddHDBan = view.findViewById(R.id.fab_add_Pro);
        btnAddHDBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonBanFragment donBanFragment = new DonBanFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_layout_admin, donBanFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;

    }

    private void loadHoaDonBan() {
        HoaDonBanInterface hoaDonBanInterface;
        hoaDonBanInterface = HoaDonBanUtils.getHoaDOnBanService();
        hoaDonBanInterface.getAllHoaDonBan().enqueue(new Callback<ArrayList<HoaDonBan>>() {
            @Override
            public void onResponse(Call<ArrayList<HoaDonBan>> call, Response<ArrayList<HoaDonBan>> response) {
                hoaDonBanList.clear();
                hoaDonBanList.addAll(response.body());
                hoaDonBanAdapter = new HoaDonBanAdapter(getContext(),hoaDonBanList);
                lvHoaDonBan.setAdapter(hoaDonBanAdapter);
                tvSL.setText("Tổng số hóa đơn nhập: " + hoaDonBanList.size());
            }

            @Override
            public void onFailure(Call<ArrayList<HoaDonBan>> call, Throwable t) {

            }
        });
    }
}