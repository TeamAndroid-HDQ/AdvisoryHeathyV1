package com.example.firebaseuser.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseuser.Model.DoctorModel;
import com.example.firebaseuser.Model.MedicineModel;
import com.example.firebaseuser.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoctorRecyclerAdapter extends RecyclerView.Adapter<DoctorRecyclerAdapter.DoctorViewHolder> {

    private ArrayList<DoctorModel> list;
    private Activity context;
    private int layoutID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public DoctorRecyclerAdapter(ArrayList<DoctorModel> list) {
        this.list = list;
//        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    static private RecyclerViewClickInterface recyclerViewClickInterface;

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView tvDoctorName, tvSpecialist, rating;
        ImageView imgDoctor;
        Button btnRequest, btnUnRequest;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String doctor_Id;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvNameDoctor);
            tvSpecialist = itemView.findViewById(R.id.tvSpecialist);
            rating = itemView.findViewById(R.id.tvRating);
            imgDoctor = itemView.findViewById(R.id.imgDoctor);
            btnRequest = itemView.findViewById(R.id.btnRequest);
            btnUnRequest = itemView.findViewById(R.id.btnUnRequest);
            btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRequestToDR(doctor_Id);
                }
            });

            btnUnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelRequest(doctor_Id);
                }
            });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
//
//                }
//            });
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
//                    return true;
//                }
//            });
        }

        public void sendRequestToDR(final String doctorId) {
            if (user != null) {
                // Name, email address, and profile photo Url
                String uid = user.getUid();
                Log.d("id", uid);
                myRef = database.getReference("Request");
                myRef.child(doctorId).child(uid).setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        btnRequest.setVisibility(View.GONE);
                        btnUnRequest.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        public void cancelRequest(final String doctor_Id) {
            if (user != null) {
                String uid = user.getUid();
                myRef = database.getReference("Request");
                myRef.child(doctor_Id).child(uid).setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        btnRequest.setVisibility(View.VISIBLE);
                        btnUnRequest.setVisibility(View.GONE);
                    }
                });
            }
        }

    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        cardView = (CardView) inflater.inflate(viewType, parent, false);
        return new DoctorViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorViewHolder holder, final int position) {
        holder.tvDoctorName.setText(list.get(position).getDoctorName());
        holder.tvSpecialist.setText(list.get(position).getSpecialist());
        String UID_doctor = list.get(position).getUID();
        Picasso.get()
                .load(list.get(position).getImgDoctor())
                .into(holder.imgDoctor);
        holder.doctor_Id = UID_doctor;
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_item_doctor;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
