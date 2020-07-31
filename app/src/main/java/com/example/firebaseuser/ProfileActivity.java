package com.example.firebaseuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Intent intent;
    TextView tvOldName;
    ImageView imgAvartar;
    LinearLayout linearSpecialist, linearStatus;
    Spinner spnStatus, spnSpecialist;
    MaterialEditText edtMail, edtPhoneNumber, edtAllergy, edtLisence, edtWorkPlace, edtDisplayName;
    ArrayList listStatus, listSpecialist;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String uidCurrent;

    //TODO:init
    private void init() {
        linearSpecialist = findViewById(R.id.linearSpecialist);
        linearStatus = findViewById(R.id.linearSpecialist);
        spnSpecialist = findViewById(R.id.spnSpecialistProfile);
        spnStatus = findViewById(R.id.spnStatusProfile);
        edtWorkPlace = findViewById(R.id.edtWorkPlaceProfile);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumberProfile);
        edtMail = findViewById(R.id.edtEmailProfile);
        edtDisplayName = findViewById(R.id.edtDisplayNameProfile);
        edtAllergy = findViewById(R.id.edtAllergyProfile);
        edtLisence = findViewById(R.id.edtLisenceProfile);
        tvOldName = findViewById(R.id.tvOldName);
        imgAvartar = findViewById(R.id.imgAvartar);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        //setup for spinner
        listStatus = new ArrayList<>();
        listStatus.add("GOOD1");
        listStatus.add("NORMAL");
        listStatus.add("BAD");
        final ArrayAdapter arrayAdapterStatus = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listStatus);
        spnStatus.setAdapter(arrayAdapterStatus);

        //list wanna fix
        listSpecialist = new ArrayList<>();
        listSpecialist.add("GOOD1");
        listSpecialist.add("NORMAL");
        listSpecialist.add("BAD");
        final ArrayAdapter arrayAdapterSpecialist = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listSpecialist);
        spnSpecialist.setAdapter(arrayAdapterSpecialist);

        //TODO: call and update info user
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uidCurrent = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("MEMBER");
        //TODO:///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.child(uidCurrent).child("User_name").getValue() != null){
                    String name = snapshot.child(uidCurrent).child("User_name").getValue().toString();
                    String phone_number = snapshot.child(uidCurrent).child("Phone_number").getValue() + "";
                    String mail = snapshot.child(uidCurrent).child("Email").getValue() + "";
                    String UID = snapshot.child(uidCurrent).child("UID").getValue() + "";
                    String role = snapshot.child(uidCurrent).child("Role").getValue() + "";
                    String imgURL = snapshot.child(uidCurrent).child("ImgURL").getValue() + "";
                    edtDisplayName.setText(name);
                    edtMail.setText(mail);
                    edtDisplayName.setText(name);
                    edtPhoneNumber.setText(phone_number);
                    Picasso.get().load(imgURL).into(imgAvartar);
                    tvOldName.setText(name);
                    if (role == "Doctor") {
                        String license = snapshot.child(uidCurrent).child("License").getValue() + "";
                        String workplace = snapshot.child(uidCurrent).child("WorkPlace").getValue() + "";
                        String specialist = snapshot.child(uidCurrent).child("Specialist").getValue() + "";
                        spnSpecialist.setSelection(arrayAdapterSpecialist.getPosition(specialist));
                        edtLisence.setText(license);
                        edtWorkPlace.setText(workplace);
                        edtAllergy.setVisibility(View.GONE);
                        linearStatus.setVisibility(View.GONE);
                        linearSpecialist.setVisibility(View.VISIBLE);
                        edtWorkPlace.setVisibility(View.VISIBLE);
                        edtLisence.setVisibility(View.VISIBLE);
                    } else {
                        String allergy = snapshot.child(uidCurrent).child("Allergy").getValue() + "";
                        String status = snapshot.child(uidCurrent).child("Status").getValue() + "";
                        spnStatus.setSelection(arrayAdapterStatus.getPosition(status));
                        edtAllergy.setText(allergy);
                        edtAllergy.setVisibility(View.VISIBLE);
                        linearStatus.setVisibility(View.VISIBLE);
                        linearSpecialist.setVisibility(View.GONE);
                        edtWorkPlace.setVisibility(View.GONE);
                        edtLisence.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //TODO://////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //TODO: navigation bottom
        BottomNavigationView navigation = findViewById(R.id.navigationProfile);
        navigation.setSelectedItemId(R.id.itemAccount);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.itemHome:
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.itemCart:
                    intent = new Intent(getApplicationContext(), CartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.itemMess:
                    intent = new Intent(getApplicationContext(), ContactActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.itemNews:
                    intent = new Intent(getApplicationContext(), NewsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.itemAccount:
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }
}