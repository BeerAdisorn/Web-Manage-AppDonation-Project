package com.example.donation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donation.ModelClasses.UserRegister;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CommentActivity extends AppCompatActivity {
    RatingBar ratingBar;
    Button submit;
    EditText mComment;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser currentUser;
    private DatabaseReference userRef;
    private UserRegister user;
    private String keyPlace;
    private DatabaseReference placeRef;
    private DatabaseReference commentRef;
    private FirebaseRecyclerOptions<ModelComment> firebaseRecyclerOptions;
    private FirebaseRecyclerAdapter<ModelComment, ViewHolderComment> firebaseRecyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coment);

        setTitle("Comment");

        keyPlace = getIntent().getStringExtra("keyPlace");

        submit = findViewById(R.id.btnRating);

        mComment = findViewById(R.id.rComment);
        ratingBar = findViewById(R.id.ratingIv);
        recyclerView = findViewById(R.id.recyclerView);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        currentUser = firebaseAuth.getCurrentUser();
        userRef = mDatabase.getReference("users").child(currentUser.getUid());
        placeRef = mDatabase.getReference("Place").child(keyPlace);
        commentRef = placeRef.child("comments");

        firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<ModelComment>().setQuery(commentRef, ModelComment.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelComment, ViewHolderComment>(firebaseRecyclerOptions) {
            @NonNull
            @Override
            public ViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
                return new ViewHolderComment(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolderComment viewHolderComment, int i, @NonNull ModelComment modelRating) {
                viewHolderComment.textView.setText(modelRating.textcomment);
                viewHolderComment.nameuser.setText(modelRating.nameuser);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(UserRegister.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = mComment.getText().toString();

                if (comment.length() < 10 || comment.length() > 150) {
                    mComment.setError("กรุณาเขียนรีวิวอย่างน้อย 10 ตัวอักษร");
                } else {
                    CommentCheck();
                }

            }
        });


    }

    private void CommentCheck() {

        if (user == null) {
            return;
        }

        final String nameUser = user.getFirstname() + " " + user.getLastname();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final String uid = firebaseAuth.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference("/Place/" + keyPlace + "/comments").orderByChild("uid").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {

                    String textComment = mComment.getText().toString();
                    int rating = (int) ratingBar.getRating();

                    ModelComment model = new ModelComment(nameUser, textComment, rating);
                    model.setUid(uid);
                    commentRef.push().setValue(model);
                    Toast.makeText(CommentActivity.this, "โพสต์สำเร็จ", Toast.LENGTH_SHORT).show();

                    // Clear
                    mComment.setText("");

                }else {
                    Toast.makeText(CommentActivity.this, "คุณเคยคอมเม้นไปแล้ว", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    static class ViewHolderComment extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView nameuser;


        ViewHolderComment(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tvText);
            nameuser = itemView.findViewById(R.id.vName);
        }
    }
}
