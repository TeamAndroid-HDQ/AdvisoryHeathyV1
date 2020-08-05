package com.example.firebaseuser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseuser.MessingActivity;
import com.example.firebaseuser.Model.Chat;
import com.example.firebaseuser.Model.UserRequest;
import com.example.firebaseuser.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.MessengerViewHolder>{
    private Context context;
    private ArrayList<Chat> chats;
    private String imgUrl;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    FirebaseUser fUser;
    public MessageAdapter(ArrayList<Chat> chats,String imgUrl, Context context) {
        this.context = context;
        this.chats = chats;
        this.imgUrl = imgUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.MessengerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_item_chat_right, parent, false);
            return new MessageAdapter.MessengerViewHolder(view);
        }else {
            View view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_item_chat_left, parent, false);
            return new MessageAdapter.MessengerViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessengerViewHolder holder, int position) {
        final Chat chat = chats.get(position);
        holder.tvContentMess.setText(chat.getMessage());
        if (imgUrl.equals("default")){
            holder.imgUser.setImageResource(R.drawable.user);
        }else{
            Picasso.get().load(imgUrl).into(holder.imgUser);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chats.get(position).equals(fUser.getUid())){
            return MSG_TYPE_LEFT;
        }else return MSG_TYPE_RIGHT;

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    static class MessengerViewHolder extends  RecyclerView.ViewHolder{

        CircleImageView imgUser;
        TextView tvContentMess;
        public MessengerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.profile_imgItem);
            tvContentMess = itemView.findViewById(R.id.tvContentMessIt);
        }
    }
}
