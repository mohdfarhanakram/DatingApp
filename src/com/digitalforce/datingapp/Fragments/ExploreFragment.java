package com.digitalforce.datingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExploreFragment extends SupportMapFragment{

	private GoogleMap googleMap;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
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

		Marker hamburg = googleMap.addMarker(new MarkerOptions().position(USER_CURRENT_LOCATION)
				.title("Dating App"));

		// Move the camera instantly to hamburg with a zoom of 15.
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(USER_CURRENT_LOCATION, 15));

		// Zoom in, animating the camera.
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}


}
