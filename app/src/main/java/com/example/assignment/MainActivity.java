package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.assignment.databinding.ActivityMainBinding;
import com.example.assignment.fragment.HeartRateFragment;
import com.example.assignment.fragment.KcalFragment;
import com.example.assignment.fragment.WalkActivityFragment;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.example.assignment.search.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    ShareButton shareButton;
    CallbackManager callbackManager;
    //setting up the content for facebook sharing
    ShareLinkContent content = new ShareLinkContent.Builder()
            .setContentUrl(Uri.parse("https://developers.facebook.com"))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Main activity", "line1");

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //facebook sharing
//        shareButton = binding.sbPlan;
//        shareButton.setShareContent(content);

        setSupportActionBar(binding.appBar.toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_fragment,
                R.id.heart_rate_fragment,
                R.id.walk_activity_fragment,
                R.id.kcal_fragment,
                R.id.squatup_fragment,
                R.id.ThirdActivity,
                R.id.PlanListActivity,
                R.id.MapActivity,
                R.id.ChartActivity)
                //to display the Navigation button as a drawer symbol,not being shown as an Up button
                .setOpenableLayout(binding.drawerLayout)
                .build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navigationView, navController);
        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(binding.appBar.toolbar, navController, mAppBarConfiguration);


        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

    }

    public void getHeartRateFragment() {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        HeartRateFragment heartRateFragment = new HeartRateFragment();
        fragmentTransaction.replace(R.id.nav_host_fragment, heartRateFragment);
        fragmentTransaction.commit();
    }

    public void getWalkActivityFragment() {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        WalkActivityFragment walkActivityFragment = new WalkActivityFragment();
        fragmentTransaction.replace(R.id.nav_host_fragment, walkActivityFragment);
        fragmentTransaction.commit();
    }

    public void getKcalFragment() {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        KcalFragment kcalFragment = new KcalFragment();
        fragmentTransaction.replace(R.id.nav_host_fragment, kcalFragment);
        fragmentTransaction.commit();
    }

}