package com.example.edward.agentlocator;

import android.app.Application;

import com.firebase.client.Firebase;
/**
 * Created by edward on 6/9/17.
 */

public class Agentlocator extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);

    }
}
