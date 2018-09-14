package com.example.administrator.appfoody.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.model.MonAnModel;

import java.util.List;

public class AdapterMonAn extends BaseAdapter{
    Context context;
    List<MonAnModel>monAnModelList;

    public AdapterMonAn(Context context, List<MonAnModel> monAnModelList) {
        this.context = context;
        this.monAnModelList = monAnModelList;
    }

    @Override
    public int getCount() {
        return monAnModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        convertView=layoutInflater.inflate(R.layout.custom_layout_monan,null);
        TextView txttenmonan=convertView.findViewById(R.id.txt_tenmonan);
        MonAnModel monAnModel=monAnModelList.get(position);
        txttenmonan.setText(monAnModel.getTenmon());
        return convertView;
    }
}
