package com.example.administrator.appfoody.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.administrator.appfoody.R;

import java.util.List;

public class AdapterChonHinhBinhLuan extends BaseAdapter{
    Context context;
    int layout;
    List<String>listduongdan;

    public AdapterChonHinhBinhLuan(Context context, int layout, List<String> listduongdan) {
        this.context = context;
        this.layout = layout;
        this.listduongdan = listduongdan;
    }

    @Override
    public int getCount() {
        return listduongdan.size();
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
        convertView=layoutInflater.inflate(layout,null);
        ImageView imageView=convertView.findViewById(R.id.img_chonhinhbinhluan);
        CheckBox checkBox=convertView.findViewById(R.id.checkbox_chonhinhbinhluan);
        String duongdan=listduongdan.get(position);
        Uri uri=Uri.parse(duongdan);
        imageView.setImageURI(uri);
        return convertView;
    }
}
