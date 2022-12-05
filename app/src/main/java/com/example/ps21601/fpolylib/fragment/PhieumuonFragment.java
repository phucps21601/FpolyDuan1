package com.example.ps21601.fpolylib.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.adapter.LoaiSachAdapter;
import com.example.ps21601.fpolylib.adapter.PhieuMuonAdapter;
import com.example.ps21601.fpolylib.model.LoaiSachModel;
import com.example.ps21601.fpolylib.model.PhieumuonModel;
import com.example.ps21601.fpolylib.model.SachModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class PhieumuonFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FloatingActionButton btnFloatingButtonPhieumuon;
    RecyclerView recyclerView;
    private PhieumuonModel phieumuonModel = null;
    private EditText txtMaPm, txtTenNguoitao,txtTennguoimuon,
    txtGiaSach;
    private TextView txtNgaythue;
    private Spinner spnTenSach;
    private ImageView imgNgaythue;
    private ArrayList<PhieumuonModel> list;
    PhieuMuonAdapter phieuMuonAdapter;
    private ArrayList<String> arraySach;
    private ArrayAdapter<String> adapterSach;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        readData();
        return inflater.inflate(R.layout.fragment_phieumuon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcPhieumuonFrag);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), list);
        recyclerView.setAdapter(phieuMuonAdapter);
        btnFloatingButtonPhieumuon = view.findViewById(R.id.floatingButtonPhieumuon);
        btnFloatingButtonPhieumuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem();
            }
        });
    }

    public void DialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.phieu_muon_dialog, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        txtMaPm = view.findViewById(R.id.edtmaPM);
        txtTenNguoitao = view.findViewById(R.id.edtTenNguoitao);
        txtTennguoimuon = view.findViewById(R.id.edtTenNguoiMuon);
        spnTenSach = view.findViewById(R.id.spnTenSach);
        txtGiaSach = view.findViewById(R.id.edtGiaSach);
        txtNgaythue = view.findViewById(R.id.tvNgayThue);
        imgNgaythue = view.findViewById(R.id.dialog_NgayThue);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        txtTenNguoitao.setText(user.getEmail());
        arraySach = new ArrayList<>();
        adapterSach = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                arraySach);
        adapterSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTenSach.setAdapter(adapterSach);
        readDataSach();
        imgNgaythue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgaythue();
            }
        });

        Button btnOKPM = view.findViewById(R.id.btnOkPhieumuon);
        btnOKPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtMaPM = txtMaPm.getText().toString();
                String txtNguoitao = user.getEmail();
                String txtNguoimuon = txtTennguoimuon.getText().toString();
                String txtTenSach = spnTenSach.getSelectedItem().toString();
                float txtGia_Sach = Float.parseFloat(txtGiaSach.getText().toString());
                String txtNgay_thue = txtNgaythue.getText().toString();
                Map<String, Object> item = new HashMap<>();
                item.put("ma_phieumuon", txtMaPM);
                item.put("ten_nguoitao", txtNguoitao);
                item.put("ten_nguoimuon",txtNguoimuon);
                item.put("tensach_phieumuon",txtTenSach);
                item.put("giasach_phieumuon",txtGia_Sach);
                item.put("ngaythue",txtNgay_thue);
                item.put("ngaytra","Chưa trả sách");
                item.put("giathue_phieumuon",0);
                if (phieumuonModel == null) {
                    db.collection("phieumuon")
                            .add(item)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    readData();
                                    phieumuonModel = null;
                                    txtMaPm.setText(null);
                                    txtTennguoimuon.setText(null);
                                    txtNgaythue.setText(null);
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

    private void ChonNgaythue(){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtNgaythue.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }


    public void readDataSach(){
        db.collection("sach")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() >0){
                            arraySach.clear();
                            for (DocumentSnapshot doc: queryDocumentSnapshots){
                                arraySach.add(doc.getString("ten_sach"));
                            }
                            adapterSach.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void readData() {
        db.collection("phieumuon")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = document.getData();
                                String txtMaPM = map.get("ma_phieumuon").toString();
                                String txtNguoiTao = map.get("ten_nguoitao").toString();
                                String txtNguoiMuon = map.get("ten_nguoimuon").toString();
                                String txtTenSach = map.get("tensach_phieumuon").toString();
                                String txtNgayThue = map.get("ngaythue").toString();
                                String txtNgayTra = map.get("ngaytra").toString();
                                float txtGiasach = Float.parseFloat(map.get("giasach_phieumuon").toString());
                                float txtGiathue = Float.parseFloat(map.get("giathue_phieumuon").toString());
                                PhieumuonModel phieumuonModel = new PhieumuonModel(txtMaPM,txtNguoiTao,txtNguoiMuon
                                ,txtTenSach,txtNgayThue,txtNgayTra,txtGiathue,txtGiasach);
                                phieumuonModel.setId_PM(document.getId());
                                list.add(phieumuonModel);
                            }
                            phieuMuonAdapter = new PhieuMuonAdapter(getContext(), list);
                            recyclerView.setAdapter(phieuMuonAdapter);
                        }
                    }
                });
    }
    public void onResume() {
        super.onResume();
        IntentFilter loginFilter = new IntentFilter("reloadPhieumuon");
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
            int count = intent.getIntExtra("resultphieumuon", 0);
            Log.d(">>>>>>>>TAG", "onReceive: " + count);
            if (count != 0) {

                list.clear();
                readData();

            }


        };
    };
}