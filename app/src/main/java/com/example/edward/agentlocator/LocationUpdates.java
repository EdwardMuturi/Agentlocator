package com.example.edward.agentlocator;

import android.icu.text.DateFormat;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationUpdates extends AppCompatActivity {
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_updates);
        mTextView= (TextView) findViewById(R.id.txtlocationUpdates);
        setupLocationRequest();
    }

    protected synchronized void setupLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(mConnectionCallbacks)
                .addOnConnectionFailedListener(mOnConnecteionFailedListener)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    LocationListener mLocationListerner= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location !=null) {
                mTextView.setText(DateFormat.getTimeInstance().format(location.getTime()) + "\n" +
                "Latitude=" + location.getLatitude() + "\n" +
                "Longitude=" + location.getLongitude());
            }
        }
    };

    GoogleApiClient.ConnectionCallbacks mConnectionCallbacks= new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            Log.i("onConnected", "start");
            try{
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListerner);
            } catch (SecurityException e) {
                Log.i("onConnected", "SecurityException: " +e.getMessage());
            }
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };

    GoogleApiClient.OnConnectionFailedListener mOnConnecteionFailedListener= new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Toast.makeText(LocationUpdates.this,connectionResult.toString(), Toast.LENGTH_LONG).show();
            Log.i("onConnected()", "SecurityException: " + connectionResult.toString());
        }
    };
}
