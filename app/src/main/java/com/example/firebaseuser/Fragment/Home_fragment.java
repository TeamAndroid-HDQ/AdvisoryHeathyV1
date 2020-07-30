//package com.example.firebaseuser.Fragment;
//
//import android.app.Dialog;
//import android.hardware.TriggerEvent;
//import android.hardware.TriggerEventListener;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.firebaseuser.Adapter.DoctorRecyclerAdapter;
//import com.example.firebaseuser.Adapter.NewsRecyclerAdapter;
//import com.example.firebaseuser.Adapter.ProductRecyclerAdapter;
//import com.example.firebaseuser.HomeActivity;
//import com.example.firebaseuser.Model.DoctorModel;
//import com.example.firebaseuser.Model.MedicineModel;
//import com.example.firebaseuser.Model.NewsModel;
//import com.example.firebaseuser.R;
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//import com.rengwuxian.materialedittext.MaterialEditText;
//import com.squareup.picasso.Picasso;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class Home_fragment extends Fragment {
//
//    private final String DEFAULT = "default";
//    public final String ROLE_USER = "Customer";
//    public final String ROLE_DOCTOR = "Doctor";
//    ImageView btnTurnRightLD;
//    Button btnRequest;
//    private boolean check = true;
//    private int posAnimation;
//    DatabaseReference dbNews, dbProduct, dbDoctor;
//    ProductRecyclerAdapter adapter;
////    DoctorRecyclerAdapter adapterDoctors;
//    NewsRecyclerAdapter adapterNews;
//    FirebaseDatabase database;
//    FirebaseAuth mAuth;
//    String UID, User_name, Email, Phone_number, Role, imgURL;
//    String Allergy, Status;
//    String Liense, Specialist, Work_place;
//    private FirebaseFirestore firebaseFirestore;
//    ArrayList<DoctorModel> listDoctor;
//    ArrayList<MedicineModel> arrayList;
//    ArrayList<NewsModel> listNews;
//    DoctorRecyclerAdapter adapterDoctors;
//    RecyclerView recyclerView,recyclerNews,recyclerDoctor;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        View fragmentLayout = null;
//
//        fragmentLayout = inflater.inflate(R.layout.fragment_home, container, false);
////        checkData();
//        recyclerNews = fragmentLayout.findViewById(R.id.recycleViewNews);
//        recyclerDoctor = fragmentLayout.findViewById(R.id.recycleViewDoctors);
//        recyclerView = fragmentLayout.findViewById(R.id.RecyclerView);
//        database = FirebaseDatabase.getInstance();
//        DatabaseReference dbDoctor = database.getReference("Doctor");
//        listDoctor = new ArrayList<>();
//        listNews = new ArrayList<>();
//        arrayList = new ArrayList<>();
//
//
//
//
//        adapterNews = new NewsRecyclerAdapter(listNews);
//        adapter = new ProductRecyclerAdapter(arrayList);
//        adapterDoctors = new DoctorRecyclerAdapter(listDoctor);
//
//        LinearLayoutManager layoutVerticalManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        LinearLayoutManager layoutHorizontalManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        LinearLayoutManager layoutHorizontal1Manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//
//        recyclerView = fragmentLayout.findViewById(R.id.RecyclerView);
//        recyclerView.setLayoutManager(layoutVerticalManager);
//        recyclerView.setAdapter(adapter);
//
//        recyclerDoctor = fragmentLayout.findViewById(R.id.recycleViewDoctors);
//        recyclerDoctor.setLayoutManager(layoutHorizontalManager);
//        recyclerDoctor.setAdapter(adapterDoctors);
//
//        recyclerNews = fragmentLayout.findViewById(R.id.recycleViewNews);
//        recyclerNews.setLayoutManager(layoutHorizontal1Manager);
//        recyclerNews.setAdapter(adapterNews);
//
//        dbDoctor.child("Doctor").addValueEventListener(new ValueEventListener() {
//
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()){
//                    String name = ds.child("User_name").getValue().toString();
//                    String specialist = ds.child("Specialist").getValue().toString();
//                    String imgURL = ds.child("imgURL").getValue().toString();
//                    listDoctor.add(new DoctorModel("",name,"",imgURL,"",specialist,"",""));
//                    Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
//                }
//                adapterDoctors = new DoctorRecyclerAdapter(listDoctor);
//                recyclerDoctor.setAdapter(adapterDoctors);
//                adapterDoctors.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
////        turnRightListDoctor();
////        btnRequest = fragmentLayout.findViewById(R.id.btnRequest);
////        btnRequest.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                swapContentFragment( Cart_fragment.newInstance(), true,R.id.fragment_container);
////            }
////        });
//
//        return fragmentLayout;
//    }
//
//    //TODO:handle animation
//    //Click button tủn left
////    private void turnRightListDoctor() {
////        final Handler handler = new Handler();
////        handler.post(new Runnable() {
////            @Override
////            public void run() {
////
////                if (check) {
////                    posAnimation++;
////                    if (posAnimation >= listDoctor.size()-1){
////
////                        check = false;
////                    }
////                }
////                else {
////                    posAnimation--;
////                    if (posAnimation<=0)check=true;
////                }
////                recyclerDoctor.smoothScrollToPosition(posAnimation);
////                handler.postDelayed(this, 3000);
////            }
////        });
////    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//    //TODO: swap fragment
//    private void swapContentFragment(final Fragment i_newFragment, final boolean i_addToStack, final int container) {
//        final FragmentManager fm = getFragmentManager();
//        final FragmentTransaction transaction = fm.beginTransaction();
//        transaction.replace(container, i_newFragment);
//        if (!i_addToStack) {
//            transaction.addToBackStack(null);
//        }
//        transaction.commit();
//    }
//
//    //TODO: request to doctor
//    private void requestToDoctor(){
//
//    }
//
////    @Override
////    public void onItemClick(int position) {
////
////    }
////
////    @Override
////    public void onLongItemClick(int position) {
////
////    }
//    //TODO: check data user
////    private void checkData(){
////        mAuth = FirebaseAuth.getInstance();
////        FirebaseUser user = mAuth.getCurrentUser();
////        if (user.getUid() != null )
////        {
////            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
////            DatabaseReference databaseReference = firebaseDatabase.getReference(user.getUid());
////            databaseReference.addChildEventListener(new ChildEventListener() {
////                @Override
////                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
////                    String value = snapshot.getValue().toString();
////                    try {
////                        JSONObject jsonObject = new JSONObject(value);
////                        Phone_number = jsonObject.getString("Phone_number");
////                        Allergy = jsonObject.getString("Allergy");
////                        Role = jsonObject.getString("Role");
////                        Status = jsonObject.getString("Status");
////                        Liense = jsonObject.getString("Liense");
////                        Work_place = jsonObject.getString("Work_place");
////                        Specialist = jsonObject.getString("Specialist");
////                        String demo = Phone_number +  Allergy + Role + Status + Liense + Work_place + Specialist;
////                        Toast.makeText(getContext(),demo,Toast.LENGTH_LONG).show();
//////                        if (!Role.isEmpty()){
//////                            dialogUpdate(Role);
//////                        }
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
////
////                @Override
////                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
////
////                }
////
////                @Override
////                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
////
////                }
////
////                @Override
////                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
////
////                }
////
////                @Override
////                public void onCancelled(@NonNull DatabaseError error) {
////
////                }
////            });
////        }
////    }
//    MaterialEditText nameUpdate, emailUpdate,
//            allergy,
//            specialist, license, work_place;
//    Spinner spnStatus;
//    Button btnOK;
//    ImageView btnCancel;
//
////    private void dialogUpdate(String role){
////        final Dialog dialog = new Dialog(getContext());
////        btnOK = dialog.findViewById(R.id.btnOK);
////        if(role == ROLE_USER && (Allergy == DEFAULT || Status == DEFAULT)){
////            dialog.setContentView(R.layout.dialog_confirm_info_customer);
////            nameUpdate = dialog.findViewById(R.id.nameUpdate);
////            emailUpdate = dialog.findViewById(R.id.emailUpdate);
////            allergy = dialog.findViewById(R.id.Allergy);
////            spnStatus = dialog.findViewById(R.id.spnStatus);
////            dialog.show();
////        }
////        else if (role == ROLE_DOCTOR && (Specialist == DEFAULT || Liense == DEFAULT || Work_place == DEFAULT)){
////            dialog.setContentView(R.layout.dialog_confirm_info_doctor);
////            nameUpdate = dialog.findViewById(R.id.nameUpdate);
////            emailUpdate = dialog.findViewById(R.id.emailUpdate);
////            specialist = dialog.findViewById(R.id.spnSpecialist);
////            license = dialog.findViewById(R.id.lisence);
////            work_place = dialog.findViewById(R.id.work_place);
////            dialog.show();
////        }
////
////        btnCancel = dialog.findViewById(R.id.btnCancel);
////        btnCancel.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                dialog.dismiss();
////            }
////        });
////        btnOK.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
////    }
//}
