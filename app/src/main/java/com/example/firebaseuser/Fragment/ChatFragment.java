package com.example.firebaseuser.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebaseuser.Adapter.UserRequestAdapter;
import com.example.firebaseuser.Model.Chat;
import com.example.firebaseuser.Model.User;
import com.example.firebaseuser.Model.UserRequest;
import com.example.firebaseuser.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    UserRequestAdapter userRequestAdapter;
    ArrayList<UserRequest> mUsers;

    FirebaseUser fUser;
    DatabaseReference reference;
    ArrayList<String> userLists;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recycleViewUserContacted);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userLists = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String sender = data.child("Sender").getValue().toString();
                    String mess = data.child("Message").getValue().toString();
                    String receiver = data.child("Receiver").getValue().toString();
                    Chat chat = new Chat(sender,receiver,mess);
                    if (chat.getSender().equals(fUser.getUid())){
                        userLists.add(chat.getReceiver());
                    }
                    if (chat.getReceiver().equals(fUser.getUid())){
                        userLists.add(chat.getSender());
                    }
                }
                readListUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
    private void readListUser(){
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("MEMBER");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    for (DataSnapshot shot: data.getChildren()){
                        String role = shot.child("Role").getValue().toString();
                        if (role.equals("User")){
                            String UID = shot.child("UID").getValue().toString();
                            String name = shot.child("User_name").getValue().toString();
                            String imgURL = shot.child("imgURL").getValue().toString();
                            String status = shot.child("Status").getValue().toString();
                            String allergy = shot.child("Allergy").getValue().toString();
                            UserRequest user = new UserRequest(name,imgURL,status,allergy,UID);
                            for (String id : userLists){
                                if (user.getUid().equals(id)){
                                    if (mUsers.size() != 0){
                                        for (UserRequest user1 : mUsers){
                                            if (!user.getUid().equals(user1.getUid())){
                                                mUsers.add(user);
                                            }
                                        }
                                    }else {
                                        mUsers.add(user);
                                    }
                                }
                            }
                        }
                    }
                }
                userRequestAdapter = new UserRequestAdapter(mUsers,getContext());
                recyclerView.setAdapter(userRequestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}