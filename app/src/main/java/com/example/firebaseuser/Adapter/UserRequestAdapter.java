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
import com.example.firebaseuser.Model.UserRequest;
import com.example.firebaseuser.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.UserRequestViewHolder> {
    private Context context;
    private ArrayList<UserRequest> mUsers;

    public UserRequestAdapter(ArrayList<UserRequest> mUsers, Context context) {
        this.context = context;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public UserRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView;
        LayoutInflater inflater = LayoutInflater.from(context);
        cardView = (CardView) inflater.inflate(viewType, parent, false);
        return new UserRequestViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRequestViewHolder holder, int position) {
        final UserRequest user = mUsers.get(position);
        holder.tvNameUserRequest.setText(user.getName());
        holder.tvStatusUserRequest.setText(user.getStatus());
        holder.tvAllergy.setText(user.getAllergy());
        if (user.getImgUrl().equals("default")){
            holder.imgUser.setImageResource(R.drawable.user);
        }else{
            Picasso.get().load(user.getImgUrl()).into(holder.imgUser);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessingActivity.class);
                intent.putExtra("userID",user.getUid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_item_user_request;
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    static class UserRequestViewHolder extends  RecyclerView.ViewHolder{

        CircleImageView imgUser;
        TextView tvStatusUserRequest, tvNameUserRequest,tvAllergy;
        public UserRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.imgUser);
            tvNameUserRequest = itemView.findViewById(R.id.tvNameUserRequest);
            tvStatusUserRequest = itemView.findViewById(R.id.tvStatusUserRequest);
            tvAllergy = itemView.findViewById(R.id.tvAllergy);
        }
    }
}
