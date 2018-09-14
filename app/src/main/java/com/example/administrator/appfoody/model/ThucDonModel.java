package com.example.administrator.appfoody.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.appfoody.interfaces.ThucDonInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThucDonModel {
    String mathucdon;
    String tenthucdon;
    List<MonAnModel>monAnModelList;
    public ThucDonModel() {
    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }

    public List<MonAnModel> getMonAnModelList() {
        return monAnModelList;
    }

    public void setMonAnModelList(List<MonAnModel> monAnModelList) {
        this.monAnModelList = monAnModelList;
    }

    public void getDanhSachThucDonTheoMaQuanAn(String maquanan, final ThucDonInterface thucDonInterface){
        DatabaseReference nodeThucDonQuanAn= FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maquanan);
        nodeThucDonQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final List<ThucDonModel>thucDonModels=new ArrayList<>();
                for(DataSnapshot valuethucdon:dataSnapshot.getChildren()){
                    final ThucDonModel thucDonModel=new ThucDonModel();
                    DatabaseReference nodethucdon=FirebaseDatabase.getInstance().getReference().child("thucdons").child(valuethucdon.getKey());
                    nodethucdon.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshotthucdon) {

                            String mathucdon=dataSnapshotthucdon.getKey();
                            thucDonModel.setMathucdon(mathucdon);
                            thucDonModel.setTenthucdon(dataSnapshotthucdon.getValue(String.class));
                            List<MonAnModel>monAnModels=new ArrayList<>();
                            for(DataSnapshot valueMonAn:dataSnapshot.child(mathucdon).getChildren()){
                                MonAnModel monAnModel=valueMonAn.getValue(MonAnModel.class);
                                monAnModel.setMamon(valueMonAn.getKey());
                                Log.d("kiemtratenmon",monAnModel.getTenmon());
                                monAnModels.add(monAnModel);
                            }
                            thucDonModel.setMonAnModelList(monAnModels);
                            thucDonModels.add(thucDonModel);
                            thucDonInterface.getThucDonThanhCong(thucDonModels);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
