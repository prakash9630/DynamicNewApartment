package com.example.prakash.DynamicApartment.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prakash.DynamicApartment.R;
import com.example.prakash.DynamicApartment.app.MyApplication;

import java.util.Locale;

/**
 * Created by prakash on 3/30/2017.
 */

public class Location extends Fragment {
    View mainView;

    Button btn;

    TextView textView;
    Typeface tf;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.location_layout,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Location");

        btn=(Button)mainView.findViewById(R.id.location_id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirection();

            }
        });
        textView=(TextView)mainView.findViewById(R.id.location_description);
//
//        tf= Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraLight.ttf");
//        textView.setTypeface(tf);




        return mainView;
    }

//    q=Retreat+Serviced+Apartment

    void getDirection()
    {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=",27.713594, 85.298008, "Retreat Serviced Apartments");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(getContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Location");
    }
}
