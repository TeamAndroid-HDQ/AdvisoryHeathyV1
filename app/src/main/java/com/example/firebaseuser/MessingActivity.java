package com.example.firebaseuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.firebaseuser.Adapter.MessageAdapter;
import com.example.firebaseuser.Model.Chat;
import com.example.firebaseuser.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessingActivity extends AppCompatActivity {

    CircleImageView profile_img;
    TextView tvUsername;
    FirebaseUser fUser;
    DatabaseReference databaseReference;
    ImageButton btnSend;
    EditText etSend;

    MessageAdapter messageAdapter;
    ArrayList<Chat> chats;
    RecyclerView recyclerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messing);

        Toolbar toolbar = findViewById(R.id.toolBarMessenger);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerViewMess);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        profile_img = findViewById(R.id.profile_imgItem);
        tvUsername = findViewById(R.id.userNameMess);
        etSend = findViewById(R.id.etSend);
        btnSend = findViewById(R.id.btnSendMess);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        final String userId = intent.getStringExtra("userID");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etSend.getText().toString();
                if (!msg.equals("")) {
                    sendMess(fUser.getUid(), userId, msg);
                } else
                    Toast.makeText(MessingActivity.this, "Error don't send plz check again network", Toast.LENGTH_SHORT).show();
                etSend.setText("");
            }
        });
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("MEMBER").child("Customer");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String key = data.getKey();
                    if (key.equals(userId) && userId != null) {
                        User user = data.getValue(User.class);
                        tvUsername.setText(user.getUser_name());
                        if (user.getImgURL().equals("default")) {
                            profile_img.setImageResource(R.drawable.user);
                        } else {
                            Picasso.get().load(user.getImgURL()).into(profile_img);
                        }
                        readMessenger(fUser.getUid() + "", key, user.getImgURL() + "");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMess(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chat");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Sender", sender);
        hashMap.put("Receiver", receiver);
        hashMap.put("Message", message);
        reference.push().setValue(hashMap);
    }

    private void readMessenger(final String myUID, final String userID, final String imgURL) {
        chats = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String receiver = data.child("Receiver").getValue().toString();
                    String sender = data.child("Sender").getValue().toString();
                    String mess = data.child("Message").getValue().toString();
                    Chat chat = new Chat(sender,receiver,mess);
                    if (receiver.equals(myUID) && sender.equals(userID) || receiver.equals(userID) && sender.equals(myUID)) {
                        chats.add(chat);
                    }
                    messageAdapter = new MessageAdapter(chats, imgURL, MessingActivity.this);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}