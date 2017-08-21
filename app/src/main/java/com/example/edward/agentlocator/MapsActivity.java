package com.example.edward.agentlocator;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.ConnectionResult;


    public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient; //Google play services entry point
    protected Location mLastLocation; //represents Geographical location
        protected String mLatitudeLabel;
        protected String mLongitudeLabel;
        protected TextView mLatitudeText;
        protected TextView mLongitudeText;
    //private FusedLocationProviderClient mFusedLocationClient;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //create GoogleAPICLient Instance
        buildGoogleApiClient();

        Button QueryAgentButton = (Button) findViewById(R.id.btn_query_agent);
            QueryAgentButton.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v){
                    Intent QueryAgentIntent= new Intent(MapsActivity.this, QueryAgentActivity.class);
                    startActivity(QueryAgentIntent);
                }
            });
    }

    protected synchronized void buildGoogleApiClient(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-1.063152, 36.561739);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your are Here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


        @Override
        public void onConnected( Bundle connectionHint) {


        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }

        @Override
        public void onLocationChanged(Location location) {

        }
    }
