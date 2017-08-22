package com.example.edward.agentlocator;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import org.w3c.dom.Text;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected static final String TAG = "RegisterActivity";
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_PERMISSION_REQUEST_CODE = 34;
    protected EditText mAgentNumber;
    protected EditText mAgentName;
    protected EditText mOperationHours;
    protected Button mRegisterButton;
    
    // Provides the entry point to Google Play services.
    protected GoogleApiClient mGoogleApiClient;

    protected Location mLastLocation;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    protected EditText mAgentName;
    protected EditText mAgentNumber;
    protected EditText mOperationHours;

    private DatabaseReference myFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myFirebase = FirebaseDatabase.getInstance().getReference();
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        //Fused Location Provider instance created
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
                mAgentName= (EditText) findViewById(R.id.txt_agent_name);
                mAgentNumber= (EditText) findViewById(R.id.txt_agent_number);
                mOperationHours= (EditText) findViewById(R.id.txt_operationHours);


            }
        });


        buildGoogleApiClient();
    }


    /**
     * request LocationServices API.
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
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            
            return;
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitudeText.setText(String.format(Locale.ENGLISH, "%s: %f", mLatitudeLabel, mLastLocation.getLatitude()));
                mLongitudeText.setText(String.format(Locale.ENGLISH, "%s: %f", mLongitudeLabel, mLastLocation.getLongitude()));
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


}
