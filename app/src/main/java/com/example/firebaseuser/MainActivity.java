package com.example.firebaseuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.firebaseuser.Model.MedicineModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ArrayList<MedicineModel> arrayList;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

//        Fragment fragment = new Home_fragment();
//        loadFragment(fragment);
//        BottomNavigationView navigation = findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment fragment;
//            switch (item.getItemId()) {
//                case R.id.itemHome:
////                    fragment = new Home_fragment();
////                    loadFragment(fragment);
//                    return true;
//                case R.id.itemCart:
//                    fragment = new Cart_fragment();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.itemMess:
//                    fragment = new Mess_fragment();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.itemNews:
//                    fragment = new News_fragment();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.itemAccount:
//                    fragment = new Account_fragment();
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragment_container, fragment);
//
//                    transaction.commit();
//                    return true;
//            }
//            return false;
//        }
//    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TODO: sau này nhớ chỉnh lại đoạn này
        FirebaseAuth.getInstance().signOut();
    }
}