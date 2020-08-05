package com.example.firebaseuser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoctorRecyclerAdapter extends RecyclerView.Adapter<DoctorRecyclerAdapter.DoctorViewHolder> {

    private ArrayList<DoctorModel> list;
    Activity context;
    private int layoutID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public DoctorRecyclerAdapter(ArrayList<DoctorModel> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    static private RecyclerViewClickInterface recyclerViewClickInterface;

    class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView tvDoctorName, tvSpecialist, rating;
        ImageView imgDoctor;
        Button btnRequest, btnUnRequest;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String doctor_Id;
        String doctor_Img;
        String doctor_Name;
        String doctor_Special;
        String doctor_WorkPlace;
        String doctor_Email;
        String doctor_PhoneNumber;
        String doctor_Role;
        //private Activity context;


        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvNameDoctor);
            tvSpecialist = itemView.findViewById(R.id.tvSpecialist);
            rating = itemView.findViewById(R.id.tvRating);
            imgDoctor = itemView.findViewById(R.id.imgDoctor);
            btnRequest = itemView.findViewById(R.id.btnRequest);
            btnUnRequest = itemView.findViewById(R.id.btnUnRequest);
            String uid = user.getUid();
            if (doctor_Id != null) {
                //bug in this
                DatabaseReference myRequest = database.getReference("Request").child(doctor_Id).child(uid);
                myRequest.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String requested = snapshot.child("Requested").getValue().toString();
                        if (requested == "1") {
                            btnRequest.setVisibility(View.GONE);
                            btnUnRequest.setVisibility(View.VISIBLE);
                        } else {
                            btnRequest.setVisibility(View.VISIBLE);
                            btnUnRequest.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
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
                final String uid = user.getUid();
                myRef = database.getReference("MEMBER").child("Customer").child(uid);
                final DatabaseReference myRequest = database.getReference("Request").child(doctorId).child(uid);
                final DatabaseReference myListRequest = database.getReference("DoctorRequested").child(uid).child(doctorId);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("User_name").getValue().toString();
                        String imgUrl = snapshot.child("imgURL").getValue().toString();
                        String phone = snapshot.child("Phone_number").getValue().toString();
                        String status = snapshot.child("Status").getValue().toString();
                        String allergy = snapshot.child("Allergy").getValue().toString();

                        //lay thong tin cua bac sy duoc request dua vao bang Doctor requested
                        myListRequest.child("DoctorName").setValue(doctor_Name);
                        myListRequest.child("DoctorImg").setValue(doctor_Img);
                        myListRequest.child("DoctorSpecialist").setValue(doctor_Special);
                        myListRequest.child("DoctorEmail").setValue(doctor_Email);
                        myListRequest.child("DoctorPhoneNumber").setValue(doctor_PhoneNumber);
                        myListRequest.child("DoctorWorkPlace").setValue(doctor_WorkPlace);
                        myListRequest.child("Role").setValue(doctor_Role);
                        myListRequest.child("Requested").setValue("1");
                        //lay thong tin cua minh dua vao bang Request
                        myRequest.child("Name").setValue(name);
                        myRequest.child("Status").setValue(status);
                        myRequest.child("Img").setValue(imgUrl);
                        myRequest.child("Allergy").setValue(allergy);
                        myRequest.child("Role").setValue(doctor_Role);
                        myRequest.child("Requested").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btnRequest.setVisibility(View.GONE);
                                btnUnRequest.setVisibility(View.VISIBLE);
                                Toast.makeText(context, "REQUEST SUCCESS", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }

        public void cancelRequest(final String doctor_Id) {
            if (user != null) {
                String uid = user.getUid();
                myRef = database.getReference("Request").child(doctor_Id).child(uid);
                DatabaseReference myListRequest = database.getReference("DoctorRequested").child(uid).child(doctor_Id);
                myListRequest.child("Requested").setValue("0");
                myRef.child("Requested").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
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
        String name_doctor = list.get(position).getDoctorName();
        String img_doctor = list.get(position).getImgDoctor();
        String specialist_doctor = list.get(position).getSpecialist();
        String workPlace_doctor = list.get(position).getWork_place();
        String email_doctor = list.get(position).getWork_place();
        String phone_doctor = list.get(position).getPhone_number();
        String role_doctor = list.get(position).getRole();
        Picasso.get()
                .load(list.get(position).getImgDoctor())
                .into(holder.imgDoctor);
        holder.doctor_Id = UID_doctor;
        holder.doctor_Img = img_doctor;
        holder.doctor_Name = name_doctor;
        holder.doctor_Special = specialist_doctor;
        holder.doctor_WorkPlace = workPlace_doctor;
        holder.doctor_Email = email_doctor;
        holder.doctor_PhoneNumber = phone_doctor;
        holder.doctor_Role = role_doctor;
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
