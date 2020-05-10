package com.example.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.donation.Activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MapDemoActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private Button btnsave;
    private Button btnproceed;
    private EditText editTextName;
    private EditText editTextLatitude;
    private EditText editTextLongitude;

    private EditText editTextAdress;
    private EditText editTextPhonenumber;
    private FirebaseAuth firebaseAuth;

    String categoryItemText;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Place");

        categorySpinner = (Spinner) findViewById(R.id.category);
//        category = initSpinner(category, R.array.categorys);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAdress = (EditText) findViewById(R.id.editTextAdress);
        editTextPhonenumber = (EditText) findViewById(R.id.editTextPhonenumber);

        editTextLatitude = (EditText) findViewById(R.id.editTextLatitude);
        editTextLongitude = (EditText) findViewById(R.id.editTextLongitude);
        btnsave = (Button) findViewById(R.id.btnsave);
        btnsave.setOnClickListener(this);

        btnproceed = (Button) findViewById(R.id.btnproceed);
        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapDemoActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            // go to login
            startActivity(new Intent(MapDemoActivity.this, LoginActivity.class));
        }

        //13-02-63
        final String[] category = getResources().getStringArray(R.array.categorys);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_style, category);
        categorySpinner.setAdapter(adapter);

        //setOnItemSelectedListener
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MapDemoActivity.this, "Select: " + category[position], Toast.LENGTH_SHORT).show();

                categoryItemText = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveUserInformation() {

        String name = editTextName.getText().toString().trim();
        String address = editTextAdress.getText().toString().trim();
        String phonenumber = editTextPhonenumber.getText().toString().trim();

        String category = categoryItemText;

//        double latitude = Double.parseDouble(editTextLatitude.getText().toString().trim());
//        double longitude = Double.parseDouble(editTextLongitude.getText().toString().trim());
        String latitude = editTextLatitude.getText().toString().trim();
        String longitude = editTextLongitude.getText().toString().trim();

//        ModelEventDetail userInformation = new ModelEventDetail(name, address, phonenumber, latitude, longitude, category);
//        mDatabase.child(firebaseAuth.getUid()).setValue(userInformation);
//        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view == btnproceed) {
            finish();
        }
        if (view == btnsave) {
            saveUserInformation();
            editTextName.getText().clear();

            editTextAdress.getText().clear();
            editTextPhonenumber.getText().clear();

            editTextLatitude.getText().clear();
            editTextLongitude.getText().clear();
        }
    }

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
