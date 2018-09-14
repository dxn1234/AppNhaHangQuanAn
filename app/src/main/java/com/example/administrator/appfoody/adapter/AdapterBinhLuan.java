package com.example.administrator.appfoody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.model.BinhLuanModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBinhLuan extends BaseAdapter{
    Context context;
    int layout;
    List<BinhLuanModel>binhLuanModelList;

    public AdapterBinhLuan(Context context, int layout, List<BinhLuanModel> binhLuanModelList) {
        this.context = context;
        this.layout = layout;
        this.binhLuanModelList = binhLuanModelList;
    }

    @Override
    public int getCount() {
//        if(binhLuanModelList.size()>5){
//            return 5;
//        }
//        else{
//            return binhLuanModelList.size();
//        }
        return binhLuanModelList.size();
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
        if(context!=null){
            Log.d("kiemtracontext","aha");
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.custom_layout_binhluan,null);
            if(convertView!=null){
                Log.d("kiemtraview","nenu");
            }
            final CircleImageView circleImageView;
            TextView txttieudebinhluan,txtnoidungbinhluan,txtsodiem;
            circleImageView=convertView.findViewById(R.id.cicleImageUser);
            txttieudebinhluan=convertView.findViewById(R.id.txtTieudebinhluan);
            txtnoidungbinhluan=convertView.findViewById(R.id.txtNodungbinhluan);
            txtsodiem=convertView.findViewById(R.id.txtChamDiemBinhLuan);
            txtnoidungbinhluan.setText(binhLuanModelList.get(position).getNoidung());
            txttieudebinhluan.setText(binhLuanModelList.get(position).getTieude());
            txtsodiem.setText(binhLuanModelList.get(position).getChamdiem()+"");
            setHinhanhbinhluan(circleImageView,binhLuanModelList.get(position).getThanhVienModel().getHinhanh());
            return convertView;
        }
        else{
            return null;
        }

        //        AdapterGirdHinhBinhLuan adapterGirdHinhBinhLuan=new AdapterGirdHinhBinhLuan(context,R.layout.custom_layout_hinhbinhluan,binhLuanModelList.get(position).getHinhanhbinhluanList());
//        gridViewhinhbinhluan.setAdapter(adapterGirdHinhBinhLuan);
    }

    public void setHinhanhbinhluan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storagehinhuser=FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE=1024*1024;
        storagehinhuser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}
