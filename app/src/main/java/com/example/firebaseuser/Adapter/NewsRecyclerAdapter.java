package com.example.firebaseuser.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseuser.Model.DoctorModel;
import com.example.firebaseuser.Model.NewsModel;
import com.example.firebaseuser.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private ArrayList<NewsModel> list;
    private Activity context;
    private int layoutID;

    public NewsRecyclerAdapter(ArrayList<NewsModel> list) {
        this.list = list;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvDate;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        cardView = (CardView) inflater.inflate(viewType, parent, false);
        return new NewsViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvContent.setText(list.get(position).getContent());
        holder.tvDate.setText(list.get(position).getDate());
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_item_news;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
