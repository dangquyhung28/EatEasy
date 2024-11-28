package com.example.eateasy.Fragments.Admin.BaoCaoThongKe;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eateasy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BanHangFragment extends Fragment {

    private TextView tvToday, tvThisMonth, tvLastMonth;
    private TextView tvTotalRevenue, tvTotalOrders, tvTotalCustomers, tvAvgPerOrder;
    private BarChart barChartRevenue;
    private ImageView ivSelectTime;

    private LocalDateTime selectedDate; // Giả sử bạn dùng LocalDateTime để xử lý thời gian

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ban_hang, container, false);

        // Ánh xạ các Views
        tvToday = rootView.findViewById(R.id.tvToday);
        tvThisMonth = rootView.findViewById(R.id.tvThisMonth);
        tvLastMonth = rootView.findViewById(R.id.tvLastMonth);
        tvTotalRevenue = rootView.findViewById(R.id.tvTotalRevenue);
        tvTotalOrders = rootView.findViewById(R.id.tvTotalOrders);
        tvTotalCustomers = rootView.findViewById(R.id.tvTotalCustomers);
        tvAvgPerOrder = rootView.findViewById(R.id.tvAvgPerOrder);
        barChartRevenue = rootView.findViewById(R.id.barChartRevenue);
        ivSelectTime = rootView.findViewById(R.id.ivSelectTime);

        // Xử lý sự kiện nhấn vào các lựa chọn thời gian
        tvToday.setOnClickListener(v -> updateReportForToday());
        tvThisMonth.setOnClickListener(v -> updateReportForThisMonth());
        tvLastMonth.setOnClickListener(v -> updateReportForLastMonth());

        // Xử lý sự kiện chọn thời gian tùy chỉnh
        ivSelectTime.setOnClickListener(v -> showDatePickerDialog());

        // Trả về view đã được inflate
        return rootView;
    }

    private void updateReportForToday() {
        // Cập nhật dữ liệu báo cáo cho "Hôm nay"
        // Giả sử bạn có hàm lấy dữ liệu từ API hoặc cơ sở dữ liệu
        updateReportData("Hôm nay");
    }

    private void updateReportForThisMonth() {
        // Cập nhật dữ liệu báo cáo cho "Tháng này"
        updateReportData("Tháng này");
    }

    private void updateReportForLastMonth() {
        // Cập nhật dữ liệu báo cáo cho "Tháng trước"
        updateReportData("Tháng trước");
    }

    private void updateReportData(String timePeriod) {
        // Giả sử bạn đã có hàm này để lấy dữ liệu và cập nhật UI
        // Ở đây bạn sẽ cần gọi API hoặc database để lấy dữ liệu

        // Dữ liệu mẫu (Thay thế bằng dữ liệu thực tế)
        double totalRevenue = 1000000; // Doanh thu
        int totalOrders = 50; // Số đơn hàng
        int totalCustomers = 30; // Số khách hàng
        double avgPerOrder = totalRevenue / totalOrders; // Trung bình đơn hàng

        // Cập nhật các TextView với dữ liệu
        tvTotalRevenue.setText("Doanh thu: " + totalRevenue + " VND");
        tvTotalOrders.setText("Số đơn hàng: " + totalOrders);
        tvTotalCustomers.setText("Số khách hàng: " + totalCustomers);
        tvAvgPerOrder.setText("Trung bình/đơn: " + avgPerOrder + " VND");

        // Cập nhật biểu đồ doanh thu (giả sử bạn đã có dữ liệu biểu đồ)
        updateBarChart();
    }

    private void updateBarChart() {
        // Cập nhật biểu đồ với dữ liệu (Sử dụng MPAndroidChart)
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 500000)); // Dữ liệu ví dụ

        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu");
        BarData barData = new BarData(dataSet);
        barChartRevenue.setData(barData);
        barChartRevenue.invalidate(); // Cập nhật lại biểu đồ
    }

    private void showDatePickerDialog() {
        // Hiển thị DatePicker để người dùng chọn khoảng thời gian tùy chỉnh
        // Ví dụ bạn có thể sử dụng DatePickerDialog hoặc CalendarView
        DatePickerDialog datePickerDialog = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            datePickerDialog = new DatePickerDialog(getActivity(),
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // Xử lý thời gian người dùng chọn
                        selectedDate = LocalDateTime.of(year, monthOfYear + 1, dayOfMonth, 0, 0);
                        // Cập nhật báo cáo theo ngày chọn
                        updateReportData("Ngày tùy chỉnh");
                    }, LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue() - 1, LocalDateTime.now().getDayOfMonth());
        }
        datePickerDialog.show();
    }
}
