package com.example.edward.agentlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgentDashboardAtcivity extends AppCompatActivity {
    private EditText mUpdateFloatText;
    private Button mUpdateFloatButton;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_dashboard);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase syncAgents = new Firebase("https://agentlocator-bbfac.firebaseio.com/Agents");
        syncAgents.keepSynced(true);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String agentName = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        updateFloat(agentName);

    }
    public void updateFloat(final String agentName){
        mUpdateFloatText= (EditText) findViewById(R.id.available_float);
        mUpdateFloatButton= (Button) findViewById(R.id.btnUpdate_float);

        mUpdateFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String floatValue= mUpdateFloatText.getText().toString();
                mDatabase.child("Agents").child(agentName).child("float").setValue(floatValue);
                //Toast.makeText(this, R.string.float_update_staus, Toast.LENGTH_LONG).show();
            }
        });
    }
}
