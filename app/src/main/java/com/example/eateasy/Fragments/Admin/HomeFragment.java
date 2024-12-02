package com.example.eateasy.Fragments.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import com.example.eateasy.Fragments.Admin.DonNhap.DonNhapFragment;
import com.example.eateasy.Fragments.Admin.SanPham.ProductFragment;
import com.example.eateasy.Model.DoanhThuSanPham;
import com.example.eateasy.Model.DonHang;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.DonHangInterface;
import com.example.eateasy.Retrofit.Utils.DonHangUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    TextView tvXemThem, tvDoanhThu, tvNgay;
    ListView lsDon;
    ArrayList<DonHang> donHangs;
    DonXyLyAdapter donXyLyAdapter;
    ArrayList<DonHang> tongDonHang = new ArrayList<>();

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvNgay = view.findViewById(R.id.tv_today_date);
        tvDoanhThu = view.findViewById(R.id.tv_revenue);
        tvXemThem = view.findViewById(R.id.tv_today_detail);
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
        tvXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaoCaoThongKeFragment baoCaoThongKeFragment = new BaoCaoThongKeFragment();
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_layout_admin, baoCaoThongKeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }
    //doanh thu, loi nhuan, so don
    private void loadDoanhThu() {
        ArrayList<DoanhThuSanPham> doanhThuSanPhams = new ArrayList<>();
        DonHangInterface donHangInterface = DonHangUtils.getHoaDOnBanService();

        donHangInterface.getDoanhThuSanPhamHomNay().enqueue(new Callback<ArrayList<DoanhThuSanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<DoanhThuSanPham>> call, Response<ArrayList<DoanhThuSanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    doanhThuSanPhams.clear();
                    doanhThuSanPhams.addAll(response.body());

                    // Tính toán tổng doanh thu
                    double tongDoanhThu = 0;
                    for (DoanhThuSanPham dt : doanhThuSanPhams) {
                        tongDoanhThu += dt.getDoanhThu();
                    }

                    // Cập nhật UI sau khi có dữ liệu
                    tvDoanhThu.setText(String.format("Doanh thu: %.2f", tongDoanhThu));
                } else {
                    Log.e("API ERROR", "Không nhận được phản hồi hợp lệ từ server");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoanhThuSanPham>> call, Throwable t) {
                Log.e("API ERROR", "Gọi API thất bại: " + t.getMessage());
            }
        });
    }


    private void loadAllHoaDonBan() {
        DonHangInterface donHangInterface;
        donHangInterface = DonHangUtils.getHoaDOnBanService();
        donHangInterface.getAllHoaDonBan().enqueue(new Callback<ArrayList<DonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<DonHang>> call, Response<ArrayList<DonHang>> response) {
                tongDonHang.clear();
                tongDonHang.addAll(response.body());
                loadDoanhThu();
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {

            }
        });
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
                loadAllHoaDonBan();
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {

            }
        });

    }
}