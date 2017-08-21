package com.example.edward.agentlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import static com.example.edward.agentlocator.R.id.btnAgent;
import static com.example.edward.agentlocator.R.id.btnCustomer;

public class UserCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);
        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

        final Button Agentbutton = (Button) findViewById(btnAgent);
        Agentbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(UserCategoryActivity.this, LoginActivity.class);
                startActivity(activityChangeIntent);
            }

        });

        final Button customerButton= (Button) findViewById(btnCustomer);
        customerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentCustomer = new Intent(UserCategoryActivity.this, MapsActivity.class);
                startActivity(intentCustomer);
            }
        });

    }//end of oncreate




}
