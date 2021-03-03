package com.cse8.rideAlong;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pl.pawelkleczkowski.customgauge.CustomGauge;

import static android.widget.Toast.LENGTH_SHORT;

public class DashboardFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;

    SwipeButton swipeBtn;

    FirebaseAuth mAuth;
    int kilometers, points;

    int maxTravelled=20;
    int percentageCovered=0;
    CustomGauge gauge;
    ImageView accountImage;
    MainActivity mainActivity;
    View view;
    TextView pointsEarned, kmsTravelled;

    GoogleSignInClient googleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mainActivity = new MainActivity();

        mAuth = FirebaseAuth.getInstance();

        swipeRefreshLayout = view.findViewById(R.id.refreshDashboard);
        accountImage = view.findViewById(R.id.profileDashboard);
        pointsEarned = view.findViewById(R.id.pointsEarned);
        kmsTravelled = view.findViewById(R.id.kmsTravelled);
        gauge = view.findViewById(R.id.gauge2);

        swipeBtn = view.findViewById(R.id.swipeBtn);

        swipeBtn.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Toast.makeText(getContext(), "State:" + active, LENGTH_SHORT).show();
            }
        });

        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent singOutIntent = new Intent(getActivity(), StartActivity.class);
                startActivity(singOutIntent);
            }
        });
        gauge = view.findViewById(R.id.gauge2);

        refreshData();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    public void refreshData(){
        if(mAuth.getCurrentUser() != null){
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    kilometers =  dataSnapshot.child("kilometer").getValue(Integer.class);
                    points = dataSnapshot.child("points").getValue(Integer.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });

            percentageCovered = (kilometers / maxTravelled) * 100;
            gauge.setValue(percentageCovered);

            kmsTravelled.setText(kilometers + "KMS");
            pointsEarned.setText(points + "pts");
        }
    }

}