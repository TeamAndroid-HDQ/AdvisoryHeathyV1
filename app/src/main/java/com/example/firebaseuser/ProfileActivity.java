package com.example.firebaseuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Intent intent;
    TextView tvOldName;
    ImageView imgAvartar;
    TextView tvSpnStatus, tvSpnSpecialist;
    Spinner spnStatus, spnSpecialist;
    MaterialEditText edtMail, edtPhoneNumber, edtAllergy, edtLisence, edtWorkPlace, edtDisplayName;
    ArrayList listStatus, listSpecialist;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String uidCurrent;
    Toolbar toolbar;
    FirebaseDatabase database;
    //TODO:init
    private void init() {
        tvSpnStatus = findViewById(R.id.tvSpnStatus);
        tvSpnSpecialist = findViewById(R.id.tvSpnSpecialist);
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
        toolbar = findViewById(R.id.toolBar);

        init();
        setSupportActionBar(toolbar);



        //setup for spinner
        listStatus = new ArrayList<>();
        listStatus.add("Helthy");
        listStatus.add("Normal");
        listStatus.add("Sick");
        final ArrayAdapter arrayAdapterStatus = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listStatus);
        spnStatus.setAdapter(arrayAdapterStatus);

        //list wanna fix
        listSpecialist = new ArrayList<>();
        listSpecialist.add("Khoa Sản");
        listSpecialist.add("Khoa Chỉnh Hình");
        listSpecialist.add("Khoa Tim Mạch");
        listSpecialist.add("Khoa Hồi Sức");
        final ArrayAdapter arrayAdapterSpecialist = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listSpecialist);
        spnSpecialist.setAdapter(arrayAdapterSpecialist);
        //TODO: call and update info user
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uidCurrent = user.getUid();

        database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("MEMBER");
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.child(uidCurrent).child("User_name").getValue() != null) {
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
                    if (imgURL.equals("default")){
                        imgAvartar.setImageResource(R.drawable.user);
                    }else{
                        Picasso.get().load(imgURL).into(imgAvartar);
                    }
                    tvOldName.setText(name);
                    if (role == "Doctor") {
                        String license = snapshot.child(uidCurrent).child("License").getValue() + "";
                        String workplace = snapshot.child(uidCurrent).child("WorkPlace").getValue() + "";
                        String specialist = snapshot.child(uidCurrent).child("Specialist").getValue() + "";
                        spnSpecialist.setSelection(arrayAdapterSpecialist.getPosition(specialist));
                        edtLisence.setText(license);
                        edtWorkPlace.setText(workplace);
                        tvSpnSpecialist.setVisibility(View.VISIBLE);
                        spnSpecialist.setVisibility(View.VISIBLE);
                        edtWorkPlace.setVisibility(View.VISIBLE);
                        edtLisence.setVisibility(View.VISIBLE);
                        edtAllergy.setVisibility(View.GONE);
                        tvSpnStatus.setVisibility(View.GONE);
                        spnStatus.setVisibility(View.GONE);
                        finish();
                    }
                    else if (role == "User"){
                        String allergy = snapshot.child(uidCurrent).child("Allergy").getValue() + "";
                        String status = snapshot.child(uidCurrent).child("Status").getValue() + "";
                        spnStatus.setSelection(arrayAdapterStatus.getPosition(status));
                        edtAllergy.setText(allergy);
                        edtAllergy.setVisibility(View.VISIBLE);
                        tvSpnStatus.setVisibility(View.VISIBLE);
                        spnStatus.setVisibility(View.VISIBLE);
                        tvSpnSpecialist.setVisibility(View.GONE);
                        spnSpecialist.setVisibility(View.GONE);
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

        //TODO: navigation bottom
        BottomNavigationView navigation = findViewById(R.id.navigationProfile);
        navigation.setSelectedItemId(R.id.itemAccount);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void updateInfoUser(String oldName, String oldMail, String oldPhone, String oldAllergy, String oldStatus, String oldImgURL, String role,
                                String license, String specialist, String workplace) {

        String newMail = edtMail.getText().toString();
        String newName = edtDisplayName.getText().toString();
        String newPhone = edtPhoneNumber.getText().toString();
        String newImage = imgAvartar.getResources().toString();
        String newStatus = spnStatus.getSelectedItem().toString();
        if (newMail != oldMail || newName != oldName || newPhone != oldPhone || newImage != oldImgURL) {
            //Create toolbar have btnUpdate, btnLogout, (btnHistory)

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.itemHome:
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.itemCart:
                    intent = new Intent(getApplicationContext(), CartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.itemMess:
                    intent = new Intent(getApplicationContext(), ContactActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.itemNews:
                    intent = new Intent(getApplicationContext(), NewsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.itemAccount:
                    return true;
            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btnLogout:
                mAuth.signOut();
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnUpdateProfile:
//                updateInfoUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}