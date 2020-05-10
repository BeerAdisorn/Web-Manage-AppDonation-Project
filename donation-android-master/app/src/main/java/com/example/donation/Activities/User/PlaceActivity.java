package com.example.donation.Activities.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donation.EventActivity;
import com.example.donation.R;
import com.example.donation.ViewHoder.ModelPlace;
import com.example.donation.ViewHoder.ViewHolderPlace;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlaceActivity extends AppCompatActivity {

    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    Query mDatabaseReference;
    PlaceFirebaseAdapter firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<ModelPlace> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = findViewById(R.id.recyclerView);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Place"); //place

        Intent intent = getIntent();
        if (intent != null) {

            String filter = intent.getStringExtra("filter");

            if (!filter.isEmpty()) {
                mDatabaseReference = mDatabaseReference.orderByChild("category").equalTo(filter);
            }
        }

        showData();

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Place");

        final EditText editText = findViewById(R.id.editTextSearch);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firebaseRecyclerAdapter.getFilter().filter(editText.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showData() {
        options = new FirebaseRecyclerOptions.Builder<ModelPlace>().setQuery(mDatabaseReference, ModelPlace.class).build();
        firebaseRecyclerAdapter = new PlaceFirebaseAdapter(options);

        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    protected void onStart() {
        super.onStart();

        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }


    @Override
    protected void onStop() {
        if (this.firebaseRecyclerAdapter != null) {
            this.firebaseRecyclerAdapter.stopListening();
        }

        super.onStop();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class PlaceFirebaseAdapter extends FirebaseRecyclerAdapter<ModelPlace, ViewHolderPlace> implements Filterable {
        List<ModelPlace> items;
        List<ModelPlace> filteredItems;
        boolean filter;

        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                filter = true;

                List<ModelPlace> filteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(items);
                } else {
                    for (ModelPlace place : items) {
                        if (place.getName().toLowerCase().contains(charSequence.toString().toLowerCase()) && place.getStatus()) {
                            filteredList.add(place);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            //Automatic on UI thread
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredItems.clear();
                filteredItems.addAll((Collection<? extends ModelPlace>) filterResults.values);
                notifyDataSetChanged();
            }
        };

        public PlaceFirebaseAdapter(@NonNull FirebaseRecyclerOptions<ModelPlace> options) {
            super(options);
            this.items = new ArrayList<>();
            this.filteredItems = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            return filteredItems.size();
        }

        @NonNull
        @Override
        public ModelPlace getItem(int position) {
            return filteredItems.get(position);
        }

        @Override
        public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {
            this.items = super.getSnapshots();

            if (!filter) {
                this.filteredItems.clear();
                for (ModelPlace item : items) {
                    if (item.getStatus()) {
                        filteredItems.add(item);
                    }
                }
                notifyDataSetChanged();
            }
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolderPlace holder, int position, @NonNull ModelPlace model) {
            holder.setDetails(getApplicationContext(), model.getName(), model.getAddress(), model.getPhonenumber(), model.getAverageRating(), model.getCountRating());//model.getImage()
        }

        @NonNull
        @Override
        public ViewHolderPlace onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.place, parent, false);

            ViewHolderPlace viewHolder = new ViewHolderPlace(itemView);
            viewHolder.setOnClickListener(new ViewHolderPlace.ClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    DataSnapshot snapshot = getSnapshots().getSnapshot(position);

//                        Toast.makeText(PlaceActivity.this, "Hello", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(view.getContext(), EventActivity.class);
                    intent.putExtra("keyPlace", snapshot.getKey());
                    startActivity(intent);

                }

                @Override
                public void onItemLongClick(View view, int position) {

                    Toast.makeText(PlaceActivity.this, "Long Click", Toast.LENGTH_SHORT).show();

                }
            });

            return viewHolder;
        }

        @Override
        public Filter getFilter() {
            return myFilter;
        }
    }
}
