package com.digitalforce.datingapp.adapter;

import android.content.Context;
import android.view.View;
import com.digitalforce.datingapp.model.UserInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by FARHAN on 11/9/2014.
 */
public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    private Context mContext;
    ArrayList<UserInfo> mUserInfos;

    public MapInfoWindowAdapter(Context context,ArrayList<UserInfo> userInfos){
        mContext = context;
        mUserInfos = userInfos;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {


        return null;
    }
}
