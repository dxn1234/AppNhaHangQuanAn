package com.example.administrator.appfoody.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class BinhLuanModel implements Parcelable{
    long chamdiem,luotthich ;
    ThanhVienModel thanhVienModel;
    String noidung,tieude, mauser;
    String mabinhluan;
    List<String>hinhanhbinhluanList;

    protected BinhLuanModel(Parcel in) {
        chamdiem = in.readLong();
        luotthich = in.readLong();
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        mabinhluan = in.readString();
        hinhanhbinhluanList = in.createStringArrayList();
        thanhVienModel=in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }

    public List<String> getHinhanhbinhluanList() {
        return hinhanhbinhluanList;
    }

    public void setHinhanhbinhluanList(List<String> hinhanhbinhluanList) {
        this.hinhanhbinhluanList = hinhanhbinhluanList;
    }

    public BinhLuanModel() {
    }

    public long getChamdiem() {
        return chamdiem;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public void setChamdiem(long chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(chamdiem);
        dest.writeLong(luotthich);
        dest.writeString(noidung);
        dest.writeString(tieude);
        dest.writeString(mauser);
        dest.writeString(mabinhluan);
        dest.writeStringList(hinhanhbinhluanList);
        dest.writeParcelable(thanhVienModel,flags);
    }

    public void themBinhLuan(final Context context, String maquanan, BinhLuanModel binhLuanModel){
        DatabaseReference nodebinhluan= FirebaseDatabase.getInstance().getReference().child("binhluans");
        String key=nodebinhluan.child(maquanan).push().getKey();
        nodebinhluan.child(maquanan).child(key).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Bình luận thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
