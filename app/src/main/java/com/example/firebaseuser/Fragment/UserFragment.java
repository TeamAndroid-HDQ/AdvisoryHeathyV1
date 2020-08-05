package com.example.firebaseuser.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.example.firebaseuser.Adapter.UserRequestAdapter;
import com.example.firebaseuser.Model.User;
import com.example.firebaseuser.Model.UserRequest;
import com.example.firebaseuser.R;
import com.example.firebaseuser.database.DatabaseSQLite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    UserRequestAdapter userRequestAdapter;
    ArrayList<UserRequest> mUsers;

//    void initPreferences() {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//        editor = sharedPreferences.edit();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewRequest);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String my_UID = firebaseUser.getUid().toString();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Request").child(my_UID);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String uid = data.getKey() + "";
                    String name = data.child("Name").getValue().toString();
                    String allergy = data.child("Allergy").getValue().toString();
                    String status = data.child("Status").getValue().toString();
                    String img = data.child("Img").getValue().toString();
                    String requested = data.child("Requested").getValue().toString();
                    if (requested.equals("1")){
                        mUsers.add(new UserRequest(name,img,status,allergy,uid));
                    }
                }
                recyclerView.setAdapter(userRequestAdapter);
                userRequestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userRequestAdapter = new UserRequestAdapter(mUsers,getContext());
        recyclerView.setAdapter(userRequestAdapter);
        return view;
    }


}