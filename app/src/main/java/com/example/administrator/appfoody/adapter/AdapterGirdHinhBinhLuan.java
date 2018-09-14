package com.example.administrator.appfoody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.appfoody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdapterGirdHinhBinhLuan extends BaseAdapter{
    Context context;
    int layout;
    List<String>listHinh;
    public AdapterGirdHinhBinhLuan(Context context, int layout,List<String>listHinh) {
        this.context = context;
        this.layout = layout;
        this.listHinh=listHinh;
    }

    @Override
    public int getCount() {
        return listHinh.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        convertView=layoutInflater.inflate(R.layout.custom_layout_hinhbinhluan,null);
        TextView txtsohinhbinhluan=convertView.findViewById(R.id.txt_sohinhbinhluan);
        final ImageView imghinhbinhluan=convertView.findViewById(R.id.img_binhluan);
        for(String linkhinh:listHinh){
            StorageReference storagehinhuser= FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE=1024*1024;
            storagehinhuser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imghinhbinhluan.setImageBitmap(bitmap);
                }
            });
        }
        return convertView;
    }
}
