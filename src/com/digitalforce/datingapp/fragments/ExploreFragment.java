package com.digitalforce.datingapp.fragments;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.digitalforce.datingapp.widgets.MapScrollLayout;
import com.digitalforce.datingapp.widgets.MapScrollLayout.UpdateMapAfterUserInterection;
import com.farru.android.persistance.AppSharedPreference;
import com.farru.android.utill.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExploreFragment extends SupportMapFragment implements UpdateMapAfterUserInterection{

	private GoogleMap googleMap;

	public View mOriginalContentView;
	public MapScrollLayout mMapScrollLayout;

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
		}

	}

	private void setUpMap() {
		// Hide the zoom controls as the button panel will cover it.
		googleMap.getUiSettings().setZoomControlsEnabled(false); 

		float latitude = Float.parseFloat(DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LATITUDE, "0.0", getActivity()));
		float longitude = Float.parseFloat(DatingAppPreference.getString(DatingAppPreference.USER_DEVICE_LONGITUDE, "0.0", getActivity()));

		final LatLng USER_CURRENT_LOCATION = new LatLng(latitude,longitude);

		Marker hamburg = googleMap.addMarker(new MarkerOptions().position(USER_CURRENT_LOCATION).title("Dating App"));
		
		hamburg.setVisible(false);

		// Move the camera instantly to hamburg with a zoom of 15.
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(USER_CURRENT_LOCATION, 15));

		// Zoom in, animating the camera.
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		
		googleMap.setMyLocationEnabled(false);

		//googleMap.setOnCameraChangeListener(this);
	}

	/*@Override
	public void onCameraChange(CameraPosition position) {
		LatLng target = position.target;

		ToastCustom.makeText(getActivity(), "New Latitude "+target.latitude+" New Longitude "+target.longitude, 3000);

	}*/

	@Override
	public void onUpdateMapAfterUserInterection() {
		//ToastCustom.makeText(getActivity(), "Listener is called", 3000);

		LatLng target = googleMap.getCameraPosition().target;
		//double lat = latLng.latitude;
		//double lng = latLng.longitude;
		
		ToastCustom.makeText(getActivity(), "New Latitude "+target.latitude+" New Longitude "+target.longitude, 3000);

		//getCityName(target.latitude,target.longitude);

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


}
