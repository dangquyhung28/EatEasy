package com.example.eateasy.Fragments.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eateasy.Adapter.Admin.KhachHangAdapter;
import com.example.eateasy.Model.KhachHang;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.KhachHangInterface;
import com.example.eateasy.Retrofit.Utils.KhachHangUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuanLyKhachHangFragment extends Fragment {
    private RecyclerView recyclerView;
    private KhachHangAdapter adapter;
    private ArrayList<KhachHang> khachHangList;
    private EditText searchBar;

    KhachHangInterface khachHangInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_quan_ly_khach_hang, container, false);
        //
        recyclerView = view.findViewById(R.id.rvKhachHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchBar = view.findViewById(R.id.etSearch);
        khachHangInterface = KhachHangUtils.getKhachHangService();
        khachHangList = new ArrayList<>();
        adapter = new KhachHangAdapter(getContext(),khachHangList);
        recyclerView.setAdapter(adapter);
        khachHangInterface.getAllKhachHang().enqueue(new Callback<ArrayList<KhachHang>>() {
            @Override
            public void onResponse(Call<ArrayList<KhachHang>> call, Response<ArrayList<KhachHang>> response) {
                khachHangList.clear();
                khachHangList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<KhachHang>> call, Throwable t) {

            }
        });


        return view;
    }
}