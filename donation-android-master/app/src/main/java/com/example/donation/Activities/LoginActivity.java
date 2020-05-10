package com.example.donation.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.donation.HomeActivity;
import com.example.donation.MainMapsActivity;
import com.example.donation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final DatabaseReference dbrefUser = FirebaseDatabase.getInstance().getReference("users");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            FirebaseUser user = auth.getCurrentUser();
            String uid = user.getUid();
            dbrefUser.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String type = dataSnapshot.child("type").getValue().toString().trim();
                    if (type.equals("Owner")) {
                        startActivity(new Intent(LoginActivity.this, MainMapsActivity.class));
                        finish();
                    } else if (type.equals("User")) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            finish();
        }

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_LONG).show();
                    return;
                }
                closeKeyboard();

                progressBar.setVisibility(View.VISIBLE);
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    String uid = user.getUid();

                                    dbrefUser.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String type = dataSnapshot.child("type").getValue().toString().trim();
                                            if (type.equals("Owner")) {
                                                startActivity(new Intent(LoginActivity.this, MainMapsActivity.class));
                                                finish();
                                            } else if (type.equals("User")) {
                                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                } else {

                                    //showProgress(false);
                                    Toast.makeText(LoginActivity.this, "Login Failed ! Wrong Credentials", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }

        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void resetPassword() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_reset_password, null);
        dialogBuilder.setView(dialogView);
        final EditText editEmail = dialogView.findViewById(R.id.email);
        final Button btnReset = dialogView.findViewById(R.id.btn_reset_password);
        final Button btnBack = dialogView.findViewById(R.id.btn_back);
        final ProgressBar progressBar1 = dialogView.findViewById(R.id.progressBar);
        //dialogBuilder.setTitle("Send Photos");
        final AlertDialog dialog = dialogBuilder.create();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar1.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                                }
                                progressBar1.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        });
            }
        });
        dialog.show();
    }
}
