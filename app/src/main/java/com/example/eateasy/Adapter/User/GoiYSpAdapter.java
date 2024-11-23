package com.example.eateasy.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;

import java.util.ArrayList;
import java.util.List;


public class GoiYSpAdapter extends BaseAdapter {
    private List<SanPham> sanPhamList;
    private Context context;

    public GoiYSpAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
    }

    @Override
    public Object getItem(int position) {
        return sanPhamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate layout for GridView item
            convertView = LayoutInflater.from(context).inflate(R.layout.best_food_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tenSPTextView = convertView.findViewById(R.id.tvFoodName);
            viewHolder.giaBanTextView = convertView.findViewById(R.id.tvFoodPrice);
            viewHolder.imageView = convertView.findViewById(R.id.imgFood);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Bind data to views
        SanPham sanPham = sanPhamList.get(position);
        viewHolder.tenSPTextView.setText(sanPham.getTenSP());
        viewHolder.giaBanTextView.setText(String.format("Giá bán: %.0f VNĐ", sanPham.getGiaBan()));
        String imageUrl = sanPham.getAnhSanPham();

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.avatar) // Hình ảnh mặc định trong trường hợp không tải được
                .error(R.drawable.avatar) // Hình ảnh hiển thị khi có lỗi
                .into(viewHolder.imageView);

        return convertView;
    }

    private static class ViewHolder {
        TextView tenSPTextView, giaBanTextView;
        ImageView imageView;
    }
}

