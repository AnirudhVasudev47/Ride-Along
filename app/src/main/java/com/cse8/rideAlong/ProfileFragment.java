package com.cse8.rideAlong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    View view;
    View changeAvatar;
    ImageView avatarImageView;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    String username;
    Button editProfile;

    TextView usernameTextView, userDob, userFirstName, userLastName;

    SharedPreferences sharedPreferences;
    int avatarId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences = getContext().getSharedPreferences("com.codedesign.wearhelmetapp", Context.MODE_PRIVATE);

        firebaseAuth = FirebaseAuth.getInstance();
        userDob = view.findViewById(R.id.userDob);
        usernameTextView = view.findViewById(R.id.usernameProfileText);
        userFirstName = view.findViewById(R.id.userFirstName);
        userDob = view.findViewById(R.id.userDob);
        userLastName = view.findViewById(R.id.userLastName);
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.child("username").getValue(String.class);
                username = username.replaceAll("[^a-zA-Z0-9]", "");
                username = username.toLowerCase();
                usernameTextView.setText("@"+username);

                if (!snapshot.child("firstName").getValue(String.class).equals("")){
                    userFirstName.setText(snapshot.child("firstName").getValue(String.class));
                }
                if (!snapshot.child("lastName").getValue(String.class).equals("")){
                    userLastName.setText(snapshot.child("lastName").getValue(String.class));
                }
                if (!snapshot.child("dob").getValue(String.class).equals("")){
                    userDob.setText(snapshot.child("dob").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        avatarImageView = view.findViewById(R.id.avatarImageView);
        avatarId = sharedPreferences.getInt("selectedAvatar", R.drawable.avatar1);
        avatarImageView.setImageResource(avatarId);

        changeAvatar = view.findViewById(R.id.changeAvatar);
        changeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent avatarChangeIntent = new Intent(getActivity(), SelectAvatarActivity.class);
                avatarChangeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(avatarChangeIntent);
            }
        });

        editProfile = view.findViewById(R.id.editProfileButton);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(getContext(), UserInfoEditActivity.class);
                editProfileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(editProfileIntent);
            }
        });

        return view;
    }

}