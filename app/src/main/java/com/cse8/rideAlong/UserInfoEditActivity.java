package com.cse8.rideAlong;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserInfoEditActivity extends AppCompatActivity {

    TextView userDob;
    FirebaseAuth firebaseAuth;
    EditText firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);

        firebaseAuth = FirebaseAuth.getInstance();

        firstName = findViewById(R.id.userFirstNameEditActivity);
        lastName = findViewById(R.id.userLastNameEditActivity);
        userDob = findViewById(R.id.userDobEditActivity);
        userDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate();
            }
        });

    }

    public void handleDate(){
        final Calendar calendar = Calendar.getInstance();
        final int Year = calendar.get(Calendar.YEAR);
        final int Month = calendar.get(Calendar.MONTH);
        final int Day = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(UserInfoEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                month += 1;
                String selectedDate;

                selectedDate = date + " - " + month + " - " + year;
                userDob.setText(selectedDate);

            }
        }, Year, Month, Day);
        datePickerDialog.show();
    }

    public void updateDatabase(View view) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());

        Map<String, Object> userUpdate = new HashMap<>();

        if(!TextUtils.isEmpty(firstName.getText().toString())){
            userUpdate.put("firstName", firstName.getText().toString());
        }
        if(!TextUtils.isEmpty(lastName.getText().toString())){
            userUpdate.put("lastName", lastName.getText().toString());
        }
        userUpdate.put("dob", userDob.getText().toString());

        reference.updateChildren(userUpdate);

        Intent backToProfile = new Intent(UserInfoEditActivity.this, MainActivity.class);
        startActivity(backToProfile);
    }
}