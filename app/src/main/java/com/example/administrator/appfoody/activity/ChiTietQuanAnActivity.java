package com.example.administrator.appfoody.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.adapter.AdapterBinhLuan;
import com.example.administrator.appfoody.adapter.AdapterThucDon;
import com.example.administrator.appfoody.interfaces.ThucDonInterface;
import com.example.administrator.appfoody.model.QuanAnModel;
import com.example.administrator.appfoody.model.ThucDonModel;
import com.example.administrator.appfoody.model.TienIchModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback{
    List<ThucDonModel>thucDonModelList;
    ListView lvthucdon;
    ThucDonInterface thucDonInterface;
    ThucDonModel thucDonModel;
    VideoView videoView;
    Button btnbinhluan;
    Button btnchiduong;
    LinearLayout khungtienich;
    GoogleMap mMap;
    ScrollView scrollViewchitietquanan;
    AdapterBinhLuan adapterBinhLuan;
    ListView lvbinhluanchitietquanan;
    Toolbar toolbar;
    TextView txttieudetoolbar,txttenquanan,txtdiachi,txtthoigianhoatdong,txttrangthaihoatdong,txttongsohinhanh,txttongsobinhluan,txttongsocheckin,txttongsoluulai;
    ImageView imghinhanhquanan;
    QuanAnModel quanAnModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);
        final Intent intent=getIntent();
        quanAnModel = (QuanAnModel) intent.getParcelableExtra("quanan");
        anhXa();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ganDuLieu();
        btnchiduong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdanduong=new Intent(ChiTietQuanAnActivity.this,DanhDuongToiQuanAnActivity.class);
                intentdanduong.putExtra("latitude",quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude());
                intentdanduong.putExtra("longtitude",quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
                startActivity(intentdanduong);
            }
        });
        btnbinhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbinhluan=new Intent(ChiTietQuanAnActivity.this,BinhLuanActivity.class);
                intentbinhluan.putExtra("maquanan",quanAnModel.getMaquanan());
                intentbinhluan.putExtra("diachi",quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
                intentbinhluan.putExtra("tenquan",quanAnModel.getTenquanan());
                startActivity(intentbinhluan);
            }
        });
//        final AdapterThucDon adapterThucDon=new AdapterThucDon(this,thucDonModelList);
//        lvthucdon.setAdapter(adapterThucDon);
//
//        thucDonInterface=new ThucDonInterface() {
//            @Override
//            public void getThucDonThanhCong(List<ThucDonModel> thucDonModels) {
//                thucDonModelList.clear();
//                for(int i=0;i<thucDonModels.size();i++){
//                    thucDonModelList.add(thucDonModels.get(i));
//                    adapterThucDon.notifyDataSetChanged();
//                }
//                setListViewHeightBasedOnChildren(lvthucdon);
//                Log.d("kiemtrasizethucdon",thucDonModelList.size()+"");
//                Log.d("kiemtraadapter",adapterThucDon.getCount()+"");
//            }
//        };
//        Log.d("ttt","bbb");
//        thucDonModel.getDanhSachThucDonTheoMaQuanAn(quanAnModel.getMaquanan(),thucDonInterface);

    }

    private void ganDuLieu() {
        scrollViewchitietquanan.smoothScrollTo(0,0);
        txttenquanan.setText(quanAnModel.getTenquanan());
        txtdiachi.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtthoigianhoatdong.setText(quanAnModel.getGiomocua()+"-"+quanAnModel.getGiodongcua());
        txttongsohinhanh.setText(quanAnModel.getHinhanhquanan().size()+"");
        txttongsobinhluan.setText(quanAnModel.getBinhLuanModelList().size()+"");
        txttieudetoolbar.setText(quanAnModel.getTenquanan());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChiTietQuanAnActivity.this,TrangChuActivity.class));
            }
        });
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm");
        String giohientai=dateFormat.format(calendar.getTime());
        String giomocua=quanAnModel.getGiomocua();
        String giodongcua=quanAnModel.getGiodongcua();
        txtthoigianhoatdong.setText("Thời gian mở cửa:"+giomocua+" - "+giodongcua);
