package com.farru.android.utill;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.*;
import android.os.Build;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.Log;

import com.digitalforce.datingapp.BuildConfig;
import com.farru.android.utill.DateTimeUtils.Format;

/**
 * This class is only to hold location related methods. <br/>
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> <br/>
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 */
public class LocationUtils {

	private static final String	LOG_TAG							= LocationUtils.class.getSimpleName();

	public static final long	RELIABLE_LOCATION_AGE_MINUTES	= 5;

	/**
	 * @param pContext
	 * @return true if any of GPS_PROVIDER or NETWORK_PROVIDER is enabled.
	 */
	public static boolean isLocationEnabled(Context pContext) {
		return isLocationEnabled(pContext, true, true);
	}

	/**
	 * @param pContext
	 * @return true if GPS_PROVIDER is enabled on device
	 */
	public static boolean isGpsEnabled(Context pContext) {
		return isLocationEnabled(pContext, true, false);
	}

	/**
	 * @param pContext
	 * @return true if NETWORK_PROVIDER is enabled on device
	 */
	public static boolean isNetworkProviderEnabled(Context pContext) {
		return isLocationEnabled(pContext, false, true);
	}

	/**
	 * @param pContext
	 * @param pCheckGpsProvider
	 * @param pCheckNetworkProvider
	 * @return true if providers are enabled as per pCheckGpsProvider and
	 *         pCheckNetworkProvider
	 */
	private static boolean isLocationEnabled(Context pContext, boolean pCheckGpsProvider, boolean pCheckNetworkProvider) {
		LocationManager locationManager = (LocationManager) pContext.getSystemService(Context.LOCATION_SERVICE);
		boolean isLocationEnabled = false;
		if (pCheckGpsProvider) {
			isLocationEnabled |= locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}
		if (pCheckNetworkProvider) {
			isLocationEnabled |= locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		}
		return isLocationEnabled;
	}

	/**
	 * Get the most recent location of the user. <br/>
	 * 
	 * @note Requires ACCESS_FINE_LOCATION permission
	 * 
	 * @note use {@link LocationClient} insteat of this method.
	 * 
	 * @param pContext
	 */
	
	public static Location getLastKnownLocation(Context pContext) {
		Location mostRecentLocation = null;
		try {
			LocationManager locationManager = (LocationManager) pContext.getSystemService(Context.LOCATION_SERVICE);
			List<String> providersList = locationManager.getAllProviders();
			if (providersList == null || providersList.isEmpty()) {
				return mostRecentLocation;
			}
			for (String provider : providersList) {
				Location location = locationManager.getLastKnownLocation(provider);
				if (location == null) {
					continue;
				}
				if (BuildConfig.DEBUG) {
					Log.i(LOG_TAG, "" + location);
				}
				if (mostRecentLocation == null) {
					mostRecentLocation = location;
				} else if (mostRecentLocation.getTime() < location.getTime()) {
					mostRecentLocation = location;
				} else if (mostRecentLocation.getTime() == location.getTime()) {
					if (mostRecentLocation.getAccuracy() > location.getAccuracy()) {
						mostRecentLocation = location;
					}
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "getLastKnownLocation()", e);
		}
		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "getLastKnownLocation() " + mostRecentLocation);
		}
		return mostRecentLocation;
	}


    public  Location getLocation(Context context) {
        Location location = null;
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            location = locationManager.getLastKnownLocation(provider);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return location;

    }

	/**
	 * @param pContext
	 * @return user's Address based on last known location
	 */
	public static Address getAddressByGeoCoder(Context pContext, Location pLocation) {
		try {
			Geocoder geocoder = new Geocoder(pContext);
			List<Address> addressList = geocoder.getFromLocation(pLocation.getLatitude(), pLocation.getLongitude(), 1);
			return addressList.get(0);
		} catch (Exception e) {
			Log.e(LOG_TAG, "getCityByGeoCoder(): " + e.getLocalizedMessage());
			return null;
		}
	}

	/**
	 * @param startLat
	 * @param startLong
	 * @param endLat
	 * @param endLong
	 * @return distance in meters or -1 if method fails
	 */
	public static float getDistance(double startLat, double startLong, double endLat, double endLong) {
		try {
			float[] results = new float[3];
			Location.distanceBetween(startLat, startLong, endLat, endLong, results);
			return results[0];
		} catch (Exception e) {
			Log.e(LOG_TAG, "getDistance() " + e.getLocalizedMessage());
			return -1;
		}
	}

	/**
	 * @param pLocation
	 * @return
	 */
	public static boolean isRecentLocation(Location pLocation) {
		return isRecentLocation(pLocation, RELIABLE_LOCATION_AGE_MINUTES);
	}

	/**
	 * @param pLocation
	 * @param pReliableAgeMinutes
	 * @return
	 */
	public static boolean isRecentLocation(Location pLocation, long pReliableAgeMinutes) {
		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "isRecentLocation() Location Time: " + DateTimeUtils.getFormattedDate(pLocation.getTime(), Format.DD_Mmmm_YYYY_HH_MM));
		}
		long locationAgeMillis = getLocationAge(pLocation);
		return locationAgeMillis < pReliableAgeMinutes * DateUtils.MINUTE_IN_MILLIS;
	}

	/**
	 * @param pLocation
	 * @return location age in milliseconds
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static long getLocationAge(Location pLocation) {
		long locationAgeMillis;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			long bootNanos = SystemClock.elapsedRealtimeNanos();
			long locationNanos = pLocation.getElapsedRealtimeNanos();
			locationAgeMillis = (bootNanos - locationNanos) / 1000 / 1000 / DateUtils.MINUTE_IN_MILLIS;
		} else {
			long currentMillis = System.currentTimeMillis();
			long locationMillis = pLocation.getTime();
			locationAgeMillis = (currentMillis - locationMillis) / DateUtils.MINUTE_IN_MILLIS;
		}
		if (BuildConfig.DEBUG) {
			Log.i(LOG_TAG, "getLocationAge(). Age: " + (locationAgeMillis / 1000f) + " minutes");
		}
		return locationAgeMillis;
	}
}