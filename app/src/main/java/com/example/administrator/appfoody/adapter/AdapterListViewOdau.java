package com.example.administrator.appfoody.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.activity.ChiTietQuanAnActivity;
import com.example.administrator.appfoody.model.BinhLuanModel;
import com.example.administrator.appfoody.model.ChiNhanhQuanAnModel;
import com.example.administrator.appfoody.model.QuanAnModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListViewOdau extends BaseAdapter{
    Context context;
    List<QuanAnModel>quanAnModelList;
    int resource;

    public AdapterListViewOdau(Context context, List<QuanAnModel> quanAnModelList, int resource) {
        this.context = context;
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return quanAnModelList.size();
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
        Log.d("nguyenduc",position+"");
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        convertView=layoutInflater.inflate(resource,null);
        TextView txtdiachiquananodau;
        TextView txtkhoangcachquananodau;
        txtkhoangcachquananodau=convertView.findViewById(R.id.txtKhoangCachQuanAnODau);
        txtdiachiquananodau=convertView.findViewById(R.id.txtDiaChiQuanAnODau);
        TextView txtdiemtrungbinh=convertView.findViewById(R.id.txtDiemTrungBinhQuanAn);
        TextView txttonghinhbinhluan=convertView.findViewById(R.id.txtTongHinhBinhLuan);
        TextView txttongbinhluan=convertView.findViewById(R.id.txtTongBinhLuan);
        TextView txtchamdiembinhluan1=convertView.findViewById(R.id.txtChamDiemBinhLuan);
        TextView txtchamdiembinhluan2=convertView.findViewById(R.id.txtChamDiemBinhLuan2);
        LinearLayout containerbinhluan1=convertView.findViewById(R.id.containerBinhLuan);
        LinearLayout containerbinhluan2=convertView.findViewById(R.id.containerBinhLuan2);
        TextView txttieudebinhluan1=convertView.findViewById(R.id.txtTieudebinhluan);
        TextView txttieudebinhluan2=convertView.findViewById(R.id.txtTieudebinhluan2);
        TextView txtnoidungbinhluan1=convertView.findViewById(R.id.txtNodungbinhluan);
        TextView txtnoidungbinhluan2=convertView.findViewById(R.id.txtNodungbinhluan2);

        final CircleImageView circleImageuser1=convertView.findViewById(R.id.cicleImageUser);
        CircleImageView circleImageuser2=convertView.findViewById(R.id.cicleImageUser2);
        Button btndatmonodau=convertView.findViewById(R.id.btnDatMonOdau);
        final ImageView imghinhquananodau=convertView.findViewById(R.id.imageHinhQuanAnOdau);
        if(quanAnModelList.get(position).getHinhanhquanan().size()>0){
            StorageReference storagehinhanh= FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModelList.get(position).getHinhanhquanan().get(0));
            long ONE_MEGABYTE=1024*1024;
            storagehinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imghinhquananodau.setImageBitmap(bitmap);
                }
            });

        }
        TextView txttenquananodau=convertView.findViewById(R.id.txtTenQuananOdau);
        txttenquananodau.setText(quanAnModelList.get(position).getTenquanan());
//        if(quanAnModelList.get(position).isGiaohang()){
//            btndatmonodau.setVisibility(View.VISIBLE);
//        }
//        else{
//            btndatmonodau.setVisibility(View.INVISIBLE);
//        }
        if(quanAnModelList.get(position).getBinhLuanModelList().size()>0){
            BinhLuanModel binhLuanModel=quanAnModelList.get(position).getBinhLuanModelList().get(0);
            txttieudebinhluan1.setText(binhLuanModel.getTieude());
            txtnoidungbinhluan1.setText(binhLuanModel.getNoidung());
            txtchamdiembinhluan1.setText(binhLuanModel.getChamdiem()+"");
            setHinhanhbinhluan(circleImageuser1,binhLuanModel.getThanhVienModel().getHinhanh());
            if(quanAnModelList.get(position).getBinhLuanModelList().size()>1){
                BinhLuanModel binhLuanMode2=quanAnModelList.get(position).getBinhLuanModelList().get(1);
                txttieudebinhluan2.setText(binhLuanMode2.getTieude());
                txtnoidungbinhluan2.setText(binhLuanMode2.getNoidung());
                txtchamdiembinhluan2.setText(binhLuanMode2.getChamdiem()+"");
                setHinhanhbinhluan(circleImageuser2,binhLuanMode2.getThanhVienModel().getHinhanh());
            }
            txttongbinhluan.setText(quanAnModelList.get(position).getBinhLuanModelList().size()+"");
            int tongsohinhbinhluan=0;
            double tongdiem=0;
            for(BinhLuanModel binhLuanModel1:quanAnModelList.get(position).getBinhLuanModelList()){
                tongsohinhbinhluan+=binhLuanModel1.getHinhanhbinhluanList().size();
                tongdiem+=binhLuanModel1.getChamdiem();
            }
            tongdiem/=quanAnModelList.get(position).getBinhLuanModelList().size();
            txtdiemtrungbinh.setText(String.format("%.2f",tongdiem));
            if(tongsohinhbinhluan>0){
                txttonghinhbinhluan.setText(tongsohinhbinhluan+"");
            }
        }
        else{
            containerbinhluan1.setVisibility(View.GONE);
            containerbinhluan2.setVisibility(View.GONE);
            txtchamdiembinhluan1.setVisibility(View.GONE);
            txtchamdiembinhluan2.setVisibility(View.GONE);
        }
        //lấy chi nhánh quán ăn và hiển thị địa chỉ và khoảng cách
        if(quanAnModelList.get(position).getChiNhanhQuanAnModelList().size()>0){
            ChiNhanhQuanAnModel chiNhanhQuanAnModeltam=quanAnModelList.get(position).getChiNhanhQuanAnModelList().get(0);
            for(ChiNhanhQuanAnModel chiNhanhQuanAnModel:quanAnModelList.get(position).getChiNhanhQuanAnModelList()){
                if(chiNhanhQuanAnModeltam.getKhoangcach()>chiNhanhQuanAnModel.getKhoangcach()){
                    chiNhanhQuanAnModeltam=chiNhanhQuanAnModel;
                }
            }
            txtdiachiquananodau.setText(chiNhanhQuanAnModeltam.getDiachi());
            txtkhoangcachquananodau.setText(String.format("%.1f",chiNhanhQuanAnModeltam.getKhoangcach())+" km");
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                QuanAnModel quanAnModel=quanAnModelList.get(position);
//                Intent intent=new Intent(context, ChiTietQuanAnActivity.class);
//                intent.putExtra("quanan",quanAnModel);
//                context.startActivity(intent);
            }
        });
        btndatmonodau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuanAnModel quanAnModel=quanAnModelList.get(position);
                Intent intent=new Intent(context, ChiTietQuanAnActivity.class);
                intent.putExtra("quanan",quanAnModel);
                context.startActivity(intent);
            }
        });
        return convertView;
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
