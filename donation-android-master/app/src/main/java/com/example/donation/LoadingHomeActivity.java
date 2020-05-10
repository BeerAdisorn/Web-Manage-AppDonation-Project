package com.example.donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.donation.Activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadingHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_home);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        if (currentUser != null) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String uid = user.getUid();
            databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String type = dataSnapshot.child("type").getValue().toString().trim();
                    if (type.equals("Owner")) {
                        Intent intent = new Intent(LoadingHomeActivity.this, MainMapsActivity.class);
//                        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        finish();
                    } else if (type.equals("User")) {
                        Intent intent = new Intent(LoadingHomeActivity.this, HomeActivity.class);
//                        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }
    }
}
