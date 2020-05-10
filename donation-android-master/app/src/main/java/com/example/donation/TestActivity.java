package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.donation.Activities.LoginActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class TestActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    Double latitude = 0.0;
    Double longitude = 0.0;

    private Button btnsave;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("place");
//        firebaseAuth = FirebaseAuth.getInstance();

        btnsave = findViewById(R.id.bt_save);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, latitude + " : " + longitude, Toast.LENGTH_SHORT).show();

//                ModelMap modelmap = new ModelMap(latitude, longitude);
//                mDatabase.child(firebaseAuth.getUid()).setValue(modelmap);
//                Toast.makeText(TestActivity.this, "Saved", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        // Add a marker in Sydney and move the camera
        LatLng bkk = new LatLng(13.736717, 100.523186);
        mMap.addMarker(new MarkerOptions().position(bkk));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(bkk));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17f));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);
                latitude = latLng.latitude;
                longitude = latLng.longitude;

//                Toast.makeText(TestActivity.this, latitude + " : " + longitude, Toast.LENGTH_SHORT).show();
                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
            }
        });
    }
    private FirebaseAuth firebaseAuth;
    //Log out toolbar
    private void checkUserStatus() {
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }

}
