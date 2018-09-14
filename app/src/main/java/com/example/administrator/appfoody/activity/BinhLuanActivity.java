package com.example.administrator.appfoody.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.model.BinhLuanModel;

public class BinhLuanActivity extends AppCompatActivity{
    SharedPreferences sharedPreferences;
    EditText edttieudebinhluan,edtnoidungbinhluan;
    BinhLuanModel binhLuanModel;
    String maquanan;
    TextView txtdangbinhluan;
    public final int REQUEST_CHONHINHBINHLUAN=11;
    Toolbar toolbar;
    TextView txttenquanan,txtdiachiquanan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);
        anhXa();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        maquanan=getIntent().getStringExtra("maquanan");
        String tenquan=getIntent().getStringExtra("tenquan");
        String diachi=getIntent().getStringExtra("diachi");
        sharedPreferences=getSharedPreferences("Luudangnhap",MODE_PRIVATE);
        txttenquanan.setText(tenquan);
        txtdiachiquanan.setText(diachi);
//        btnchonhinh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentchonhinhbinhluan=new Intent(BinhLuanActivity.this,ChonHinhBinhLuanActivity.class);
//                startActivityForResult(intentchonhinhbinhluan,REQUEST_CHONHINHBINHLUAN);
//            }
//        });
        txtdangbinhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieude=edttieudebinhluan.getText().toString();
                String noidung=edtnoidungbinhluan.getText().toString();
                String mauser=sharedPreferences.getString("mauser","");
                binhLuanModel.setTieude(tieude);
                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setChamdiem(0);
                binhLuanModel.setLuotthich(0);
                binhLuanModel.setMauser(mauser);
                binhLuanModel.themBinhLuan(BinhLuanActivity.this,maquanan,binhLuanModel);
            }
        });
    }

    private void anhXa() {
        edttieudebinhluan=findViewById(R.id.edt_tieudebinhluan);
        edtnoidungbinhluan=findViewById(R.id.edt_noidungbinhluan);
        binhLuanModel=new BinhLuanModel();
        txtdangbinhluan=findViewById(R.id.txt_dangbinhluan);
        txttenquanan=findViewById(R.id.txt_tenquanan);
        txtdiachiquanan=findViewById(R.id.txt_diachiquanan);
        toolbar=findViewById(R.id.toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
