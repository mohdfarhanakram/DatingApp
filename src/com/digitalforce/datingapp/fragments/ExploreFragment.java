package com.digitalforce.datingapp.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.adapter.NearByAdapter;
import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingUrlConstants;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.PicassoEx;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.digitalforce.datingapp.view.BaseActivity;
import com.digitalforce.datingapp.view.ProfileActivity;
import com.digitalforce.datingapp.widgets.MapScrollLayout;
import com.digitalforce.datingapp.widgets.MapScrollLayout.UpdateMapAfterUserInterection;
import com.farru.android.network.ServiceResponse;
import com.farru.android.persistance.AppSharedPreference;
import com.farru.android.utill.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import org.json.JSONException;
import org.json.JSONObject;

public class ExploreFragment extends SupportMapFragment implements UpdateMapAfterUserInterection{

	private GoogleMap googleMap;

	public View mOriginalContentView;
	public MapScrollLayout mMapScrollLayout;

    private ArrayList<UserInfo> mlistNearBy = new ArrayList<UserInfo>();

    private WeakHashMap<String, Object> haspMap;

    private String mSearchKey = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mOriginalContentView = super.onCreateView(inflater, container, savedInstanceState);

		//View view = inflater.inflate(R.layout.map_frame_layout, container, false);

		mMapScrollLayout = new MapScrollLayout(getActivity());
		//mMapScrollLayout = (MapScrollLayout)view.findViewById(R.id.map_layout);
		mMapScrollLayout.setUpdateMapListener(this);

		mMapScrollLayout.addView(mOriginalContentView);

