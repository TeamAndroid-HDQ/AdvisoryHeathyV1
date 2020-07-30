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

import com.example.firebaseuser.Model.MedicineModel;
import com.example.firebaseuser.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyViewHolder> {

    private ArrayList<MedicineModel> list;
    private Activity context;
    private int layoutID;

    public ProductRecyclerAdapter(ArrayList<MedicineModel> list) {
        this.list = list;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;
        ImageView imgView, imgAddCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgView = itemView.findViewById(R.id.imgView);
            imgAddCart = itemView.findViewById(R.id.imgAddCart);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        cardView = (CardView) inflater.inflate(viewType, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getName());
        holder.tvContent.setText(list.get(position).getPrice());
        Picasso.get()
                .load(list.get(position).getImgUrl())
                .into(holder.imgView);
        holder.imgAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_item_view;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
