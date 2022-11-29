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
import com.example.ps21601.fpolylib.adapter.NXBAdapter;
import com.example.ps21601.fpolylib.adapter.TacgiaAdapter;
import com.example.ps21601.fpolylib.model.NXBModel;
import com.example.ps21601.fpolylib.model.TacgiaModel;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NXBFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NXBFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FloatingActionButton btnFloatingButtonNXB;
    RecyclerView recyclerView;
    private NXBModel nxbModel = null;
    private EditText txtMaNXB, txtTenNXB;
    private TextView erMaNXB,erTenNXB;
    NXBAdapter nxbAdapter;
    private ArrayList<NXBModel> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        readData();
        return inflater.inflate(R.layout.fragment_n_x_b, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcNXBFrag);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        nxbAdapter = new NXBAdapter(getContext(), list);
        recyclerView.setAdapter(nxbAdapter);
        btnFloatingButtonNXB = view.findViewById(R.id.floatingButtonNXB);
        btnFloatingButtonNXB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem();
            }
        });
    }

    public void DialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.them_nxb_dialog, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        txtMaNXB = view.findViewById(R.id.edMaNXB);
        txtTenNXB = view.findViewById(R.id.edTenNXB);
        erMaNXB = view.findViewById(R.id.errorMaNXB);
        erTenNXB = view.findViewById(R.id.errorTenNXB);
        Button btnOKNXB = view.findViewById(R.id.btnOKNXB);
        Button btnCanNXB = view.findViewById(R.id.btnCanNXB);

        btnOKNXB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTacgia = txtMaNXB.getText().toString();
                String maTacgia = txtTenNXB.getText().toString();
                // Create a new user with a first and last name
                if(!maTacgia.isEmpty() || !tenTacgia.isEmpty()){
                    Map<String, Object> item = new HashMap<>();
                    item.put("ma_nxb", maTacgia);
                    item.put("ten_nxb", tenTacgia);
                    if (nxbModel == null) {
                        db.collection("nxb")
                                .add(item)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        readData();
                                        nxbModel = null;
                                        txtTenNXB.setText(null);
                                        txtMaNXB.setText(null);
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                    } else {

                    }} else if(maTacgia.length() < 4){
                    erMaNXB.setText("Mã NXB phải lớn hơn 4 kí tự");
                }
                else{
                    erMaNXB.setText("Mã NXB không được để trống");
                    erTenNXB.setText("Tên NXB không được để trống");
                }

            }
        });

        btnCanNXB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    public void readData() {
        db.collection("nxb")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = document.getData();
                                String tenNXB = map.get("ten_nxb").toString();
                                String maNXB = map.get("ma_nxb").toString();
                                NXBModel nxb = new NXBModel(maNXB, tenNXB);
                                nxb.setNxb_id(document.getId());
                                list.add(nxb);
                            }
                            nxbAdapter = new NXBAdapter(getContext(), list);
                            recyclerView.setAdapter(nxbAdapter);
                        }
                    }
                });
    }

    public void onResume() {
        super.onResume();
        IntentFilter loginFilter = new IntentFilter("reloadNXB");
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
            int count = intent.getIntExtra("resultNXB", 0);
            Log.d(">>>>>>>>TAG", "onReceive: "+count);
            if (count != 0) {

                list.clear();
                readData();
            }
        }
    };
}