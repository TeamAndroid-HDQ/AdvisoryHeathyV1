package com.example.firebaseuser;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText edtUsername, edtEmail, edtPass, edtConfirmPW;
    MaterialEditText edtAllergy, edtLisence, edtWork_place;
    Spinner spnStatus, spnSpecialist;
    Button btnRegister;
    ImageView imageRegister;
    RadioButton radioDr, radioUser;
    RadioGroup radioGroup;
    ArrayList<String> listStatus, listSpecialist;
    String status, specialist;
    String email, name, imgUrl, UID, role;
    public final String DEFAULT = "default";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    static DatabaseReference mData;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private void init() {
        imageRegister = findViewById(R.id.imageRegister);
        edtUsername = findViewById(R.id.userName);
        edtEmail = findViewById(R.id.email);
        edtPass = findViewById(R.id.password);
        edtConfirmPW = findViewById(R.id.confirmPW);
        btnRegister = findViewById(R.id.btnRegister);
        radioDr = findViewById(R.id.radioDR);
        radioUser = findViewById(R.id.radioUser);
        radioGroup = findViewById(R.id.roleGroup);
        edtAllergy = findViewById(R.id.allergy);
        edtLisence = findViewById(R.id.lisence);
        edtWork_place = findViewById(R.id.work_place);
        spnSpecialist = findViewById(R.id.spnSpecialist);
        spnStatus = findViewById(R.id.spnStatus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        final String userName = edtUsername.getText().toString();
        final String email = edtEmail.getText().toString();
        final String password = edtPass.getText().toString();
        final String confirm = edtConfirmPW.getText().toString();

        imageRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFormLibrary();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioDr.isChecked()) {
                    edtLisence.setVisibility(View.VISIBLE);
                    edtWork_place.setVisibility(View.VISIBLE);
                    spnSpecialist.setVisibility(View.VISIBLE);
                    edtAllergy.setVisibility(View.GONE);
                    spnStatus.setVisibility(View.GONE);
                } else if (radioUser.isChecked()) {
                    edtLisence.setVisibility(View.GONE);
                    edtWork_place.setVisibility(View.GONE);
                    spnSpecialist.setVisibility(View.GONE);
                    edtAllergy.setVisibility(View.VISIBLE);
                    spnStatus.setVisibility(View.VISIBLE);
                }
            }
        });

        listStatus = new ArrayList<>();
        listStatus.add("Helthy");
        listStatus.add("Normal");
        listStatus.add("Sick");

        ArrayAdapter arrayAdapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, listStatus);
        spnStatus.setAdapter(arrayAdapter);
        status = spnStatus.getSelectedItem().toString();

        //list wanna fix
        listSpecialist = new ArrayList<>();
        listSpecialist.add("Khoa Sản");
        listSpecialist.add("Khoa Chỉnh Hình");
        listSpecialist.add("Khoa Tim Mạch");
        listSpecialist.add("Khoa Hồi Sức");
//        listSpecialist.add("Khoa Tim Mạch");
//        listSpecialist.add("Khoa Tim Mạch");
//        listSpecialist.add("Khoa Tim Mạch");
//        listSpecialist.add("Khoa Tim Mạch");

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpecialist);
        spnSpecialist.setAdapter(arrayAdapter1);
        specialist = spnSpecialist.getSelectedItem().toString();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                || email.isEmpty() || password.isEmpty() || confirm.isEmpty()
                if (edtUsername.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()
                        || edtPass.getText().toString().isEmpty() || edtConfirmPW.getText().toString().isEmpty() || (!radioDr.isChecked() && !radioUser.isChecked())) {
                    if (radioDr.isChecked()) {
                        if (edtLisence.getText().toString().isEmpty() || edtWork_place.getText().toString().isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (edtPass.getText().toString().length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    String rl = checkRole(role);
                    if (edtAllergy.getText().toString().isEmpty()) {
                        String allergy = DEFAULT;
                        registerNewUser(edtUsername.getText().toString(), edtEmail.getText().toString(), edtPass.getText().toString(), rl,
                                edtWork_place.getText().toString(), edtLisence.getText().toString(), specialist, status, allergy);
                    }
                }
            }
        });
    }

    private void pickImageFormLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageRegister.setImageURI(data.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFormLibrary();
                } else Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
        }
    }

    private String checkRole(String role) {
        if (radioUser.isChecked()) {
            role = radioUser.getText().toString();
        }
        if (radioDr.isChecked()) {
            role = radioDr.getText().toString();
        }
        return role;
    }

    private void registerNewUser(final String userNameT, final String email, String password, final String role, final String work_place,
                                 final String lisence, final String specialist, final String status, final String allergy) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Email", email);
                    hashMap.put("User_name", userNameT);
                    hashMap.put("imgURL", DEFAULT);
                    hashMap.put("Role", role);
                    hashMap.put("Phone_number", DEFAULT);
                    hashMap.put("UID", userId);
                    if (role == radioUser.getText().toString()) {
                        hashMap.put("Status", status);
                        hashMap.put("Allergy", allergy);
                        mData = FirebaseDatabase.getInstance().getReference("MEMBER").child("Customer");
                        mData.child(userId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                    if (role == radioDr.getText().toString()) {
                        hashMap.put("WorkPlace", work_place);
                        hashMap.put("License", lisence);
                        hashMap.put("Specialist", specialist);
                        mData = FirebaseDatabase.getInstance().getReference("MEMBER").child("Doctor");
                        mData.child(userId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Don't register plz check info insert", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerByFB() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (!bundle.isEmpty()) {
            edtEmail.setText(bundle.getString(LoginActivity.KEY_EMAIL));
            edtUsername.setText(bundle.getString(LoginActivity.KEY_NAME));
            String ID = LoginActivity.KEY_ID;
            String photo_url = "https://graph.facebook.com/" + ID + "/picture?type=normal";
            Picasso.get().load(photo_url).into(imageRegister);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "RegisterActivity Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "RegisterActivity Resume", Toast.LENGTH_SHORT).show();
    }
}