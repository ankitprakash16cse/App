package com.erp.collection;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


public class GeoLocation implements LocationListener {
	Context mContext;
	LocationManager locationManager;
	Globals g;

	public GeoLocation(Context mContext) {
		this.mContext = mContext;
		locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		g = Globals.getInstance(mContext);
		try
		{
			if (locationManager != null)
			{
				if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

					return ;
				}
				Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	            if (lastKnownLocationGPS != null) 
	            {
	            	Location location= lastKnownLocationGPS;
	            	g.latitude=(double) location.getLatitude();
	        		g.longitude =(double) location.getLongitude();
	            } 
	            else 
	            {
	            	Location location= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	            	g.latitude = (double)location.getLatitude();
	        		g.longitude = (double)location.getLongitude();
	            }
	       }
	}
	catch (Exception e) {
		g.latitude = (double)0;
		g.longitude = (double)0;
	}
		g=Globals.getInstance(mContext);
	}
	@Override
	public void onLocationChanged(Location location) 
	{
		try
		{
		g.latitude = (double)location.getLatitude();
		g.longitude = (double)location.getLongitude();
         double speed =(double)location.getSpeed(); //spedd in meter/minute
         speed = (double)(speed*3600)/1000;      // speed in km/minute
		}
		catch (Exception e) {
			g.latitude = (double)0;
			g.longitude = (double)0;

		}
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub		
	}
	
	//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	
 


}
