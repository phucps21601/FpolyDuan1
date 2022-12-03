package com.example.ps21601.fpolylib.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.adapter.LoaiSachAdapter;

import com.example.ps21601.fpolylib.model.LoaiSachModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LoaiSachFragment extends Fragment {

    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FloatingActionButton btnFloatingButtonLoaiSach;
    RecyclerView recyclerView;
    private LoaiSachModel loaiSachModel = null;
    private EditText txtTenLoaiSach, txtMaLoaiSach;
    private TextView erMaloaisach,erTenloaisach;
    LoaiSachAdapter LoaiSachAdapter;
    private ArrayList<LoaiSachModel> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        readData();
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcLoaiSachFrag);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
       LoaiSachAdapter = new LoaiSachAdapter(getContext(), list);
        recyclerView.setAdapter(LoaiSachAdapter);
        btnFloatingButtonLoaiSach = view.findViewById(R.id.floatingButtonLoaiSach);
        btnFloatingButtonLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem();
            }
        });
    }
    public void DialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.them_loai_sach_dialog, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        txtTenLoaiSach = view.findViewById(R.id.edtTenLoaiSach);
        txtMaLoaiSach = view.findViewById(R.id.edtMaLoaiSach);
        erMaloaisach = view.findViewById(R.id.errorMaLoaiSach);
        erTenloaisach = view.findViewById(R.id.errorTenLoaiSach);
        Button btnOkLoaiSach = view.findViewById(R.id.btnOkLoaiSach);
        Button btnCanLoaiSach = view.findViewById(R.id.btnCanLoaiSach);

        btnOkLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiSach = txtTenLoaiSach.getText().toString();
                String maLoaiSach = txtMaLoaiSach.getText().toString();
                // Create a new user with a first and last name
                if(!maLoaiSach.isEmpty() && !tenLoaiSach.isEmpty()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("ma_loaisach", maLoaiSach);
                    item.put("ten_loaisach", tenLoaiSach);
                    if (loaiSachModel == null) {
                        db.collection("loaisach")
                                .add(item)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        readData();
                                        loaiSachModel = null;
                                        txtMaLoaiSach.setText(null);
                                        txtTenLoaiSach.setText(null);
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }
                } else if(maLoaiSach.length() < 4){
                    erMaloaisach.setText("Mã loại sách phải lớn hơn 4 kí tự");
                    erTenloaisach.setText("");
                }
                else if(tenLoaiSach.isEmpty()){
                    erTenloaisach.setText("Tên loại sách không được để trống");
                    erMaloaisach.setText("");
                }
                else if(maLoaiSach.isEmpty()){
                    erMaloaisach.setText("Mã loại không được để trống");
                    erTenloaisach.setText("");
                }
                    btnCanLoaiSach.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }

                });


            }

    public void readData() {
        db.collection("loaisach")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = document.getData();
                                String tenLoaisach = map.get("ten_loaisach").toString();
                                String maLoaisach = map.get("ma_loaisach").toString();
                                LoaiSachModel loaiSach = new LoaiSachModel(maLoaisach, tenLoaisach);
                                loaiSach.setLoaisach_id(document.getId());
                                list.add(loaiSach);
                            }
                            LoaiSachAdapter = new LoaiSachAdapter(getContext(), list);
                            recyclerView.setAdapter(LoaiSachAdapter);
                        }
                    }
                });
    }

    public void onResume() {
        super.onResume();
        IntentFilter loginFilter = new IntentFilter("reloadLoaisach");
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(receiver, loginFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext())
                .unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int count = intent.getIntExtra("resultloaisach", 0);
            Log.d(">>>>>>>>TAG", "onReceive: " + count);
            if (count != 0) {

                list.clear();
                readData();

            }


        };
    };
}

