/**
 * 
 */
package com.digitalforce.datingapp.location;

import android.location.Location;

/**
 * @author FARHAN
 *
 */
public interface LocationListener {

	public static int LOCATION_PROVIDER_OFF = 0;
	public static int LOCATION_FOUND = 1;
	public static int LOCATION_ERROR = 2;
	
	public void onLocationFound(int locationCode,Location location);
}
