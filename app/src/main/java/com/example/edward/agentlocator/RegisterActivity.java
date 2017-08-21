package com.example.edward.agentlocator;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity implements
        OnConnectionFailedListener, ConnectionCallbacks {

    protected static final String TAG = "RegisterActivity";
    private FusedLocationProviderClient mFusedLocationClient;
    /*protected EditText mAgentNumber;
    protected EditText mAgentName;
    protected EditText mOperationHours;
    protected Button mRegisterButton;*/
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;

    private DatabaseReference myFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myFirebase= FirebaseDatabase.getInstance().getReference();
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);

        TextView LoginTextView = (TextView) findViewById(R.id.txt_ExistingUser);
        LoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });
        Button RegisterButton = (Button) findViewById(R.id.btnRegisterAgent);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postRegstration = new Intent(RegisterActivity.this, AgentDashboardAtcivity.class);
                startActivity(postRegstration);

                mLatitudeLabel = getResources().getString(R.string.latitude_label);
                mLongitudeLabel = getResources().getString(R.string.longitude_label);
                mLatitudeText = (TextView) findViewById((R.id.agent_latitude));
                mLongitudeText = (TextView) findViewById((R.id.agent_longitude));
                /*mAgentName= (EditText) findViewById(R.id.txt_agent_name);
                mAgentNumber= (EditText) findViewById(R.id.txt_agent_number);
                mOperationHours= (EditText) findViewById(R.id.txt_operationHours);*/


            }
        });


        buildGoogleApiClient();
    }



    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,  mLastLocation.getLatitude()));
            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel, mLastLocation.getLongitude()));
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

}
