package com.example.firebaseuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.firebaseuser.Adapter.NewsRecyclerAdapter;
import com.example.firebaseuser.Model.NewsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    Intent intent;
    ArrayList<NewsModel> listNews;
    NewsRecyclerAdapter newsRecyclerAdapter;
    RecyclerView recyclerNews;
    LinearLayoutManager linearLayoutManagerNews;
    DatabaseReference dbNews;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        database = FirebaseDatabase.getInstance();
        //TODO: Recyclerview of News
        dbNews = database.getReference("News");
        recyclerNews = (RecyclerView) findViewById(R.id.recyclerNews);
        linearLayoutManagerNews = new LinearLayoutManager(this);
        linearLayoutManagerNews.setOrientation(LinearLayoutManager.VERTICAL);

        listNews = new ArrayList<>();
        recyclerNews.setLayoutManager(linearLayoutManagerNews);

        newsRecyclerAdapter = new NewsRecyclerAdapter(listNews);
        recyclerNews.setAdapter(newsRecyclerAdapter);
        dbNews.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String tile = ds.child("Title").getValue().toString();
                    String content = ds.child("Content").getValue().toString();
                    String author = ds.child("Author").getValue().toString();
                    String b_date = ds.child("B_Date").getValue().toString();
                    listNews.add(new NewsModel(tile,content,b_date,author));
                }
                newsRecyclerAdapter = new NewsRecyclerAdapter(listNews);
                recyclerNews.setAdapter(newsRecyclerAdapter);
                newsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        BottomNavigationView navigation = findViewById(R.id.navigationNew);
        navigation.setSelectedItemId(R.id.itemNews);
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
                    intent = new Intent(getApplicationContext(),CartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.itemMess:
                    intent = new Intent(getApplicationContext(),ContactActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                case R.id.itemNews:
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