package com.example.eateasy.Fragments.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eateasy.Adapter.Admin.GridViewAdapter;
import com.example.eateasy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        GridView gridView = view.findViewById(R.id.grid_view);
        String[] titles = {"Quản lý sản phẩm", "Quản lý đơn hàng", "Báo cáo thống kê", "Quản lý khách hàng"};
        int[] icons = {R.drawable.icon_product_admin, R.drawable.icon_cart_admin, R.drawable.icon_blackboard_admin, R.drawable.ic_user_management}; // Sử dụng các drawable icon của bạn

        GridViewAdapter adapter = new GridViewAdapter(getContext(), titles, icons);
        gridView.setAdapter(adapter);

        return view;
    }
}