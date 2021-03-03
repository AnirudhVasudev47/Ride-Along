package com.cse8.rideAlong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 9009;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.register_illustration)      // Set logo drawable
                        .setTheme(R.style.Theme_AppCompat_NoActionBar)
                        .setTosAndPrivacyPolicyUrls(
                                "https://www.termsfeed.com/blog/terms-conditions-mobile-apps/",
                                "https://www.termsfeed.com/blog/sample-mobile-app-privacy-policy-template/")
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, "User name: " + user, Toast.LENGTH_SHORT).show();

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            if (snapshot.getKey().equals(currentUser)){
                                Intent login = new Intent(getApplicationContext(), MainActivity.class);
                                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(login);
                            }
                        }
                        Map<String, Object> user = new HashMap<String, Object>();
                        user.put("username", mAuth.getCurrentUser().getDisplayName());
                        user.put("kilometer", 0);
                        user.put("points", 0);
                        user.put("number", "");
                        user.put("firstName", "");
                        user.put("lastName", "");
                        user.put("dob", "");
                        user.put("reward", null);

                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser);
                        myRef.setValue(user);


                        Intent freshGoogleLogin = new Intent(getApplicationContext(), MainActivity.class);
                        freshGoogleLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(freshGoogleLogin);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(this, "Failed...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}