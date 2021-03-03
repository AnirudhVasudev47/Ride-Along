package com.cse8.rideAlong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RewardFragment extends Fragment {

    View view;
    RecyclerView rewardsList;
    DatabaseReference reference;
    FirebaseRecyclerAdapter<Rewards, RewardsViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Rewards> options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_reward, container, false);

        reference = FirebaseDatabase.getInstance().getReference().child("rewards");

        rewardsList = view.findViewById(R.id.rewardList);
        rewardsList.setHasFixedSize(true);
        rewardsList.setLayoutManager(new LinearLayoutManager(getContext()));


        options = new FirebaseRecyclerOptions.Builder<Rewards>()
                .setQuery(reference, Rewards.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Rewards, RewardsViewHolder>(options) {
            @NonNull
            @Override
            public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_head_group,parent,false);
                return new RewardsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RewardsViewHolder rewardsViewHolder, int position, @NonNull Rewards rewards) {

                rewardsViewHolder.setName(rewards.storeName);
                rewardsViewHolder.setImage(rewards.storeBanner, getContext());
                rewardsViewHolder.setLogo(rewards.storeLogo, getContext());
                String storeId = getRef(position).getKey();
                rewardsViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ImageView imageView = rewardsViewHolder.view.findViewById(R.id.imageView);
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+rewards.storeBanner+".png");
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Intent offerIntent = new Intent(getContext(), OfferActivity.class);
                                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        (Activity) getContext(), imageView, ViewCompat.getTransitionName(imageView));
                                offerIntent.putExtra("image", uri.toString());
                                offerIntent.putExtra("storeName", rewards.storeName);
                                offerIntent.putExtra("storeId", storeId);
                                offerIntent.putExtra("storeDesc", rewards.storeDesc);
                                startActivity(offerIntent, options.toBundle());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                });

            }
        };

        rewardsList.setAdapter(firebaseRecyclerAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        firebaseRecyclerAdapter.stopListening();
    }

    public static class RewardsViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView storeName;
        ImageView imageView;
        ImageView logoView;

        public RewardsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setName(String name){
            storeName = (TextView) view.findViewById(R.id.name);
            storeName.setText(name);
        }

        public void setImage(String image, Context context){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+image+".png");
            imageView = view.findViewById(R.id.imageView);
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(imageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Failed to download Image", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setLogo(String image, Context context){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+image+".png");
            logoView = view.findViewById(R.id.circleImage);
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(logoView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Failed to download Logo", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}