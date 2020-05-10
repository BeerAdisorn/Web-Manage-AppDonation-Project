package com.example.donation.FragmentActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donation.EventDetailActivity;
import com.example.donation.ModelNews;
import com.example.donation.NewsActivity;
import com.example.donation.NewsDetailActivity;
import com.example.donation.R;
import com.example.donation.ViewHoderNews;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static com.android.volley.VolleyLog.TAG;

public class NewsFragment extends Fragment {

    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;

    private FirebaseDatabase database;
    private FirebaseRecyclerOptions<ModelNews> firebaseRecyclerOptions;
    private FirebaseRecyclerAdapter<ModelNews, ViewHoderNews> firebaseRecyclerAdapter;

    Query mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_news, container, false);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = inflate.findViewById(R.id.recyclerViewNews);

        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("News");


        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<ModelNews>().setQuery(reference, ModelNews.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelNews, ViewHoderNews>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHoderNews hoder, int position, @NonNull ModelNews model) {

                hoder.setDetails(getActivity(), model.getTopic(), model.getDetail(), model.getDate(), model.getUploader());
            }

            @NonNull
            @Override
            public ViewHoderNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);


                ViewHoderNews viewHoderNews = new ViewHoderNews(itemView);
                viewHoderNews.setOnClickListener(new ViewHoderNews.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        Toast.makeText(getActivity(), " News", Toast.LENGTH_SHORT).show();

                        TextView mtopic = view.findViewById(R.id.tName);
                        TextView mDatail = view.findViewById(R.id.tDatail);
                        TextView mDate = view.findViewById(R.id.tDate);

                        String topic = mtopic.getText().toString();
                        String detail = mDatail.getText().toString();
                        String datetime = mDate.getText().toString();

                        DataSnapshot snapshot = getSnapshots().getSnapshot(position);

                        Intent intent = new Intent(view.getContext(), NewsDetailActivity.class);

                        intent.putExtra("keyNews", snapshot.getKey());
                        intent.putExtra("topic", topic);
                        intent.putExtra("detail", detail);
                        intent.putExtra("date",datetime);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        Toast.makeText(getActivity(), "Long Click", Toast.LENGTH_SHORT).show();
                    }
                });

                return viewHoderNews;
            }
        };

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

        return inflate;
    }

    @Override
    public void onDetach() {
        firebaseRecyclerAdapter.stopListening();
        super.onDetach();
    }
}
