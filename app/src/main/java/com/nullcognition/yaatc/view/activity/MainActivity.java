package com.nullcognition.yaatc.view.activity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.nullcognition.yaatc.R;
import com.nullcognition.yaatc.di.activity.BaseActivity;
import com.nullcognition.yaatc.view.fragment.LoginFragment;

import javax.inject.Inject;
import javax.inject.Named;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
                                                          GoogleApiClient.OnConnectionFailedListener,
                                                          com.google.android.gms.location.LocationListener{

	private LocationRequest locationRequest;
	private Location        lastLocation;

	public static final String TAG                  = "MainActivity";
	public static final String LOCATION_GAPI_CLIENT = "locationGoogleApiClient";

	@Inject @Named(LOCATION_GAPI_CLIENT) GoogleApiClient googleApiClient;

	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		navigator.startFragment(this, R.id.activity_main_rootLayout, LoginFragment.class);
	}

	@Override protected void onStart(){
		super.onStart();
		googleApiClient.connect();
	}

	@Override protected void onStop(){
		if(googleApiClient.isConnected()){googleApiClient.disconnect();}
		super.onStop();
	}

	@Override public void onConnected(Bundle bundle){ lastKnownUpdate(); }

	public void lastKnownUpdate(){
		locationRequest = LocationRequest.create();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(1);
		locationRequest.setNumUpdates(1);
		LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
	}

	public String getLastLocation(){
		if(lastLocation != null){
			return "Lat: " + String.valueOf(lastLocation.getLatitude()) +
					", Lon: " + String.valueOf(lastLocation.getLongitude());
		}
		return "Unavailable";
	}

	@Override public void onConnectionFailed(ConnectionResult connectionResult){
		Log.i(TAG, "GoogleApiClient connection has failed" + connectionResult.getErrorCode());
	}

	@Override public void onConnectionSuspended(int i){Log.i(TAG, "GoogleApiClient connection has been suspend");}

	@Override public void onLocationChanged(Location location){ lastLocation = location; }

	@Override protected int getActivityLayout(){ return R.layout.activity_main; }

	@Override protected void injectSelf(){ activityComponent.inject(this); }

}
