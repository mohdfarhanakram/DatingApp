/**
 * 
 */
package com.digitalforce.datingapp.location;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import com.farru.android.utill.LocationUtils;

/**
 * @author FARHAN
 *
 */
public class LocationController /*extends AsyncTask<Void, Void, Void>*/{
	
	private Context mContext;
	private LocationListener mLocationListener;
	
	public LocationController(Context context,LocationListener listener){
		mContext = context;
		mLocationListener = listener;
	}
	
	public void execute(){
		if(LocationUtils.isLocationEnabled(mContext)){
			//Location location = LocationUtils.getLastKnownLocation(mContext);
            Location location = (new LocationUtils()).getLocation(mContext);
			if(location!=null){
				mLocationListener.onLocationFound(LocationListener.LOCATION_FOUND, location);
			}else{
				mLocationListener.onLocationFound(-1, null);
			}
		}else{
			mLocationListener.onLocationFound(LocationListener.LOCATION_PROVIDER_OFF, null);
		}
	}

	/*@Override
	protected Void doInBackground(Void... params) {
		if(LocationUtils.isLocationEnabled(mContext)){
			Location location = LocationUtils.getLastKnownLocation(mContext);
			if(location!=null){
				mLocationListener.onLocationFound(LocationListener.LOCATION_FOUND, location);
			}else{
				mLocationListener.onLocationFound(-1, null);
			}
		}else{
			mLocationListener.onLocationFound(LocationListener.LOCATION_PROVIDER_OFF, null);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}*/

}
