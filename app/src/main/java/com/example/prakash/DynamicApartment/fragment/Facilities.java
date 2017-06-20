package com.example.prakash.DynamicApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidadvance.topsnackbar.TSnackbar;
import com.example.prakash.DynamicApartment.Public_url;
import com.example.prakash.DynamicApartment.R;
import com.example.prakash.DynamicApartment.app.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prakash on 4/10/2017.
 */

public class Facilities extends Fragment {
    View mainView;

    WebView webView;
    String url= Public_url.Services;
    String value;
    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/Raleway-ExtraLight.ttf\")}body {font-family: 'Raleway';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";
    FrameLayout frameLayout;
    ProgressDialog pDilog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.facilities_layout,container,false);

        webView=(WebView)mainView.findViewById(R.id.services_id);
        webView.setBackgroundColor(Color.parseColor("#00000000"));

        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();


        if (!isOnline())

        {



            frameLayout=(FrameLayout)mainView.findViewById(R.id.frame_facilities);




            TSnackbar snackbar = TSnackbar.make(frameLayout, "No Internet connection", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();

        }

getServices();

        return mainView;
    }

    void getServices()
    {

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDilog.dismiss();

                try {
                    JSONObject body=response.getJSONObject("body");

                    JSONArray undarray=body.getJSONArray("und");



                    for (int i=0;i<undarray.length();i++)
                    {

                        JSONObject services=undarray.getJSONObject(i);
                        value=services.getString("value");






                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }




                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("services", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();


                editor.putString("value", value);
                editor.commit();

                String myHtmlString = pish + value + pas;


                webView.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                pDilog.dismiss();


                SharedPreferences sharedPreferences =getActivity().getSharedPreferences("services", Context.MODE_PRIVATE);
                value= sharedPreferences.getString("value", "");
                String myHtmlString = pish + value + pas;


                webView.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);



            }
        });

        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
        MyApplication.getInstance().trackScreenView("Facilities");
    }
}
