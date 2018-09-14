package com.example.administrator.appfoody.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.adapter.AdapterChonHinhBinhLuan;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhBinhLuanActivity extends AppCompatActivity {
    AdapterChonHinhBinhLuan adapterChonHinhBinhLuan;
    GridView gridViewchonhinhbinhluan;
    List<String>listduongdan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_hinh_binh_luan);
        anhXa();
        int checkReadExStorage= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(checkReadExStorage!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            getTatCaHinhAnhTrongTheNho();
        }
        adapterChonHinhBinhLuan=new AdapterChonHinhBinhLuan(ChonHinhBinhLuanActivity.this,R.layout.custom_layout_chonhinhbinhluan,listduongdan);
        gridViewchonhinhbinhluan.setAdapter(adapterChonHinhBinhLuan);
    }

    private void anhXa() {
        gridViewchonhinhbinhluan=findViewById(R.id.gridview_chonhinhbinhluan);
        listduongdan=new ArrayList<>();
    }

    public void getTatCaHinhAnhTrongTheNho(){

        String [] projection = {MediaStore.Images.Media.DATA};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            ChonHinhBinhLuanModel chonHinhBinhLuanModel = new ChonHinhBinhLuanModel(duongdan,false);
//
//            listDuongDan.add(chonHinhBinhLuanModel);
//            adapterChonHinhBinhLuan.notifyDataSetChanged();
            Log.d("kiemtraduongdan",duongdan);
            listduongdan.add(duongdan);
            cursor.moveToNext();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getTatCaHinhAnhTrongTheNho();
            }
        }
    }
}
