package com.example.movieapp.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.movieapp.R;
import com.example.movieapp.appcontext.MovieGlobal;
import com.example.movieapp.home.fragment.AddCinemaFragment;
import com.example.movieapp.home.fragment.GraphFragment;
import com.example.movieapp.home.fragment.HomeFragment;
import com.example.movieapp.home.fragment.LocationFragment;
import com.example.movieapp.home.fragment.MovieMemoirFragment;
import com.example.movieapp.home.fragment.SearchMovFragment;
import com.example.movieapp.home.fragment.WatchListFragment;
import com.example.movieapp.model.Person;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    public Context applicationContext;
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

/*        person = (Person) getIntent().getSerializableExtra("person");
        MovieGlobal movieGlobal = (MovieGlobal) getApplicationContext();
        movieGlobal.setPerson(person);*/

        //adding the toolbar
        Toolbar toolbar =  findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv);

        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //these two lines of code show the navicon drawer icon top left hand side
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("10001", "Name", importance);
            channel.setDescription("Description");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
        replaceHomeFragment();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item)
    {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void replaceHomeFragment(){
        HomeFragment homeFragment = new HomeFragment();
        Bundle b = new Bundle();
        b.putSerializable("person",person);
        homeFragment.setArguments(b);
        replaceFragment(homeFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.showbarPieGrph:
                replaceGraphFragment();
                break;
            case R.id.addMessage:
                replaceHomeFragment();
                break;
            case R.id.displayMessage:
                replaceFragment(new SearchMovFragment());
                break;
            case R.id.addCinemaNav:
                replaceCinemaFragment();
                break;
            case R.id.showmovieMemoir:
                replaceMovieMemoirFeagment();
                break;
            case R.id.googleMap:
                replaceLocationFeagment();
                break;
            case R.id.watchListNav:
                replaceWatchListFragment();
        }
        //this code closes the drawer after you selected an item from the menu, otherwise stay open
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceLocationFeagment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LocationFragment locationFragment = new LocationFragment();
        fragmentTransaction.replace(R.id.content_frame, locationFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void replaceGraphFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GraphFragment graphFragment = new GraphFragment();
        fragmentTransaction.replace(R.id.content_frame, graphFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void replaceMovieMemoirFeagment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MovieMemoirFragment movieMemoirFragment = new MovieMemoirFragment();
        fragmentTransaction.replace(R.id.content_frame, movieMemoirFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void replaceCinemaFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddCinemaFragment addCinemaFragment = new AddCinemaFragment();
        fragmentTransaction.replace(R.id.content_frame, addCinemaFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void replaceFragment(Fragment nextFragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void replaceWatchListFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        WatchListFragment watchListFragment = new WatchListFragment();
        fragmentTransaction.replace(R.id.content_frame, watchListFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
