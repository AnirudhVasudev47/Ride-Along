package com.cse8.rideAlong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

public class SelectAvatarActivity extends AppCompatActivity {

    MaterialCardView avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, avatar7, avatar8;
    CheckBox avatar1CheckBox, avatar2CheckBox, avatar3CheckBox, avatar4CheckBox, avatar5CheckBox, avatar6CheckBox, avatar7CheckBox, avatar8CheckBox;
    SharedPreferences sharedPreferences;
    Button doneWithActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.codedesign.wearhelmetapp", Context.MODE_PRIVATE);

        avatar1CheckBox = findViewById(R.id.avatar1Checkbox);
        avatar2CheckBox = findViewById(R.id.avatar2Checkbox);
        avatar3CheckBox = findViewById(R.id.avatar3Checkbox);
        avatar4CheckBox = findViewById(R.id.avatar4Checkbox);
        avatar5CheckBox = findViewById(R.id.avatar5Checkbox);
        avatar6CheckBox = findViewById(R.id.avatar6Checkbox);
        avatar7CheckBox = findViewById(R.id.avatar7Checkbox);
        avatar8CheckBox = findViewById(R.id.avatar8Checkbox);

        doneWithActivity = findViewById(R.id.doneWithActivity);

        doneWithActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                backToMainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backToMainIntent);
            }
        });

        avatar1CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    avatar2CheckBox.setChecked(false);
                    avatar3CheckBox.setChecked(false);
                    avatar4CheckBox.setChecked(false);
                    avatar5CheckBox.setChecked(false);
                    avatar6CheckBox.setChecked(false);
                    avatar7CheckBox.setChecked(false);
                    avatar8CheckBox.setChecked(false);
                }
            }
        });

        avatar2CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    avatar1CheckBox.setChecked(false);
                    avatar3CheckBox.setChecked(false);
                    avatar4CheckBox.setChecked(false);
                    avatar5CheckBox.setChecked(false);
                    avatar6CheckBox.setChecked(false);
                    avatar7CheckBox.setChecked(false);
                    avatar8CheckBox.setChecked(false);
                }
            }
        });

        avatar3CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    avatar1CheckBox.setChecked(false);
                    avatar2CheckBox.setChecked(false);
                    avatar4CheckBox.setChecked(false);
                    avatar5CheckBox.setChecked(false);
                    avatar6CheckBox.setChecked(false);
                    avatar7CheckBox.setChecked(false);
                    avatar8CheckBox.setChecked(false);
                }
            }
        });

        avatar4CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    avatar1CheckBox.setChecked(false);
                    avatar2CheckBox.setChecked(false);
                    avatar3CheckBox.setChecked(false);
                    avatar5CheckBox.setChecked(false);
                    avatar6CheckBox.setChecked(false);
                    avatar7CheckBox.setChecked(false);
                    avatar8CheckBox.setChecked(false);
                }
            }
        });

        avatar5CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    avatar1CheckBox.setChecked(false);
                    avatar2CheckBox.setChecked(false);
                    avatar3CheckBox.setChecked(false);
                    avatar4CheckBox.setChecked(false);
                    avatar6CheckBox.setChecked(false);
                    avatar7CheckBox.setChecked(false);
                    avatar8CheckBox.setChecked(false);
                }
            }
        });

        avatar6CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    avatar1CheckBox.setChecked(false);
                    avatar2CheckBox.setChecked(false);
                    avatar3CheckBox.setChecked(false);
                    avatar4CheckBox.setChecked(false);
                    avatar5CheckBox.setChecked(false);
                    avatar7CheckBox.setChecked(false);
                    avatar8CheckBox.setChecked(false);
                }
            }
        });

        avatar7CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    avatar1CheckBox.setChecked(false);
                    avatar2CheckBox.setChecked(false);
                    avatar3CheckBox.setChecked(false);
                    avatar4CheckBox.setChecked(false);
                    avatar5CheckBox.setChecked(false);
                    avatar6CheckBox.setChecked(false);
                    avatar8CheckBox.setChecked(false);
                }
            }
        });

        avatar8CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    avatar1CheckBox.setChecked(false);
                    avatar2CheckBox.setChecked(false);
                    avatar3CheckBox.setChecked(false);
                    avatar4CheckBox.setChecked(false);
                    avatar5CheckBox.setChecked(false);
                    avatar6CheckBox.setChecked(false);
                    avatar7CheckBox.setChecked(false);
                }
            }
        });

    }

    public void avatarCheck(View view){
        MaterialCardView materialCardView = (MaterialCardView) view;

        if(materialCardView.getId() == R.id.avatar1){
            avatar1CheckBox.setChecked(true);
            sharedPreferences.edit().putInt("selectedAvatar", R.drawable.avatar1).apply();
        }else if (materialCardView.getId() == R.id.avatar2){
            avatar2CheckBox.setChecked(true);
            sharedPreferences.edit().putInt("selectedAvatar", R.drawable.avatar5).apply();
        }else if (materialCardView.getId() == R.id.avatar3){
            avatar3CheckBox.setChecked(true);
            sharedPreferences.edit().putInt("selectedAvatar", R.drawable.avatar2).apply();
        }else if (materialCardView.getId() == R.id.avatar4){
            avatar4CheckBox.setChecked(true);
            sharedPreferences.edit().putInt("selectedAvatar", R.drawable.avatar6).apply();
        }else if (materialCardView.getId() == R.id.avatar5){
            avatar5CheckBox.setChecked(true);
            sharedPreferences.edit().putInt("selectedAvatar", R.drawable.avatar3).apply();
        }else if (materialCardView.getId() == R.id.avatar6){
            avatar6CheckBox.setChecked(true);
            sharedPreferences.edit().putInt("selectedAvatar", R.drawable.avatar7).apply();
        }else if (materialCardView.getId() == R.id.avatar7){
            avatar7CheckBox.setChecked(true);
            sharedPreferences.edit().putInt("selectedAvatar", R.drawable.avatar8).apply();
        }else{
            avatar8CheckBox.setChecked(true);
            sharedPreferences.edit().putInt("selectedAvatar", R.drawable.avatar4).apply();
        }

    }
}