package com.example.ps21601.fpolylib.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.adapter.LoaiSachAdapter;
import com.example.ps21601.fpolylib.adapter.SachAdapter;
import com.example.ps21601.fpolylib.model.LoaiSachModel;
import com.example.ps21601.fpolylib.model.SachModel;
import com.example.ps21601.fpolylib.model.TacgiaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SachFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FloatingActionButton btnFloatingButtonSach;
    RecyclerView recyclerView;
    private SachModel sachModel = null;
    private EditText txtTenSach, txtMaSach,txtnamXB;
    private TextView erMaSach,erTenSach;
    SachAdapter sachAdapter;
    private ArrayList<SachModel> list;
    private ArrayList<String> arrayTacgia;
    private ArrayAdapter<String> adapterStringTacgia;
    private ArrayList<String> arrayLoaiSach;
    private ArrayAdapter<String> adapterStringLoaiSach;
    private ArrayList<String> arrayNXB;
    private ArrayAdapter<String> adapterStringNXB;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        readData();
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcSachFrag);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        sachAdapter = new SachAdapter(getContext(), list);
        recyclerView.setAdapter(sachAdapter);
        btnFloatingButtonSach = view.findViewById(R.id.floatingButtonSach);
        btnFloatingButtonSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem();
            }
        });
    }


    public void DialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.them_sach_dialog, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        progressDialog = new ProgressDialog(getContext());

        txtMaSach = view.findViewById(R.id.edtMaSach);
        txtTenSach = view.findViewById(R.id.edtTenSach);
        txtnamXB = view.findViewById(R.id.edtNamXB);
        Spinner spnTenTacgia = view.findViewById(R.id.spinnerTenTacgia);
        Spinner spnTenLoaiSach = view.findViewById(R.id.spinnerTenLoaiSach);
        Spinner spnTenNXB = view.findViewById(R.id.spinnerTenNXB);
        Button btnOkSach = view.findViewById(R.id.btnOkSach);
        Button btnCanLoaiSach = view.findViewById(R.id.btnCancleSach);

        arrayTacgia = new ArrayList<>();
        adapterStringTacgia = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                arrayTacgia);
        adapterStringTacgia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTenTacgia.setAdapter(adapterStringTacgia);

        arrayLoaiSach = new ArrayList<>();
        adapterStringLoaiSach = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                arrayLoaiSach);
        adapterStringLoaiSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTenLoaiSach.setAdapter(adapterStringLoaiSach);

        arrayNXB = new ArrayList<>();
        adapterStringNXB = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                arrayNXB);
        adapterStringNXB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTenNXB.setAdapter(adapterStringNXB);

        readDataTacgia();
        readDataLoaiSach();
        readDataNXB();

        btnOkSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maSach = txtMaSach.getText().toString();
                String tenSach = txtTenSach.getText().toString();
                String namXB = txtnamXB.getText().toString();
                String tentacgia = spnTenTacgia.getSelectedItem().toString();
                String tenloaisach = spnTenLoaiSach.getSelectedItem().toString();
                String tenNXB = spnTenNXB.getSelectedItem().toString();
                Map<String, Object> item = new HashMap<>();
                item.put("ma_sach", maSach);
                item.put("ten_sach", tenSach);
                item.put("ten_tacgia",tentacgia);
                item.put("ten_loaisach",tenloaisach);
                item.put("ten_nxb",tenNXB);
                item.put("namxb_sach",namXB);
                if (sachModel == null) {
                    db.collection("sach")
                            .add(item)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    readData();
                                    sachModel = null;
                                    txtMaSach.setText(null);
                                    txtnamXB.setText(null);
                                    txtTenSach.setText(null);
                                    dialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            }
        });
    }


    public void readDataTacgia(){
        db.collection("tacgia")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() >0){
                            arrayTacgia.clear();
                            for (DocumentSnapshot doc: queryDocumentSnapshots){
                                TacgiaModel tacgiaModel;
                                arrayTacgia.add(doc.getString("ten_tacgia"));
                            }
                            adapterStringTacgia.notifyDataSetChanged();
                        }
                    }
                });
    }
    public void readDataLoaiSach(){
        db.collection("loaisach")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() >0){
                            arrayLoaiSach.clear();
                            for (DocumentSnapshot doc: queryDocumentSnapshots){
                                arrayLoaiSach.add(doc.getString("ten_loaisach"));
                            }
                            adapterStringLoaiSach.notifyDataSetChanged();
                        }
                    }
                });
    }
    public void readDataNXB(){
        db.collection("nxb")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() >0){
                            arrayNXB.clear();
                            for (DocumentSnapshot doc: queryDocumentSnapshots){
                                arrayNXB.add(doc.getString("ten_nxb"));
                            }
                            adapterStringNXB.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void readData() {
        db.collection("sach")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = document.getData();
                                String tenSach = map.get("ten_sach").toString();
                                String maSach = map.get("ma_sach").toString();
                                String ten_tacgia = map.get("ten_tacgia").toString();
                                String ten_loaisach = map.get("ten_loaisach").toString();
                                String ten_NXB = map.get("ten_nxb").toString();
                                String  namxb = map.get("namxb_sach").toString();
                                SachModel sach = new SachModel(maSach,tenSach,namxb,ten_loaisach,
                                        ten_NXB,ten_tacgia);
                                sach.setId_sach(document.getId());
                                list.add(sach);
                            }
                            sachAdapter = new SachAdapter(getContext(), list);
                            recyclerView.setAdapter(sachAdapter);
                        }
                    }
                });
    }
}