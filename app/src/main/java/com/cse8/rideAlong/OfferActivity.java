package com.cse8.rideAlong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OfferActivity extends AppCompatActivity {

    Uri imageUri;
    TextView storeDesc;
    DatabaseReference databaseReference;
    RecyclerView offerRecyclerList;
    DatabaseReference recyclerReference;
    FirebaseRecyclerOptions<Offers> options;
    FirebaseRecyclerAdapter<Offers, OfferViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapseToolbar);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("storeName"));

        ImageView imageView = findViewById(R.id.imageOfferBanner);
        imageUri = Uri.parse(getIntent().getStringExtra("image"));
        Glide.with(this).load(imageUri).into(imageView);

        storeDesc = findViewById(R.id.storeDescOffer);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("rewards").child(getIntent().getStringExtra("storeId"));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeDesc.setText(snapshot.child("storeDesc").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerReference = FirebaseDatabase.getInstance().getReference().child("rewards").child(getIntent().getStringExtra("storeId")).child("offers");

        offerRecyclerList = findViewById(R.id.storeRecyclerViewOffer);
        offerRecyclerList.setHasFixedSize(true);
        offerRecyclerList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        options = new FirebaseRecyclerOptions.Builder<Offers>()
                .setQuery(recyclerReference, Offers.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Offers, OfferViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OfferViewHolder holder, int position, @NonNull Offers model) {

                holder.setOfferTitle(model.title);
                holder.setOfferDesc(model.desc);
                holder.setOfferTarget(model.target);
                holder.setOfferId(getRef(position).getKey());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.expandOffer();
                    }
                });


            }

            @NonNull
            @Override
            public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_child_group,parent,false);
                return new OfferViewHolder(view);
            }
        };

        offerRecyclerList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder {

        boolean notExpanded = true;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setOfferTitle(String title){
            TextView offerTitle = itemView.findViewById(R.id.offerTitle);
            offerTitle.setText(title);
        }
        public void setOfferDesc(String desc){
            TextView offerDesc = itemView.findViewById(R.id.offerDesc);
            offerDesc.setText(desc);
        }
        public void setOfferTarget(String target){
            TextView offerTarget = itemView.findViewById(R.id.offerTarget);
            offerTarget.setText(target);
        }
        public void setOfferId(String offerId){
            TextView offersId = itemView.findViewById(R.id.offerId);
            offersId.setText(offerId);
        }

        public void expandOffer(){
            ConstraintLayout constraintLayout = itemView.findViewById(R.id.expandable);
            if (!notExpanded)
            {
                constraintLayout.setVisibility(View.GONE);
                notExpanded = true;
            }else {
                constraintLayout.setVisibility(View.VISIBLE);
                notExpanded = false;
            }


        }

    }

}