package com.example.eateasy.Fragments.Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eateasy.Activity.User.SanPhamActivity;
import com.example.eateasy.Adapter.Admin.DonXyLyAdapter;
import com.example.eateasy.Adapter.Admin.GridViewAdapter;
import com.example.eateasy.Adapter.Admin.HoaDonBanAdapter;
import com.example.eateasy.Fragments.Admin.BaoCaoThongKe.BaoCaoThongKeFragment;
import com.example.eateasy.Fragments.Admin.SanPham.ProductFragment;
import com.example.eateasy.Model.DonHang;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.DonHangInterface;
import com.example.eateasy.Retrofit.Utils.DonHangUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    ListView lsDon;
    ArrayList<DonHang> donHangs;
    DonXyLyAdapter donXyLyAdapter;

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            TextView toolbarTitle = getActivity().findViewById(R.id.toolbar_title);
            ImageView iconNotification = getActivity().findViewById(R.id.icon_notification);

            toolbarTitle.setText("Quản lý bán hàng"); // Change title for ProductFragment
            iconNotification.setImageResource(R.drawable.baseline_home_24); // Set a different icon if needed
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lsDon = view.findViewById(R.id.list_products);
        GridView gridView = view.findViewById(R.id.grid_view);
        String[] titles = {"Quản lý sản phẩm", "Quản lý đơn hàng", "Báo cáo thống kê", "Quản lý khách hàng"};
        int[] icons = {R.drawable.icon_product_admin, R.drawable.icon_cart_admin, R.drawable.icon_blackboard_admin, R.drawable.ic_user_management}; // Sử dụng các drawable icon của bạn

        GridViewAdapter adapter = new GridViewAdapter(getContext(), titles, icons);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(((parent, view1, position, id) -> {
            Fragment selectedItem = null;
            switch (position) {
                case 0:
                    selectedItem = new ProductFragment();
                    break;
                case 1:
                    selectedItem = new OrderFragment();
                    break;
                case 2:
                    selectedItem = new BaoCaoThongKeFragment();
                    break;
                case 3:
                    selectedItem = new QuanLyKhachHangFragment();
                    break;
            }
            if(selectedItem!=null && getActivity() !=null){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout_admin, selectedItem).commit();
            }
        }));
        donHangs = new ArrayList<>();
        loadDonHang();

        return view;
    }
    private void loadDonHang() {
        DonHangInterface donHangInterface;
        donHangInterface = DonHangUtils.getHoaDOnBanService();
        donHangInterface.getDonXyLy().enqueue(new Callback<ArrayList<DonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<DonHang>> call, Response<ArrayList<DonHang>> response) {
                donHangs.clear();
                donHangs.addAll(response.body());
                donXyLyAdapter = new DonXyLyAdapter(getContext(), donHangs);
                lsDon.setAdapter(donXyLyAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {

            }
        });

    }
}