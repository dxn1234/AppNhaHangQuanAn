package com.example.administrator.appfoody.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.activity.ChiTietQuanAnActivity;
import com.example.administrator.appfoody.activity.SlashScreenActivity;
import com.example.administrator.appfoody.adapter.AdapterListViewOdau;
import com.example.administrator.appfoody.interfaces.OdauInterface;
import com.example.administrator.appfoody.model.QuanAnModel;

import java.util.ArrayList;
import java.util.List;

public class OdauFragment extends Fragment{
    SharedPreferences sharedPreferences;
    ProgressBar progressBarodau;
    List<QuanAnModel>quanAnModelList;
    QuanAnModel quanAnModel;
    ListView lvodau;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_fragment_odau,container,false);
        anhXa();
        Location vitrihientai=new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
        taoGiaoDien();
        final AdapterListViewOdau adapterListViewOdau=new AdapterListViewOdau(getActivity(),quanAnModelList,R.layout.custom_layout_listview_odau);
        lvodau.setAdapter(adapterListViewOdau);
        OdauInterface odauInterface=new OdauInterface() {
            @Override
            public void getDanhSaachQuanAnModel(QuanAnModel quanAnModel) {
                quanAnModelList.add(quanAnModel);
                adapterListViewOdau.notifyDataSetChanged();
                Log.d("kiemtrasizelistview",lvodau.getAdapter().getCount()+"");
                progressBarodau.setVisibility(View.GONE);
            }
        };
        Log.d("kiemtra","kila");
        quanAnModel.getDanhSachQuanAn(odauInterface,vitrihientai);
        return view;
    }

    private void taoGiaoDien() {
        progressBarodau
                .getIndeterminateDrawable()
                .setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
    }

    private void anhXa() {
        sharedPreferences=getActivity().getSharedPreferences("toado", Context.MODE_PRIVATE);
        progressBarodau=view.findViewById(R.id.progressbar_odau);
        quanAnModelList=new ArrayList<>();
        quanAnModel=new QuanAnModel();
        lvodau=view.findViewById(R.id.lv_odau);
    }
}
