package com.example.administrator.appfoody.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.appfoody.interfaces.OdauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanAnModel implements Parcelable{
    public DataSnapshot dataRoot;
    boolean giaohang;
    String giodongcua,giomocua,tenquanan,videogioithieu,maquanan;
    List<String> tienich;
    List<BinhLuanModel>binhLuanModelList;
    List<String> hinhanhquanan;
    List<ChiNhanhQuanAnModel>chiNhanhQuanAnModelList;
    List<ThucDonModel> thucDonModels;
    long luotthich;
    DatabaseReference nodeRoot;

    protected QuanAnModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        luotthich = in.readLong();
        chiNhanhQuanAnModelList=new ArrayList<>();
        in.readTypedList(chiNhanhQuanAnModelList,ChiNhanhQuanAnModel.CREATOR);
        binhLuanModelList=new ArrayList<>();
        in.readTypedList(binhLuanModelList,BinhLuanModel.CREATOR);
    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public List<ThucDonModel> getThucDonModels() {
        return thucDonModels;
    }

    public void setThucDonModels(List<ThucDonModel> thucDonModels) {
        this.thucDonModels = thucDonModels;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }
    public QuanAnModel() {
        nodeRoot= FirebaseDatabase.getInstance().getReference();
    }

    public QuanAnModel(boolean giaohang, String giodongcua, String giomocua, String tenquanan, String videogioithieu, String maquanan, List<String> tienich, long luotthich) {
        this.giaohang = giaohang;
        this.giodongcua = giodongcua;
        this.giomocua = giomocua;
        this.tenquanan = tenquanan;
        this.videogioithieu = videogioithieu;
        this.maquanan = maquanan;
        this.tienich = tienich;
        this.luotthich = luotthich;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public void getDanhSachQuanAn(final OdauInterface odauInterface, final Location vitrihientai){
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataRoot=dataSnapshot;
                layDanhSachQuanAn(dataSnapshot,odauInterface,vitrihientai);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        if(dataRoot!=null){
//            layDanhSachQuanAn(dataRoot,odauInterface,vitrihientai);
        }
        else{
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }
    }
    public void layDanhSachQuanAn(DataSnapshot dataSnapshot,OdauInterface odauInterface,Location vitrihientai){
        DataSnapshot dataSnapshotquanan=dataSnapshot.child("quanans");
        //lấy danh sách quán ăn
        for(DataSnapshot valuequanan:dataSnapshotquanan.getChildren()){
            QuanAnModel quanAnModel=valuequanan.getValue(QuanAnModel.class);
            quanAnModel.setMaquanan(valuequanan.getKey());
            //lấy danh sách hình ảnh quán ăn theo mã
            DataSnapshot dataSnapshothinhquanan=dataSnapshot.child("hinhanhquanans").child(valuequanan.getKey());
            List<String>hinhanhlist=new ArrayList<>();
            for(DataSnapshot valuehinhquanan:dataSnapshothinhquanan.getChildren()){
                hinhanhlist.add(valuehinhquanan.getValue(String.class));
            }
            quanAnModel.setHinhanhquanan(hinhanhlist);
            //lấy dánh sách bình luạn của quán ăn
            DataSnapshot snapshotbinhluan=dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
            List<BinhLuanModel>binhLuanModels=new ArrayList<>();
            for(DataSnapshot valuebinhluan:snapshotbinhluan.getChildren()){
                BinhLuanModel binhLuanModel=valuebinhluan.getValue(BinhLuanModel.class);
                binhLuanModel.setMabinhluan(valuebinhluan.getKey());
                ThanhVienModel thanhVienModel=dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);
                List<String>hinhanhbinhluanList=new ArrayList<>();
                DataSnapshot snapshotnodehinhanhbinhluan=dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());
                for(DataSnapshot valuehinhbinhluan:snapshotnodehinhanhbinhluan.getChildren()){
                    hinhanhbinhluanList.add(valuehinhbinhluan.getValue(String.class));
                }
                binhLuanModel.setHinhanhbinhluanList(hinhanhbinhluanList);

                binhLuanModels.add(binhLuanModel);
            }
            quanAnModel.setBinhLuanModelList(binhLuanModels);
            //Lấy chi nhánh quán ăn
            DataSnapshot snapshotchinhanhquanan=dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
            List<ChiNhanhQuanAnModel>chiNhanhQuanAnModels=new ArrayList<>();
            for(DataSnapshot valuechinhanhquanan:snapshotchinhanhquanan.getChildren()){
                ChiNhanhQuanAnModel chiNhanhQuanAnModel=valuechinhanhquanan.getValue(ChiNhanhQuanAnModel.class);
                Location vitriquanan=new Location("");
                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                if(vitrihientai!=null){
                    double khoangcach=vitrihientai.distanceTo(vitriquanan)/1000;
                    Log.d("kiemtrakhoangcach",khoangcach+"-"+chiNhanhQuanAnModel.getDiachi());
                    chiNhanhQuanAnModel.setKhoangcach(khoangcach);
                }
                else{
                    Log.d("kiemtra11","rong");
                }
                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
            }
            quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);
            odauInterface.getDanhSaachQuanAnModel(quanAnModel);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);
        dest.writeString(videogioithieu);
        dest.writeString(maquanan);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhquanan);
        dest.writeLong(luotthich);
        dest.writeTypedList(chiNhanhQuanAnModelList);
        dest.writeTypedList(binhLuanModelList);
    }
}
