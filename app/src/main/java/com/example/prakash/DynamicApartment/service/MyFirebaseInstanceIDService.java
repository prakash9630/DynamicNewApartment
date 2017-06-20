package com.example.prakash.DynamicApartment.service;

/**
 * Created by prakash on 4/21/2017.
 */

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FCM" ;

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        this.registerToken(token);
    }

    private void registerToken(String token){

    }
}
