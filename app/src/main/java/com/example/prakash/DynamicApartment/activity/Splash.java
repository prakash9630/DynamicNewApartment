package com.example.prakash.DynamicApartment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.prakash.DynamicApartment.R;

/**
 * Created by prakash on 3/28/2017.
 */

public class Splash extends AppCompatActivity{
    ImageView icon;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    ProgressBar pBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        pBar=(ProgressBar)findViewById(R.id.progressbar_id);
        icon=(ImageView)findViewById(R.id.splash_logo);


        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_down);

        icon.setAnimation(anim);


        anim.setDuration(1900);
        anim.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();


            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    }

