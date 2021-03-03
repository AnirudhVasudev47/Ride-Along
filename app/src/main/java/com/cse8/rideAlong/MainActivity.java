package com.cse8.rideAlong;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final Integer RESTART_SCHEDULE_TIME = 30 * 60 * 1000;
    public static final Integer JOB_SCHEDULE_ID = 8762;
    private long backPressedTime;
    private Toast backToast;

    ViewPager viewPager;
    SectionPagerAdapter sectionPagerAdapter;
    TabLayout main_tab;

    SharedPreferences sharedPreferences;
    LoadingClass loadingClass;

    DashboardFragment dashboardFragment;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.codedesign.wearhelmetapp", Context.MODE_PRIVATE);
        loadingClass = new LoadingClass(MainActivity.this);
        loadingClass.startLoadingDialog();

        mAuth = FirebaseAuth.getInstance();

        viewPager = findViewById(R.id.tabPager);
        main_tab = findViewById(R.id.main_tab);
        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionPagerAdapter);
        main_tab.setupWithViewPager(viewPager);

        main_tab.getTabAt(0).setIcon(R.drawable.ic_home_button).setText("home");
        main_tab.getTabAt(1).setIcon(R.drawable.gift).setText("rewards");
        main_tab.getTabAt(2).setIcon(R.drawable.profile).setText("profile");

        dashboardFragment = new DashboardFragment();

    }

    @Override
    public void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            Intent intentUserNotLoggedIn = new Intent(getApplicationContext(), StartActivity.class);
            intentUserNotLoggedIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentUserNotLoggedIn);
        } else {
            loadingClass.stopDialog();

//            ComponentName componentName = new ComponentName(getApplicationContext(), UpdateServerScheduler.class);
//            JobInfo info = new JobInfo.Builder(JOB_SCHEDULE_ID, componentName)
//                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                    .setPersisted(true)
//                    .setPeriodic(RESTART_SCHEDULE_TIME)
//                    .build();
//            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//            int resultCode = scheduler.schedule(info);
//            if (resultCode == JobScheduler.RESULT_SUCCESS) {
//                Log.d(TAG, "Job scheduled");
//            } else {
//                Log.d(TAG, "Job scheduling failed");
//            }
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            viewPager.setCurrentItem(0,true);
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


}