package com.example.prakash.DynamicApartment.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.prakash.DynamicApartment.R;
import com.example.prakash.DynamicApartment.activity.Travells;
import com.example.prakash.DynamicApartment.app.MyApplication;

/**
 * Created by prakash on 3/28/2017.
 */

public class Dashboard extends android.support.v4.app.Fragment {

    View mainView;
    SliderLayout sliderLayout;
    LinearLayout contactus,apartment,gallery,location,travel,facilities,aboutus,booknow;
    int duration=200;
    LinearLayout lineartop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");

        final Animation animScale = AnimationUtils.loadAnimation(getContext(), R.anim.button_animation);






        mainView=inflater.inflate(R.layout.dashboard_layout,container,false);








        aboutus=(LinearLayout) mainView.findViewById(R.id.aboutus_id);
        gallery=(LinearLayout) mainView.findViewById(R.id.gallery_id);
        location=(LinearLayout) mainView.findViewById(R.id.location_id);
        travel=(LinearLayout)mainView.findViewById(R.id.travel_id);
        apartment=(LinearLayout)mainView.findViewById(R.id.apartment_id);
        facilities=(LinearLayout)mainView.findViewById(R.id.linear_facilities);
        contactus=(LinearLayout)mainView.findViewById(R.id.linear_contact);
booknow=(LinearLayout)mainView.findViewById(R.id.booknow);
        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Book now");
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Booking_form form=new Booking_form();
                        transaction.replace(R.id.mainFragment,form);
                        transaction.addToBackStack("book now");
                        transaction.commit();

                    }
                }, duration);
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Facilities");
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Facilities facilities=new Facilities();
                        transaction.replace(R.id.mainFragment,facilities);
                        transaction.addToBackStack("Facilities");
                        transaction.commit();

                    }
                }, duration);
            }
        });


        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){

                        Intent intent=new Intent(getContext(), Travells.class);
                        startActivity(intent);



                    }
                }, duration);




            }
        });
        facilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Facilities");
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Facilities facilities=new Facilities();
                        transaction.replace(R.id.mainFragment,facilities);
                        transaction.addToBackStack("Facilities");
                        transaction.commit();

                    }
                }, duration);

            }
        });


        apartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Apartments");
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Apartments apartments=new Apartments();
                        transaction.replace(R.id.mainFragment,apartments);
                        transaction.addToBackStack("apartment");
                        transaction.commit();

                    }
                }, duration);



            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About us");
                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){

                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        AboutUs aboutus=new AboutUs();

                        transaction.replace(R.id.mainFragment,aboutus);
                        transaction.addToBackStack("aboutus");
                        transaction.commit();

                                         }
                }, duration);





            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Location");
                view.startAnimation(animScale);




                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){

                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Location location=new Location();
                        transaction.replace(R.id.mainFragment,location);
                        transaction.addToBackStack("location");
                        transaction.commit();

                    }
                }, duration);


            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gallery");
                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){

                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Gallery gallery=new Gallery();
                        transaction.replace(R.id.mainFragment,gallery);
                        transaction.addToBackStack("gallery");
                        transaction.commit();

                    }
                }, duration);

            }
        });

        sliderLayout = (SliderLayout) mainView.findViewById(R.id.dashboard_slider);

        if (!isOnline())
        {

//            lineartop=(LinearLayout)mainView.findViewById(R.id.linear_top);
//
//
//
//
//            TSnackbar snackbar = TSnackbar.make(lineartop, "No Internet connection", TSnackbar.LENGTH_LONG);
//            snackbar.setActionTextColor(Color.WHITE);
//            View snackbarView = snackbar.getView();
//            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
//            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
//            textView.setTextColor(Color.WHITE);
//            snackbar.show();






        }


        TextSliderView textSliderView = new TextSliderView(getContext());
        textSliderView
                .description("Apartments")
                .image("https://img.gta5-mods.com/q95/images/single-player-apartment-spg-net/4c12d8-2015-12-20_00007.jpg");

        sliderLayout.addSlider(textSliderView);

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
//        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//                    slider.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(5000);









        return mainView;



    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Home Page");
    }



}
