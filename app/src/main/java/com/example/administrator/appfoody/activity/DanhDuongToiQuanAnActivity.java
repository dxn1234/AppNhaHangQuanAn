package com.example.administrator.appfoody.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.appfoody.R;
import com.example.administrator.appfoody.model.DownloadPolyline;
import com.example.administrator.appfoody.model.ParsePollyline;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DanhDuongToiQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback{
    List<Polyline> polylinePaths = new ArrayList<>();
    ParsePollyline parsePollyline;
    DownloadPolyline downloadPolyline;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    double latitude;
    double longtitude;
    SharedPreferences sharedPreferences;
    Location vitrihientai;
    //https://maps.googleapis.com/maps/api/directions/json?origin=112,3336&destination=222,333&key=AIzaSyA2Cb_FlfRkVWJ7ym4FqQW-JP4lRtsSOAo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_duong_toi_quan_an);
        parsePollyline=new ParsePollyline();
        downloadPolyline=new DownloadPolyline();
        latitude=getIntent().getDoubleExtra("latitude",0);
        longtitude=getIntent().getDoubleExtra("longtitude",0);
        sharedPreferences=getSharedPreferences("toado", Context.MODE_PRIVATE);
        vitrihientai=new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));
         mapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
         mapFragment.getMapAsync(this);

        
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        mMap.clear();
        LatLng latLng=new LatLng(vitrihientai.getLatitude(),vitrihientai.getLongitude());
        LatLng vitriquanan=new LatLng(latitude,longtitude);
        MarkerOptions markerOptionsquanan=new MarkerOptions();
        markerOptionsquanan.position(vitriquanan);
        mMap.addMarker(markerOptionsquanan);
        Log.d("kiemtravitrihientai",vitrihientai.getLatitude()+"-"+vitrihientai.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        mMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,14);
        mMap.moveCamera(cameraUpdate);
        String duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=" + vitrihientai.getLatitude() + "," + vitrihientai.getLongitude() + "&destination=" +latitude+"," + longtitude + "&language=vi&key=AIzaSyCy-x_lF4Tx43tkjVvjuSyGwdA_-YAGS14";
        hienThiDuongDanToiQuanAn(googleMap,duongdan);
    }

    public void hienThiDuongDanToiQuanAn(GoogleMap googleMap,String duongDan){
        downloadPolyline.execute(duongDan);
        try {
            String dataJson=downloadPolyline.get();
            Log.d("kiemtradulieu",dataJson);
            List<LatLng>latLngList=ParsePollyline.layDanhSachToaDo(dataJson);
            PolylineOptions polylineOptions=new PolylineOptions();
            for(LatLng toado:latLngList){
                polylineOptions.add(toado);
            }
            polylineOptions.color(R.color.dotham);
            Polyline polyline=googleMap.addPolyline(polylineOptions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
