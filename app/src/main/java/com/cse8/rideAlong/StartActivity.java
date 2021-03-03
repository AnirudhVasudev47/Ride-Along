package com.cse8.rideAlong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    ViewPager sliderViewPager;
    LinearLayout dotsLayout;
    StartSliderAdapter startSliderAdapter;
    TextView[] dots;
    int currentPage;
    Button nextBtn, prevBtn;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sliderViewPager = findViewById(R.id.startViewPager);
        dotsLayout = findViewById(R.id.dotsLayout);
        nextBtn = findViewById(R.id.nextBtn);
        prevBtn = findViewById(R.id.prevBtn);

        dialog = new Dialog(StartActivity.this);
        dialog.setContentView(R.layout.custom_agreement_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.custom_dialog_animation;

        Button agreeBtn = dialog.findViewById(R.id.agreeBtn);

        startSliderAdapter = new StartSliderAdapter(this);

        sliderViewPager.setAdapter(startSliderAdapter);
        addDotsIndicator(0);

        sliderViewPager.addOnPageChangeListener(viewListener);

        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent registerIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(registerIntent);
                dialog.dismiss();
            }
        });

    }
    public void addDotsIndicator(int position){

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i=0; i<dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorSecondary));

            dotsLayout.addView(dots[i]);
        }

        if (dots.length>0) {
            dots[position].setTextColor(getResources().getColor(R.color.purple_200));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);
            currentPage = position;

            if (position == 0){
                nextBtn.setEnabled(true);
                prevBtn.setEnabled(false);
                prevBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText("Skip");
                prevBtn.setText("");
            }else if(position == 1){
                nextBtn.setEnabled(true);
                prevBtn.setEnabled(true);
                prevBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("Skip");
                prevBtn.setText("back");
            }else if (position == 2){
                nextBtn.setEnabled(true);
                prevBtn.setEnabled(true);
                prevBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("Next");
                prevBtn.setText("back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void backBtnCLicked(View view) {
        sliderViewPager.setCurrentItem(currentPage-1);
    }

    public void skipBtnClicked(View view) {

        dialog.show();

    }
}