//        downLoadHinhTienIch();
        Log.d("kiemtrathoigian",giomocua+"-"+giodongcua);
        try {
            Date datehientai=dateFormat.parse(giohientai);
            Date datemocua=dateFormat.parse(giomocua);
            Date datedongcua=dateFormat.parse(giodongcua);
            if(datehientai.after(datemocua)&&datehientai.before(datedongcua)){
                //giờ mở cửa
                txttrangthaihoatdong.setText("Đang mở cửa");
            }
            else{
                //gio dong cua
                txttrangthaihoatdong.setText("Đã đóng cửa");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StorageReference storageReferencehinhquanan= FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE=1024*1024;
        storageReferencehinhquanan.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imghinhanhquanan.setImageBitmap(bitmap);
            }
        });


        if(quanAnModel.getVideogioithieu()!=null){
            videoView.setVisibility(View.VISIBLE);
            imghinhanhquanan.setVisibility(View.GONE);

            StorageReference storagevideo=FirebaseStorage.getInstance().getReference().child("video").child(quanAnModel.getVideogioithieu());
            storagevideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoView.setVideoURI(uri);
                    videoView.setMediaController(new MediaController(ChiTietQuanAnActivity.this));
                    videoView.start();
                    Log.d("kiemtrauri",uri.toString());
                }
            });
        }
        else{
            videoView.setVisibility(View.GONE);
            imghinhanhquanan.setVisibility(View.VISIBLE);
        }

        //load danh sach binh luan cua quan an
        adapterBinhLuan=new AdapterBinhLuan(ChiTietQuanAnActivity.this,R.layout.custom_layout_binhluan,quanAnModel.getBinhLuanModelList());
        lvbinhluanchitietquanan.setAdapter(adapterBinhLuan);
        setListViewHeightBasedOnChildren(lvbinhluanchitietquanan);

    }

    private void anhXa() {
        thucDonModelList=new ArrayList<>();
//        lvthucdon=findViewById(R.id.lv_thucdon);
        thucDonModel=new ThucDonModel();
        videoView=findViewById(R.id.videoTrailer);
        btnbinhluan=findViewById(R.id.btn_binhluan);
        btnchiduong=findViewById(R.id.btn_chiduong);
        khungtienich=findViewById(R.id.khungtienich);
        scrollViewchitietquanan=findViewById(R.id.scrollview_chitietquanan);
        lvbinhluanchitietquanan=findViewById(R.id.lv_binhluanchitietquanan);
        toolbar=findViewById(R.id.toolbar);
        txttongsobinhluan=findViewById(R.id.txt_tongsobinhluan);
        txttenquanan=findViewById(R.id.txt_tenquanan);
        txtdiachi=findViewById(R.id.txt_diachiquanan);
        txtthoigianhoatdong=findViewById(R.id.txt_thoigianhoatdong);
        txttrangthaihoatdong=findViewById(R.id.txt_trangthaihoatdong);
        txttongsohinhanh=findViewById(R.id.txt_tongsohinhanh);
        txttongsocheckin=findViewById(R.id.txt_tongsocheckin);
        txttongsoluulai=findViewById(R.id.txt_tongsoluulai);
        imghinhanhquanan=findViewById(R.id.img_hinhquanan);
        txttieudetoolbar=findViewById(R.id.txt_tieudetoolbar);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        double latitude=quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude();
        double longitude=quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude();
        LatLng latLng=new LatLng(latitude,longitude);
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(quanAnModel.getTenquanan());
        mMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(latLng,14);
        mMap.moveCamera(cameraUpdate);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

//    public void downLoadHinhTienIch(){
//        if(quanAnModel!=null&&quanAnModel.getTienich()!=null){
//            for(String matienich:quanAnModel.getTienich()){
//                DatabaseReference nodetienich= FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
//                nodetienich.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        TienIchModel tienIchModel=dataSnapshot.getValue(TienIchModel.class);
//                        StorageReference storageReferencehinhquanan= FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
//                        long ONE_MEGABYTE=1024*1024;
//                        storageReferencehinhquanan.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                            @Override
//                            public void onSuccess(byte[] bytes) {
//                                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                                ImageView imagetienich=new ImageView(ChiTietQuanAnActivity.this);
//                                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(200,200);
//                                imagetienich.setScaleType(ImageView.ScaleType.FIT_XY);
//                                imagetienich.setLayoutParams(layoutParams);
//                                imagetienich.setImageBitmap(bitmap);
//                                imagetienich.setPadding(5,5,5,5);
//                                layoutParams.setMargins(10,10,10,10);
//                                khungtienich.addView(imagetienich);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        }
//    }
}
