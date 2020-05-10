package com.example.donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class NewsActivity extends AppCompatActivity {

    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;

    private FirebaseDatabase database;
    private FirebaseRecyclerOptions<ModelNews> firebaseRecyclerOptions;
    private FirebaseRecyclerAdapter<ModelNews, ViewHoderNews> firebaseRecyclerAdapter;

    Query mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("News");

        mRecyclerView = findViewById(R.id.recyclerViewNews2);

        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("News");


        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<ModelNews>().setQuery(reference, ModelNews.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelNews, ViewHoderNews>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHoderNews hoder, int position, @NonNull ModelNews model) {

                hoder.setDetails(getApplicationContext(), model.getTopic(), model.getDetail(), model.getDate(), model.getUploader()); //model.getImage() model.getUploader()
            }

            @NonNull
            @Override
            public ViewHoderNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);

                ViewHoderNews viewHoderNews = new ViewHoderNews(itemView);
                viewHoderNews.setOnClickListener(new ViewHoderNews.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(NewsActivity.this, " News", Toast.LENGTH_SHORT).show();

//                        TextView mDetail = view.findViewById(R.id.rDatail);
//                        TextView mDate = view.findViewById(R.id.rDate);
//                        String detail = mDetail.getText().toString();
//                        String datetime = mDate.getText().toString();
//
//                        DataSnapshot snapshot = getSnapshots().getSnapshot(position);
////
//                        Intent intent = new Intent(view.getContext(), NewsActivity.class);//MapActivity.class
//
//                        intent.putExtra("keyEvent", snapshot.getKey());
//
//                        intent.putExtra("detail", detail);
//                        intent.putExtra("dt", "เวลา: "+datetime);
//                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        Toast.makeText(NewsActivity.this, "Long Click", Toast.LENGTH_SHORT).show();
                    }
                });

                return viewHoderNews;
            }
        };

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

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
}
