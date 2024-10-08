package com.example.eateasy.Adapter.Admin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eateasy.R;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private String[] titles;
    private int[] icons;

    public GridViewAdapter(Context context, String[] titles, int[] icons) {
        this.context = context;
        this.titles = titles;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_admin, parent, false);
        }

        ImageView iconItem = convertView.findViewById(R.id.icon_item);
        TextView titleItem = convertView.findViewById(R.id.title_item);

        iconItem.setImageResource(icons[position]);
        titleItem.setText(titles[position]);

        return convertView;
    }
}
