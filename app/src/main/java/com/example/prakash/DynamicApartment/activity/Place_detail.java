package com.example.prakash.DynamicApartment.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.prakash.DynamicApartment.Public_url;
import com.example.prakash.DynamicApartment.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by prakash on 4/18/2017.
 */

public class Place_detail extends AppCompatActivity {
    Intent i;
    String nid,image,name;
    String url= Public_url.placedetail;
    Toolbar toolbar;
    ImageView placeimg;
    TextView placename;
    WebView placedetail;
 ;Button placebtn;
    ProgressDialog pDilog;
    String value;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_detail_layout);
        i=getIntent();

        nid=i.getStringExtra("nid");
        image=i.getStringExtra("image");
        name=i.getStringExtra("name");
        pDilog = ProgressDialog.show(this, null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();




        placeimg=(ImageView)findViewById(R.id.place_image);
        placename=(TextView)findViewById(R.id.place_title);
        placedetail=(WebView)findViewById(R.id.place_description);

        placedetail.setBackgroundColor(Color.parseColor("#00000000"));

        placebtn=(Button)findViewById(R.id.place_button);
        placebtn.setVisibility(View.GONE);
        placebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDirection();
            }
        });


        toolbar=(Toolbar)findViewById(R.id.place_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle(name);

        if (!isOnline())
        {

//            frame = (FrameLayout) mainView.findViewById(R.id.frame_aptdetail);
//
//            TSnackbar snackbar = TSnackbar.make(frame, "No Internet connection", TSnackbar.LENGTH_LONG);
//            snackbar.setActionTextColor(Color.WHITE);
//            View snackbarView = snackbar.getView();
//            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
//            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
//            textView.setTextColor(Color.WHITE);
//            snackbar.show();
        }








        getData();


    }

    void getData()
    {

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url+nid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDilog.dismiss();

                try {
                    JSONObject body=response.getJSONObject("body");

                    JSONArray uid=body.getJSONArray("und");

                    for (int i=0;i< uid.length();i++)
                    {
                        JSONObject obj=uid.getJSONObject(i);
                         value=obj.getString("value");


                    }


                    placedetail.loadData(value, "text/html; charset=UTF-8", null);

                    Picasso.with(Place_detail.this)
                            .load("http://template.nuza.solutions/sites/default/files/"+image)
                            .placeholder(R.drawable.defult)   // optional
                            .error(R.drawable.defult)      // optional
                            .resize(600, 340)
                            .into(placeimg);

                    toolbar.setTitle(name);
                    placename.setText(name);

                    placebtn.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();
                Toast.makeText(Place_detail.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(Place_detail.this);
        queue.add(request);
    }


    void getDirection()
    {
        Log.d("tag", "getDirection: "+placename);
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q="+name+"","Retreat Serviced Apartments");
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
                Toast.makeText(Place_detail.this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
