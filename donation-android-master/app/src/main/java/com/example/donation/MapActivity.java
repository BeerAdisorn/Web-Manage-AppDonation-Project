package com.example.donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.donation.ModelClasses.ModelEventDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapActivity extends AppCompatActivity {

    TextView mName, mLatitude, mLongitude;
    Button mbtn;

    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;

    private FirebaseDatabase database;
//    private FirebaseRecyclerOptions<ModelMap> firebaseRecyclerOptions;
//    private FirebaseRecyclerAdapter<ModelMap, ViewHoderMap> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Map");

//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowCustomEnabled(true);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = findViewById(R.id.recyclerView);

        mName = findViewById(R.id.rName);
        mLatitude = findViewById(R.id.rLatitude);
        mLongitude = findViewById(R.id.rLongitude);
        mbtn = findViewById(R.id.rbtnM);

        database = FirebaseDatabase.getInstance();

        String keyEvent = getIntent().getStringExtra("keyEvent");

        String keyPlace = getIntent().getStringExtra("keyPlace");


        DatabaseReference placeRef = database.getReference("Place").child(keyPlace); //Datail Evnet
        placeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("DAWDADWADWADW", dataSnapshot.toString());
                final ModelEventDetail information = dataSnapshot.getValue(ModelEventDetail.class);

                mbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String label = information.name;
                        String uriBegin = "geo:" + information.latitude + "," + information.longitude;
                        String query = information.latitude + "," + information.longitude + "(" + label + ")";
                        String encodedQuery = Uri.encode(query);
                        String uriString = uriBegin + "?q=" + encodedQuery;
                        Uri uri = Uri.parse(uriString);
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        DatabaseReference eventDetailRef = database.getReference("ModelEventDetail").child(keyPlace).child(keyEvent); //main ModelEventDetail
//        eventDetailRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                Toast.makeText(getBaseContext(), dataSnapshot.toString(), Toast.LENGTH_LONG).show();
//
//                TextView mName = findViewById(R.id.rName);
//                TextView mLatitude = findViewById(R.id.rLatitude);
//                TextView mLongitude = findViewById(R.id.rLongitude);
//
//                String name = mName.getText().toString();
//                String latitude = mLatitude.getText().toString();
//                String longitude = mLongitude.getText().toString();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


//
//        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<ModelMap>().setQuery(reference, ModelMap.class).build();
//        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelMap, ViewHoderMap>(firebaseRecyclerOptions) {
//
//            @Override
//            protected void onBindViewHolder(@NonNull ViewHoderMap holder, int i, @NonNull ModelMap model) {
//                holder.setDetails(getApplicationContext(), model.getName(), model.getLatitude(), model.getLongitude());
//            }
//
//            @NonNull
//            @Override
//            public ViewHoderMap onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.map, parent, false);
//
//                ViewHoderMap viewHoderMap = new ViewHoderMap(itemView);
//                viewHoderMap.setOnClickListener(new ViewHoderMap.ClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Toast.makeText(MapActivity.this, " Map", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onItemLongClick(View view, int position) {
//
//                        Toast.makeText(MapActivity.this, "Long Click", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                return viewHoderMap;
//            }
//        };
//
//        mRecyclerView.setLayoutManager(mLinearLayoutManager);
//        firebaseRecyclerAdapter.startListening();
//        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

//
//
//        Toast.makeText(this, keyPlace, Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(this, keyEvent, Toast.LENGTH_SHORT).show();

    }

    protected void onStart() {
        super.onStart();
//
//        if (firebaseRecyclerAdapter != null) {
//            firebaseRecyclerAdapter.startListening();
//        }
    }
}
