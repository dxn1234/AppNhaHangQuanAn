package com.example.administrator.appfoody.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.adapter.AdapterViewpaperTrangChu;

public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener , RadioGroup.OnCheckedChangeListener{
    RadioGroup groupangiodau;
    RadioButton rdodau;
    RadioButton rdangi;
    ViewPager viewPagertrangchu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        anhXa();
        AdapterViewpaperTrangChu adapterViewpaperTrangChu=new AdapterViewpaperTrangChu(getSupportFragmentManager());
        viewPagertrangchu.setAdapter(adapterViewpaperTrangChu);
        viewPagertrangchu.addOnPageChangeListener(this);
        groupangiodau.setOnCheckedChangeListener(this);
    }

    private void anhXa() {
        viewPagertrangchu=findViewById(R.id.viewpaper_trangchu);
        rdodau=findViewById(R.id.rd_odau);
        rdangi=findViewById(R.id.rd_angi);
        groupangiodau=findViewById(R.id.group_odau_angi);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){
            rdodau.setChecked(true);
        }
        else if(position==1){
            rdangi.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId==R.id.rd_odau){
            viewPagertrangchu.setCurrentItem(0);
        }
        else if(checkedId==R.id.rd_angi){
            viewPagertrangchu.setCurrentItem(1);
        }
    }
}
