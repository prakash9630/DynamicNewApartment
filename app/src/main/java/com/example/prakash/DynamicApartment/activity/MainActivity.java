package com.example.prakash.DynamicApartment.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.prakash.DynamicApartment.R;
import com.example.prakash.DynamicApartment.fragment.Apartments;
import com.example.prakash.DynamicApartment.fragment.Booking_form;
import com.example.prakash.DynamicApartment.fragment.Contact_us;
import com.example.prakash.DynamicApartment.fragment.Dashboard;
import com.example.prakash.DynamicApartment.fragment.Facilities;
import com.example.prakash.DynamicApartment.fragment.Feedback;
import com.example.prakash.DynamicApartment.fragment.Gallery;
import com.example.prakash.DynamicApartment.fragment.Location;
import com.example.prakash.DynamicApartment.fragment.Login;
import com.example.prakash.DynamicApartment.fragment.User_orders;
import com.example.prakash.DynamicApartment.fragment.Web;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    boolean doubleBackToExitPressedOnce = false;

FrameLayout frameLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);



//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
//        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

         frameLayout=(FrameLayout)findViewById(R.id.mainFragment);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





FragmentManager fragmentmanager=getSupportFragmentManager();

        FragmentTransaction fragmenttranscation=fragmentmanager.beginTransaction();
 Dashboard dashboard=new Dashboard();
        fragmenttranscation.replace(R.id.mainFragment, dashboard);
        fragmenttranscation.commit();

        hideItem();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else if (!doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = true;


                FrameLayout frameLayout=(FrameLayout)findViewById(R.id.mainFragment);

                Snackbar snackbar = Snackbar
                        .make(frameLayout, "Please click BACK again to exit.", Snackbar.LENGTH_LONG);


                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.BLUE);
                TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();


                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                super.onBackPressed();

            }

        }
    }



    private void hideItem()
    {
        SharedPreferences preferences=getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        String mCookies = preferences.getString("cookies",null);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        if (mCookies != null)
        {
            nav_Menu.findItem(R.id.nav_login).setVisible(false);

        }
        else
        {
            nav_Menu.findItem(R.id.nav_order).setVisible(false);


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        else if (id == R.id.action_web) {

            toolbar.setTitle("Web site");
            setSupportActionBar(toolbar);
            Web web=new Web();
            fragmentTransaction.replace(R.id.mainFragment,web);
            fragmentTransaction.addToBackStack("web");

            return true;
        }

       else if (id == R.id.action_phone) {
            Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:014354252"));
            startActivity(i);

            return true;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fragmentTransaction.commit();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        // Handle navigation view item clicks here.


        FragmentManager fragmentManager = getSupportFragmentManager();

        //middle man
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            toolbar.setTitle("Home");
            setSupportActionBar(toolbar);
            Dashboard fragmentDashboard = new Dashboard();
            fragmentTransaction.replace(R.id.mainFragment, fragmentDashboard);
            fragmentTransaction.addToBackStack(null);




        }
        else if (id==R.id.nav_book)
        {
            toolbar.setTitle("Book now");
            setSupportActionBar(toolbar);
            Booking_form book=new Booking_form();
            Bundle arg=new Bundle();
            arg.putString("unit_name","All");
            book.setArguments(arg);

            fragmentTransaction.replace(R.id.mainFragment,book);
            fragmentTransaction.addToBackStack("book now");

        }
        else if (id == R.id.nav_apartment) {
            toolbar.setTitle("Apartment");
            setSupportActionBar(toolbar);
            Apartments apartment = new Apartments();
            fragmentTransaction.replace(R.id.mainFragment, apartment);
            fragmentTransaction.addToBackStack("apartment");

        }else if (id == R.id.nav_aboutus) {

            toolbar.setTitle("About us");
            setSupportActionBar(toolbar);
            Location location=new Location();
            fragmentTransaction.replace(R.id.mainFragment, location);
            fragmentTransaction.addToBackStack("aboutus");

        }
        else if (id == R.id.nav_gallery) {

            toolbar.setTitle("Gallery");
            setSupportActionBar(toolbar);
        Gallery gallery=new Gallery();
            fragmentTransaction.replace(R.id.mainFragment, gallery);
            fragmentTransaction.addToBackStack("gallery");

        }
        else if (id == R.id.nav_locaiton) {

            toolbar.setTitle("Location");
            setSupportActionBar(toolbar);
            Location location=new Location();
            fragmentTransaction.replace(R.id.mainFragment, location);
            fragmentTransaction.addToBackStack("location");

        } else if (id == R.id.nav_contactus) {
            toolbar.setTitle("Contact");
            setSupportActionBar(toolbar);
            Contact_us contact=new Contact_us();
            fragmentTransaction.replace(R.id.mainFragment, contact);
            fragmentTransaction.addToBackStack("contact");

        }
        else if (id==R.id.nav_travels)
        {
            Intent i=new Intent(this,Travells.class);
            startActivity(i);

        }
        else if (id==R.id.nav_website)
        {
            toolbar.setTitle("Web site");
            setSupportActionBar(toolbar);
            Web web=new Web();
            fragmentTransaction.replace(R.id.mainFragment,web);
            fragmentTransaction.addToBackStack("web");

        }
        else if (id==R.id.nav_facilities)
        {
            toolbar.setTitle("Facilities");
            Facilities facilities=new Facilities();
            fragmentTransaction.replace(R.id.mainFragment,facilities);
            fragmentTransaction.addToBackStack("facilities");

        }
        else if (id==R.id.nav_feedback)
        {
            toolbar.setTitle("Feedback");
            setSupportActionBar(toolbar);
            Feedback feedback=new Feedback();
            fragmentTransaction.replace(R.id.mainFragment,feedback);
            fragmentTransaction.addToBackStack("feedback");

        }
        else if (id==R.id.nav_login)
        {
            toolbar.setTitle("Login");
            setSupportActionBar(toolbar);
            Login login=new Login();
            fragmentTransaction.replace(R.id.mainFragment,login);
            fragmentTransaction.addToBackStack("login");

        }
        else if (id==R.id.nav_order)
        {
            toolbar.setTitle("order");
            setSupportActionBar(toolbar);
            User_orders order=new User_orders();

            fragmentTransaction.replace(R.id.mainFragment,order);
            fragmentTransaction.addToBackStack("order");

        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fragmentTransaction.commit();
        return true;
    }





}
