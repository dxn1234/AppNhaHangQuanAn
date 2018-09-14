package com.example.administrator.appfoody.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.appfoody.R;

public class AnGiFragment extends Fragment{
    TabLayout tabLayoutangi;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_fragment_angi,container,false);
        anhXa();
        taoGiaoDien();
        return view;
    }

    private void taoGiaoDien() {
        tabLayoutangi.addTab(tabLayoutangi.newTab().setText("Mới nhất"));
        tabLayoutangi.addTab(tabLayoutangi.newTab().setText("Danh mục"));
        tabLayoutangi.addTab(tabLayoutangi.newTab().setText("Hồ Chí Minh"));
        tabLayoutangi.getTabAt(0).setIcon(R.drawable.quarter);
        tabLayoutangi.getTabAt(1).setIcon(R.drawable.quarter);
        tabLayoutangi.getTabAt(2).setIcon(R.drawable.quarter);
    }

    private void anhXa() {
        tabLayoutangi=view.findViewById(R.id.tablayout_angi);
    }
}
