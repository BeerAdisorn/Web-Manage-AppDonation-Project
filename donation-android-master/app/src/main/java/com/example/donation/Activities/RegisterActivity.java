package com.example.donation.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.donation.HomeActivity;
import com.example.donation.MainMapsActivity;
import com.example.donation.ModelClasses.UserRegister;
import com.example.donation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskedittext.MaskEditText;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference databaseUserRegister;
    String typeUserItemText;
    private EditText edEmail;
    private EditText edFirsname;
    private EditText edLastname;
    private EditText edPassword;
    private EditText edAddress;
    private EditText edPhonenumber;
    private Button btregister;
    private Spinner typeUserTv;

    private MaskEditText edBirthday, edIdCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUserRegister = FirebaseDatabase.getInstance().getReference("users");
        setTitle("Register");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();

        edEmail = (EditText) findViewById(R.id.email);
        edFirsname = (EditText) findViewById(R.id.firsname);
        edLastname = (EditText) findViewById(R.id.lastname);
        edPassword = (EditText) findViewById(R.id.password);
        edAddress = (EditText) findViewById(R.id.address);
        edPhonenumber = (EditText) findViewById(R.id.phonenumber);
        btregister = (Button) findViewById(R.id.btregister);

        typeUserTv = (Spinner) findViewById(R.id.usergrope);

        edBirthday = findViewById(R.id.Birthday);
        edIdCard = findViewById(R.id.IdCard);

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edEmail.getText().toString();
                String pass = edPassword.getText().toString();
                String firsname = edFirsname.getText().toString();
                String lastname = edLastname.getText().toString();
                String phone = edPhonenumber.getText().toString();
                String address = edAddress.getText().toString();
                String birthday = edBirthday.getText().toString();
                String idcard = edIdCard.getText().toString();

                if (!email.matches("[a-zA-Z0-9._-]+[@]+[a-zA-Z]+[.]+[a-zA-Z.]+")) {
                    edEmail.setError("กรุณากรอกอีเมล");
                } else if (pass.length() < 8 || pass.length() > 16) {
                    edPassword.setError("กรุณากรอกรหัสผ่าน 8-16 ตัวอักษร");
                } else if (firsname.matches("")) {
                    edFirsname.setError("กรุณากรอกชื่อผู้ใช้");
                } else if (lastname.matches("")) {
                    edLastname.setError("กรุณากรอกนามสกุล");
//                } else if (pass.matches("")) {
//                    edPassword.setError("กรุณากรอกรหัสผ่าน");
                } else if (idcard.length() < 17 || phone.length() > 17) {
                    edIdCard.setError("กรุณากรอกเลขประจำตัวประชาชนให้ครบ 13 หลัก");
                } else if (birthday.length() < 10 || phone.length() > 10) {
                    edBirthday.setError("กรุณากรอกวัดเกิด");
                } else if (address.matches("")) {
                    edAddress.setError("กรุณากรอกที่อยู่ ");
                } else if (phone.length() < 9 || phone.length() > 10) {
                    edPhonenumber.setError("กรุณากรอกเบอร์โทรศัพท์");
                } else {
//                     checkUserForm();
                    userRegister();
                }

            }

        });

        final String[] usergrope = getResources().getStringArray(R.array.userGrope);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_reg, usergrope);
        typeUserTv.setAdapter(adapter);

        //setOnItemSelectedListener
        typeUserTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                typeUserItemText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        progressDialog.setMessage("รอสักครู่...");
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void userRegister() {

        if (TextUtils.isEmpty(edEmail.getText()) || TextUtils.isEmpty(edPassword.getText())
                || (TextUtils.isEmpty(edFirsname.getText()) || TextUtils.isEmpty(edLastname.getText())
                || (TextUtils.isEmpty(edAddress.getText()) || TextUtils.isEmpty(edPhonenumber.getText())))) {
            Toast.makeText(RegisterActivity.this, "Please All fileds are required", Toast.LENGTH_SHORT).show();
        } else {
            showDialog();

            auth.createUserWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "onComplete: " + task.getException());
                            if (task.isSuccessful()) {
                                hideDialog();

//                                String type = "";
                                String addemail = edEmail.getText().toString().trim();
                                String addpassword = edPassword.getText().toString().trim();
                                String addfirsname = edFirsname.getText().toString().trim();
                                String addlastname = edLastname.getText().toString().trim();
                                String addphone = edPhonenumber.getText().toString().trim();
                                String addaddress = edAddress.getText().toString().trim();
                                String birthday = edBirthday.getText().toString().trim();
                                String Identification = edIdCard.getText().toString().trim();

                                String type = typeUserItemText;

                                String id = auth.getCurrentUser().getUid();//databaseUserRegister.push().getKey();
                                UserRegister user = new UserRegister(id, addemail, addpassword, addfirsname, addlastname, addphone, addaddress, Identification, birthday,type);
                                databaseUserRegister.child(id).setValue(user);

                                databaseUserRegister.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String type = dataSnapshot.child("type").getValue().toString().trim();
                                        if (type.equals("Owner")) {
                                            startActivity(new Intent(RegisterActivity.this, MainMapsActivity.class));
                                            finish();
                                        } else if (type.equals("User")) {
                                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                hideDialog();
                                Toast.makeText(RegisterActivity.this, "อีเมลนี้มีผู้ใช้งานแล้ว!", Toast.LENGTH_SHORT).show();//toast text old : Register failed!
                            }

                        }
                    });
        }
    }
}
