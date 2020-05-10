package com.example.donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    TextView mTopic,mDetail, mDate;
    ImageView mUploader;

    private FirebaseDatabase database;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mTopic = findViewById(R.id.nTopic);
        mDetail = findViewById(R.id.nDetail);
        mDate = findViewById(R.id.nDate);
        mUploader = findViewById(R.id.nUploader);

//        String topic = getIntent().getStringExtra("topic");
//        String detail = getIntent().getStringExtra("detail");
//        String datetime = getIntent().getStringExtra("date");

//        mTopic.setText(topic);
//        mDetail.setText(detail);
//        mDate.setText(datetime);

        database = FirebaseDatabase.getInstance();
        String keyNews = getIntent().getStringExtra("keyNews");

        final DatabaseReference newsRef = database.getReference("News").child(keyNews);
        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelNews value = dataSnapshot.getValue(ModelNews.class);
                mTopic.setText(value.topic);
                mDetail.setText(value.detail);
                mDate.setText(value.date);
//                Picasso.get().load(value.uploader).into(mUploader);

                if (!value.uploader.isEmpty()) {
                    Picasso.get().load(value.uploader).into(mUploader);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
