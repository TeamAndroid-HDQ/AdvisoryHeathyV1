package com.example.firebaseuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.firebaseuser.Adapter.ProductRecyclerAdapter;
import com.example.firebaseuser.Model.MedicineModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    Intent intent;
    //Medicine
    ArrayList<MedicineModel> listMedicine;
    ProductRecyclerAdapter productRecyclerAdapter;
    RecyclerView recyclerMedicine;
    LinearLayoutManager linearLayoutManagerMedicine;
    FirebaseDatabase database;
    DatabaseReference dbMedicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        database = FirebaseDatabase.getInstance();

        //TODO: Recyclerview of Medicine
        dbMedicine = database.getReference("Medicine");
        recyclerMedicine = (RecyclerView) findViewById(R.id.recyclerProduct);
        linearLayoutManagerMedicine = new LinearLayoutManager(this);
        linearLayoutManagerMedicine.setOrientation(LinearLayoutManager.VERTICAL);

        listMedicine = new ArrayList<>();
        recyclerMedicine.setLayoutManager(linearLayoutManagerMedicine);

        productRecyclerAdapter = new ProductRecyclerAdapter(listMedicine);
        recyclerMedicine.setAdapter(productRecyclerAdapter);
        dbMedicine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String name = ds.child("Name").getValue().toString();
                    String price = ds.child("Price").getValue().toString();
                    String image = ds.child("ImgUrl").getValue().toString();
                    listMedicine.add(new MedicineModel(name,price,image));
                }
                productRecyclerAdapter = new ProductRecyclerAdapter(listMedicine);
                recyclerMedicine.setAdapter(productRecyclerAdapter);
                productRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        BottomNavigationView navigation = findViewById(R.id.navigationCart);
        navigation.setSelectedItemId(R.id.itemCart);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.itemHome:
                    intent = new Intent(getApplicationContext(),HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.itemCart:
                    finish();
                    return true;
                case R.id.itemMess:
                    intent = new Intent(getApplicationContext(),ContactActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.itemNews:
                    intent = new Intent(getApplicationContext(),NewsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.itemAccount:
                    intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };
}