		ImageView cenetrPoint = new ImageView(getActivity());
		cenetrPoint.setImageResource(R.drawable.center_pin);
		cenetrPoint.setScaleType(ScaleType.FIT_XY);
        cenetrPoint.setVisibility(View.GONE);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Utils.dpToPx(60, getActivity()),Utils.dpToPx(60, getActivity()));
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		cenetrPoint.setLayoutParams(layoutParams);

		mMapScrollLayout.addView(cenetrPoint);

		return mMapScrollLayout;

	}

	@Override
	public View getView() {
		return mOriginalContentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		googleMap = getMap();
        if(googleMap!=null){
            setUpMap();
            hitRequestForFetchExploreData(DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "0.0", getActivity()),DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "0.0", getActivity()));
        }


	}

	private void setUpMap() {
		// Hide the zoom controls as the button panel will cover it.
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		float latitude = Float.parseFloat(DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "0.0", getActivity()));
		float longitude = Float.parseFloat(DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "0.0", getActivity()));
		final LatLng USER_CURRENT_LOCATION = new LatLng(latitude,longitude);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(USER_CURRENT_LOCATION, 9));
        setCurrentLocation(USER_CURRENT_LOCATION);

        setMapListener();
	}


	@Override
	public void onUpdateMapAfterUserInterection() {
        /*if(googleMap==null)
            return;
        googleMap.clear();
		LatLng target = googleMap.getCameraPosition().target;
		//ToastCustom.makeText(getActivity(), "New Latitude "+target.latitude+" New Longitude "+target.longitude, 3000);
        setCurrentLocation(target);

        hitRequestForFetchExploreData(target.latitude+"",target.longitude+"");*/
	}


    private void setCurrentLocation(LatLng currentLocation){
        Marker marker = googleMap.addMarker(new MarkerOptions().position(currentLocation).title("N2HIM"));
        marker.setVisible(true);
        //googleMap.setMyLocationEnabled(true);
    }

    private void addMarkers(){
        haspMap = new WeakHashMap <String, Object>();

        setMapListener();

        for (int i = 0; i < mlistNearBy.size(); i++) {
            LatLng pos = new LatLng(Float.parseFloat(mlistNearBy.get(i).getLatitude()), Float.parseFloat(mlistNearBy.get(i).getLongitude()));
            Marker marker = null;

                marker = googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title(mlistNearBy.get(i).getFirstName().trim() + " (" + mlistNearBy.get(i).getAge()+")")
                        .snippet(mlistNearBy.get(i).getDistance()));

            haspMap.put(marker.getId(), mlistNearBy.get(i));

        }

    }

    private void setMapListener(){
        if(googleMap==null)
            return;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                googleMap.clear();
                //ToastCustom.makeText(getActivity(), "New Latitude "+target.latitude+" New Longitude "+target.longitude, 3000);
                setCurrentLocation(latLng);
                hitRequestForFetchExploreData(latLng.latitude+"",latLng.longitude+"");

            }
        });

       /* googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){
            @Override
            public View getInfoWindow(Marker marker) {
                if(marker!=null && marker.isInfoWindowShown()){
                    marker.hideInfoWindow();
                    marker.showInfoWindow();
                }
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_map_window,null);


                UserInfo userInfo = getUserInfo(marker);
                if(userInfo!=null){
                    ((TextView)view.findViewById(R.id.title_txtv)).setText(userInfo.getFirstName());
                    ((TextView)view.findViewById(R.id.des_txtv)).setText(userInfo.getAboutMe());
                    picassoLoad(userInfo.getImage(),((ImageView)view.findViewById(R.id.img_view)));
                }

                return view;
            }
        });
*/

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {


            @Override

            public void onInfoWindowClick(Marker marker) {

                UserInfo userInfo = getUserInfo(marker);
                if(userInfo!=null){
                    Intent i = new Intent(getActivity(), ProfileActivity.class);
                    i.putExtra(AppConstants.SHOW_PROFILE_USER_ID, userInfo.getUserId());
                    startActivity(i);
                }

            }

        });
    }

    private void hitRequestForFetchExploreData(String latitude,String longitude){
        postData(DatingUrlConstants.USER_NEAR_BY_URL, ApiEvent.USER_NEAR_BY_EVENT, getRequestJson(latitude,longitude));
    }
	
	private void getCityName(double lati,double longi)
	{
		Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
		try
		{
			List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
			if(null == addresses || addresses.size()==0){
				return ;
			}
			Address currentPresentAddress = addresses.get(0);
			
			ToastCustom.makeText(getActivity(), "Sublocality: "+currentPresentAddress.getSubLocality()+" City: "+currentPresentAddress.getLocality(), 3000);
		} catch (IOException e) {
		}
	}


    private String getRequestJson(String latitude,String longitude){

        //{"userid":"5", "lat":"28.5470898", "long":"77.3051591", "distance":"10"}

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("userid", DatingAppPreference.getString(DatingAppPreference.USER_ID, "", getActivity()));
            jsonObject.putOpt("lat", latitude);
            jsonObject.putOpt("long", longitude);
            jsonObject.putOpt("distance", AppConstants.NEAR_BY_DISTANCE+"");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.e("Request", jsonObject.toString());
        return jsonObject.toString();
    }


    public void updateUi(ServiceResponse serviceResponse) {

        if(serviceResponse.getErrorCode()==ServiceResponse.SUCCESS){

            switch (serviceResponse.getEventType()) {
                case ApiEvent.SEARCH_EVENT:
                case ApiEvent.USER_NEAR_BY_EVENT:

                    mlistNearBy = (ArrayList<UserInfo>)serviceResponse.getResponseObject();

                    if(mlistNearBy.size()>0){

                       //ToastCustom.makeText(getActivity(),"You have found "+mlistNearBy.size()+" user(s)",3000);

                        if(googleMap!=null){
                            googleMap.clear();
                            setUpMap();
                            addMarkers();
                            LatLng pos = new LatLng(Float.parseFloat(mlistNearBy.get(0).getLatitude()), Float.parseFloat(mlistNearBy.get(0).getLongitude()));
                            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(pos,9,0,0)));
                        }

                    }else{


                    }

                    break;



                default:
                    break;
            }


        }else{

        }

    }


    private void postData(String url, int eventType, String postData) {
        ((BaseActivity)getActivity()).postData(url, eventType, postData,false);
    }

    public void picassoLoad(String url, ImageView imageView) {
        PicassoEx.getPicasso(getActivity()).load(url).error(R.drawable.farhan).placeholder(R.drawable.farhan).fit().into(imageView);
    }

    public Bitmap picassoLoad(String url) throws IOException {
        return PicassoEx.getPicasso(getActivity()).load(url).error(R.drawable.farhan).placeholder(R.drawable.farhan).get();
    }


    private UserInfo getUserInfo(Marker marker){

        for(int i=0; i<mlistNearBy.size();i++){
            LatLng pos = new LatLng(Float.parseFloat(mlistNearBy.get(i).getLatitude()), Float.parseFloat(mlistNearBy.get(i).getLongitude()));
            if(marker.getTitle().equalsIgnoreCase(mlistNearBy.get(i).getFirstName()+ " (" + mlistNearBy.get(i).getAge()+")") && pos.latitude == marker.getPosition().latitude && pos.longitude == marker.getPosition().longitude){
                return mlistNearBy.get(i);

            }

        }
        return null;
    }



}
