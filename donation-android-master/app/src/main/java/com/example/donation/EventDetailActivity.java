package com.example.donation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.donation.ModelClasses.ModelEventDetail;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView mDetail, Dmatetime, mCall;
//    ImageButton mbtnMap, mbtnCall;

    Button mbtnMap, mbtnCall;
    double valueRating;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private GoogleMap map;
    private FirebaseDatabase mDatabase;
    private DatabaseReference placeRef;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);


        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("EventDetail");

        mDetail = findViewById(R.id.tDetail);
        Dmatetime = findViewById(R.id.tDatetime);

        mbtnMap = findViewById(R.id.btnMap);
        mbtnCall = findViewById(R.id.btnCall);

        String detail = getIntent().getStringExtra("detail");
        String datetime = getIntent().getStringExtra("dt");
        mDetail.setText(detail);
        Dmatetime.setText(datetime);

        database = FirebaseDatabase.getInstance();
//        String keyEvent = getIntent().getStringExtra("keyEvent");
        String keyPlace = getIntent().getStringExtra("keyPlace");

        placeRef = mDatabase.getReference().child("Place").child(keyPlace);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final DatabaseReference placeRef = database.getReference("Place").child(keyPlace);
        placeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ModelEventDetail eventDetail = dataSnapshot.getValue(ModelEventDetail.class);

                if (map != null) {
                    LatLng latLng = new LatLng(Double.parseDouble(eventDetail.latitude), Double.parseDouble(eventDetail.longitude));
                    map.addMarker(new MarkerOptions().position(latLng));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                }

                mbtnMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String label = eventDetail.name;
                        String uriBegin = "geo:" + eventDetail.latitude + "," + eventDetail.longitude;
                        String query = eventDetail.latitude + "," + eventDetail.longitude + "(" + label + ")";
                        String encodedQuery = Uri.encode(query);
                        String uriString = uriBegin + "?q=" + encodedQuery;
                        Uri uri = Uri.parse(uriString);
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });

                mbtnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:" + eventDetail.phonenumber));
                                startActivity(callIntent);
                            }
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Toast.makeText(this, keyPlace, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
    }
}
