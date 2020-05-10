package com.example.donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventActivity extends AppCompatActivity {

    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;

    private FirebaseDatabase database;
    private FirebaseRecyclerOptions<ModelEvent> firebaseRecyclerOptions;
    private FirebaseRecyclerAdapter<ModelEvent, ViewHoderEvent> firebaseRecyclerAdapter;

    private String keyPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Event");

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = findViewById(R.id.recyclerView);

        database = FirebaseDatabase.getInstance();

        keyPlace = getIntent().getStringExtra("keyPlace");


        DatabaseReference reference = database.getReference("/Event/" + keyPlace);

        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<ModelEvent>().setQuery(reference, ModelEvent.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelEvent, ViewHoderEvent>(firebaseRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull ViewHoderEvent hoder, int i, @NonNull ModelEvent model) {

                hoder.setDetails(getApplicationContext(), model.getNameevent(), model.getDetail(), model.getDt()); //model.getImage()
            }

            @NonNull
            @Override
            public ViewHoderEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);

                ViewHoderEvent viewHoderEvent = new ViewHoderEvent(itemView);
                viewHoderEvent.setOnClickListener(new ViewHoderEvent.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        Toast.makeText(EventActivity.this, " Event", Toast.LENGTH_SHORT).show();

                        TextView mDetail = view.findViewById(R.id.rDatail);
                        TextView mDate = view.findViewById(R.id.rDate);
                        String detail = mDetail.getText().toString();
                        String datetime = mDate.getText().toString();

                        DataSnapshot snapshot = getSnapshots().getSnapshot(position);

                        Intent intent = new Intent(view.getContext(), EventDetailActivity.class);//MapActivity.class

                        intent.putExtra("keyPlace", keyPlace);
                        intent.putExtra("keyEvent", snapshot.getKey());

                        intent.putExtra("detail", detail);//"\n"
                        intent.putExtra("dt", datetime);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        Toast.makeText(EventActivity.this, "Long Click", Toast.LENGTH_SHORT).show();
                    }
                });

                return viewHoderEvent;
            }
        };

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

        Toast.makeText(this, keyPlace, Toast.LENGTH_SHORT).show();

    }

    protected void onStart() {
        super.onStart();

        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_comment:
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra("keyPlace", keyPlace);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
