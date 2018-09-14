package com.example.administrator.appfoody.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParsePollyline {
    public ParsePollyline() {
    }

    public static List<LatLng> layDanhSachToaDo(String datajson){
        List<LatLng>latLngs=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(datajson);
            JSONArray routes=jsonObject.getJSONArray("routes");
            for(int i=0;i<routes.length();i++){
                JSONObject jsonObjectArray=routes.getJSONObject(i);
                JSONObject overviewpolyline=jsonObjectArray.getJSONObject("overview_polyline");
                String point=overviewpolyline.getString("points");
                latLngs= PolyUtil.decode(point);
                Log.d("kiemtrapoint",point);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return latLngs;
    }
}
