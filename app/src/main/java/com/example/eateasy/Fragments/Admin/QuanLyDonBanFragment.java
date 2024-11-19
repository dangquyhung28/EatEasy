package com.example.eateasy.Fragments.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eateasy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanLyDonBanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLyDonBanFragment extends Fragment {
    FloatingActionButton btnAddHDBan;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuanLyDonBanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuanLyDonBanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuanLyDonBanFragment newInstance(String param1, String param2) {
        QuanLyDonBanFragment fragment = new QuanLyDonBanFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quan_ly_don_ban, container, false);

        //anh xa
        btnAddHDBan = view.findViewById(R.id.fab_add_Pro);
        btnAddHDBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi người dùng nhấn vào nút, thay thế fragment hiện tại bằng DonBanFragment
                DonBanFragment donBanFragment = new DonBanFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_layout_admin, donBanFragment); // 'fragment_container' là ID của container chứa fragment
                transaction.addToBackStack(null); // Thêm vào back stack để có thể quay lại fragment cũ
                transaction.commit();
            }
        });
        return view;

    }
}