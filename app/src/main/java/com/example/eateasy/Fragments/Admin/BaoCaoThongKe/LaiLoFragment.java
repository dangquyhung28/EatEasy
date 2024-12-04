package com.example.eateasy.Fragments.Admin.BaoCaoThongKe;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.eateasy.Model.DoanhThuSanPham;
import com.example.eateasy.Model.SoDonHang;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.DonHangInterface;
import com.example.eateasy.Retrofit.Utils.DonHangUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LaiLoFragment extends Fragment {

    private TextView tvToday, tvThisMonth, tvLastMonth;
    private TextView tvTotalRevenue, tvTotalOrders;
    private BarChart barChartRevenue;
    private ImageView ivSelectTime;
    SoDonHang soDonHangs;
    NumberFormat numberFormat;
    Spinner spinnerSort;
    TableLayout tableSanPham;
    private ArrayList<DoanhThuSanPham> doanhThuData = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lai_lo, container, false);

        // Ánh xạ các Views
        tvToday = rootView.findViewById(R.id.tvToday);
        tvThisMonth = rootView.findViewById(R.id.tvThisMonth);
        tvLastMonth = rootView.findViewById(R.id.tvLastMonth);
        tvTotalRevenue = rootView.findViewById(R.id.tvTotalRevenue);
        tvTotalOrders = rootView.findViewById(R.id.tvTotalOrders);
        barChartRevenue = rootView.findViewById(R.id.barChartRevenue);
        ivSelectTime = rootView.findViewById(R.id.ivSelectTime);
        tableSanPham = rootView.findViewById(R.id.tableSanPham);
        numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(true);

        // Xử lý sự kiện nhấn vào các lựa chọn thời gian
        tvToday.setOnClickListener(v -> {
            updateReportForToday();
            setSelected(tvToday);
        });
        tvThisMonth.setOnClickListener(v -> {
            updateReportForThisMonth();
            setSelected(tvThisMonth);
        } );
        tvLastMonth.setOnClickListener(v ->{
            updateReportForLastMonth();
            setSelected(tvLastMonth);
        } );
        updateReportForToday();
        setSelected(tvToday);
        // Xử lý sự kiện chọn thời gian tùy chỉnh
        ivSelectTime.setOnClickListener(v -> showDatePickerDialog());
        //chi tiet


        // Đặt adapter cho Spinner
        return rootView;
    }

    private void updateReportForToday() {
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
                        tongDoanhThu += dt.getLoiNhuan();
                    }

                    // Cập nhật UI sau khi có dữ liệu
                    String formattedRevenue = numberFormat.format(tongDoanhThu) + " VND";
                    tvTotalRevenue.setText("Lợi nhuận: " + formattedRevenue);
                    //tvTotalRevenue.setText(String.format("Doanh thu: %.2f", tongDoanhThu));
                    loadDon();
                    updateBarChart(doanhThuSanPhams, "gio");
                    loadOrderDetails(doanhThuSanPhams);

                } else {
                    Log.e("API ERROR", "Không nhận được phản hồi hợp lệ từ server");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoanhThuSanPham>> call, Throwable t) {

            }
        });
    }
    private void loadDon() {
        DonHangInterface donHangInterface = DonHangUtils.getHoaDOnBanService();
        donHangInterface.getTongSoDonHang().enqueue(new Callback<SoDonHang>() {
            @Override
            public void onResponse(Call<SoDonHang> call, Response<SoDonHang> response) {
                soDonHangs = response.body();
                tvTotalOrders.setText("Số đơn hàng: " + soDonHangs.getTongSoDonHangHomNay());
            }

            @Override
            public void onFailure(Call<SoDonHang> call, Throwable t) {

            }
        });
    }

    private void updateReportForThisMonth() {
        ArrayList<DoanhThuSanPham> doanhThuSanPhams = new ArrayList<>();
        DonHangInterface donHangInterface = DonHangUtils.getHoaDOnBanService();
        donHangInterface.getDoanhThuSanPhamThangNay().enqueue(new Callback<ArrayList<DoanhThuSanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<DoanhThuSanPham>> call, Response<ArrayList<DoanhThuSanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    doanhThuSanPhams.clear();
                    doanhThuSanPhams.addAll(response.body());


                    // Cập nhật biểu đồ
                    // Tính toán tổng doanh thu
                    double tongDoanhThu = 0;
                    for (DoanhThuSanPham dt : doanhThuSanPhams) {
                        tongDoanhThu += dt.getLoiNhuan();
                    }

                    // Cập nhật UI sau khi có dữ liệu
                    String formattedRevenue = numberFormat.format(tongDoanhThu) + " VND";
                    tvTotalRevenue.setText("Lợi nhuận: " + formattedRevenue);
                    tvTotalOrders.setText("Số đơn hàng: " + soDonHangs.getTongSoDonHangThangNay());
                    updateBarChart(doanhThuSanPhams, "ngay");
                    loadOrderDetails(doanhThuSanPhams);
                } else {
                    Log.e("API ERROR", "Không nhận được phản hồi hợp lệ từ server");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoanhThuSanPham>> call, Throwable t) {

            }
        });
    }

    private void updateReportForLastMonth() {
        ArrayList<DoanhThuSanPham> doanhThuSanPhams = new ArrayList<>();
        DonHangInterface donHangInterface = DonHangUtils.getHoaDOnBanService();
        donHangInterface.getDoanhThuSanPhamThangTruoc().enqueue(new Callback<ArrayList<DoanhThuSanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<DoanhThuSanPham>> call, Response<ArrayList<DoanhThuSanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    doanhThuSanPhams.clear();
                    doanhThuSanPhams.addAll(response.body());

                    // Tính toán tổng doanh thu
                    double tongDoanhThu = 0;
                    for (DoanhThuSanPham dt : doanhThuSanPhams) {
                        tongDoanhThu += dt.getLoiNhuan();
                    }

                    // Cập nhật UI sau khi có dữ liệu
                    String formattedRevenue = numberFormat.format(tongDoanhThu) + " VND";
                    tvTotalRevenue.setText("Lợi nhuận: " + formattedRevenue);
                    tvTotalOrders.setText("Số đơn hàng: " + soDonHangs.getTongSoDonHangThangTruoc());
                    updateBarChart(doanhThuSanPhams, "ngay");
                    loadOrderDetails(doanhThuSanPhams);
                } else {
                    Log.e("API ERROR", "Không nhận được phản hồi hợp lệ từ server");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoanhThuSanPham>> call, Throwable t) {

            }
        });
    }


    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            datePickerDialog = new DatePickerDialog(getActivity(),
                    (view, year, monthOfYear, dayOfMonth) -> {
                    }, LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue() - 1, LocalDateTime.now().getDayOfMonth());
        }
        datePickerDialog.show();
    }
    private void loadOrderDetails(ArrayList<DoanhThuSanPham> doanhThuSanPham) {
        if (doanhThuSanPham != null) {
            // Clear previous rows
            tableSanPham.removeAllViews();

            // Add header row (if not already there)
            TableRow headerRow = new TableRow(getActivity());
            addTextViewToRow(headerRow, "SẢN PHẨM");
            addTextViewToRow(headerRow, "Ngày");
            addTextViewToRow(headerRow, "SL");
            addTextViewToRow(headerRow, "LỢI NHUẬN");
            tableSanPham.addView(headerRow);


            for (DoanhThuSanPham orderDetail : doanhThuSanPham) {
                TableRow row = new TableRow(getActivity());
                addTextViewToRow(row, orderDetail.getTenSP());
                String formattedDate = formatDate(orderDetail.getNgay());
                addTextViewToRow(row, formattedDate);
                addTextViewToRow(row, String.valueOf(orderDetail.getSoLuongBan()));
                addTextViewToRow(row, String.format("%.2f", orderDetail.getLoiNhuan()));
                tableSanPham.addView(row);
            }
            tableSanPham.setVisibility(View.VISIBLE);
        }

    }

    private void addTextViewToRow(TableRow row, String text) {
        TextView textView = new TextView(getActivity());
        textView.setText(text);
        textView.setTextSize(12);
        textView.setPadding(8, 8, 8, 8);
        row.addView(textView);
    }
    public String formatDate(String dateString) {
        // Loại bỏ phần giờ và GMT
        if (dateString != null && dateString.length() > 0) {
            String[] parts = dateString.split(" "); // Tách chuỗi theo khoảng trắng
            if (parts.length >= 4) {
                // Chỉ lấy ngày, tháng, và năm
                return parts[1] + "-" + getMonthNumber(parts[2]) + "-" + parts[3];  // Ví dụ "01-Dec-2024" -> "01-12-2024"
            }
        }
        return "Invalid Date";  // Trường hợp chuỗi ngày không hợp lệ
    }

    private String getMonthNumber(String month) {
        switch (month) {
            case "Jan": return "01";
            case "Feb": return "02";
            case "Mar": return "03";
            case "Apr": return "04";
            case "May": return "05";
            case "Jun": return "06";
            case "Jul": return "07";
            case "Aug": return "08";
            case "Sep": return "09";
            case "Oct": return "10";
            case "Nov": return "11";
            case "Dec": return "12";
            default: return "00";  // Trường hợp không hợp lệ
        }
    }

    private void setSelected(TextView selectedTextView) {
        // Đặt màu sắc cho TextView đã chọn
        selectedTextView.setTextColor(getResources().getColor(R.color.white)); // Ví dụ: Màu đã chọn
        selectedTextView.setBackgroundResource(R.drawable.backgroud_tv); // Ví dụ: Màu nền đã chọn

        // Đặt lại màu cho các TextView còn lại
        if (selectedTextView != tvToday) {
            tvToday.setTextColor(getResources().getColor(R.color.black));
            tvToday.setBackgroundResource(R.drawable.grey_backgound);
        }
        if (selectedTextView != tvThisMonth) {
            tvThisMonth.setTextColor(getResources().getColor(R.color.black));
            tvThisMonth.setBackgroundResource(R.drawable.grey_backgound);
        }
        if (selectedTextView != tvLastMonth) {
            tvLastMonth.setTextColor(getResources().getColor(R.color.black));
            tvLastMonth.setBackgroundResource(R.drawable.grey_backgound);
        }
    }
    //BIEU DO
    private void updateBarChart(ArrayList<DoanhThuSanPham> data, String timeType) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        // Lọc và tạo dữ liệu cho biểu đồ
        for (int i = 0; i < data.size(); i++) {
            DoanhThuSanPham item = data.get(i);
            // Thêm dữ liệu vào trục X (BarEntry)
            entries.add(new BarEntry((float) i, (float) item.getLoiNhuan()));

            // Dựa trên loại thời gian để tạo nhãn phù hợp
            if (timeType.equals("ngay")) {
                String formattedDate = formatDate(item.getNgay());
                labels.add(formattedDate); // Hiển thị ngày
            } else if (timeType.equals("gio")) {

                String formattedDate = formatDate(item.getNgay());
                labels.add(formattedDate); //
            } else {
                String formattedDate = formatDate(item.getNgay());
                labels.add(formattedDate); //
            }
        }

        // Thiết lập BarDataSet và BarData
        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu");
        dataSet.setColors(getResources().getColor(R.color.colorPrimary)); // Thay đổi màu sắc nếu cần
        BarData barData = new BarData(dataSet);

        // Cập nhật dữ liệu biểu đồ
        barChartRevenue.setData(barData);

        // Cài đặt trục X
        XAxis xAxis = barChartRevenue.getXAxis();
        xAxis.setGranularity(1f); // Khoảng cách giữa các giá trị
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(5, true); // Hiển thị tối đa 5 nhãn
        xAxis.setAvoidFirstLastClipping(true); // Tránh nhãn bị cắt ở đầu và cuối
        xAxis.setLabelRotationAngle(-45); // Xoay nhãn 45 độ
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Đưa nhãn xuống dưới

        // Cài đặt ValueFormatter để chỉ hiển thị nhãn khi có dữ liệu
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < labels.size() && !labels.get(index).isEmpty()) {
                    return labels.get(index); // Chỉ hiển thị nhãn nếu có dữ liệu
                }
                return ""; // Nếu không có dữ liệu, không hiển thị nhãn
            }
        };
        xAxis.setValueFormatter(formatter);

        // Đảo chiều trục Y (hiển thị cột bar từ dưới lên)
        YAxis leftAxis = barChartRevenue.getAxisLeft();
        leftAxis.setInverted(false); // Đảo ngược trục Y

        // Cập nhật biểu đồ
        barChartRevenue.invalidate();
    }


}