package com.example.mypackage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.content.Context;


public class MainActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Get user location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Keep track of user location.
		// Use callback/listener since requesting immediately may return null location.
		// IMPORTANT: TO GET GPS TO WORK, MAKE SURE THE LOCATION SERVICES ON YOUR PHONE ARE ON.
		// FOR ME, THIS WAS LOCATED IN SETTINGS > LOCATION.
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new Listener());
        // Have another for GPS provider just in case.
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new Listener());
        // Try to request the location immediately
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null){
        	location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null){
        	handleLatLng(location.getLatitude(), location.getLongitude());
        }
        Toast.makeText(getApplicationContext(),
        	"Trying to obtain GPS coordinates. Make sure you have location services on.",
        	Toast.LENGTH_SHORT).show();
    }

    /**
     * Handle lat lng.
     */
    private void handleLatLng(double latitude, double longitude){
        Log.v("TAG", "(" + latitude + "," + longitude + ")");
    }

    /**
     * Listener for changing gps coords.
     */
    private class Listener implements LocationListener {
	    public void onLocationChanged(Location location) {
	        double longitude = location.getLongitude();
	        double latitude = location.getLatitude();
	        handleLatLng(latitude, longitude);
	    }

	    public void onProviderDisabled(String provider){}
	    public void onProviderEnabled(String provider){}
	    public void onStatusChanged(String provider, int status, Bundle extras){}
    }
}
