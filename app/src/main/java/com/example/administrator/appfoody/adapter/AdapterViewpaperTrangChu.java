package com.example.administrator.appfoody.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.appfoody.fragment.AnGiFragment;
import com.example.administrator.appfoody.fragment.OdauFragment;

public class AdapterViewpaperTrangChu extends FragmentStatePagerAdapter{
    AnGiFragment anGiFragment;
    OdauFragment odauFragment;
    public AdapterViewpaperTrangChu(FragmentManager fm) {
        super(fm);
        anGiFragment=new AnGiFragment();
        odauFragment=new OdauFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return odauFragment;
        }
        else if(position==1){
            return anGiFragment;
        }
        else{
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
