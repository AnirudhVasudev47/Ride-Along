package com.cse8.rideAlong;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;


public class StartSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public StartSliderAdapter(Context context){
        this.context = context;
    }

    //Arrays
    public int[] slide_images = {
            R.drawable.ride,
            R.drawable.earn,
            R.drawable.money

    };

    public String[] slide_headings = {
            "WEAR HELMET AND RIDE SAFE",
            "EARN MONEY",
            "WITHDRAW YOUR CASH"
    };

    public String[] slide_desc = {
            "Your helmet, your ride.\n We pay you for that.",
            "Gain points by connecting the device to your helmet.",
            "Redeem the points for money."
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);


        ImageView slideImageView = view.findViewById(R.id.imageStartSlider);
        TextView slideHeading = view.findViewById(R.id.headingStartSlider);
        TextView slideDesc = view.findViewById(R.id.descStartSlider);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDesc.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
