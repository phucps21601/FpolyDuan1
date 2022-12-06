package com.example.ps21601.fpolylib.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.service.autofill.CharSequenceTransformation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.model.LoaiSachModel;
import com.example.ps21601.fpolylib.model.PhieumuonModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {
    Context context;
    ArrayList<PhieumuonModel> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public PhieuMuonAdapter(Context context, ArrayList<PhieumuonModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.phieu_muon_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieumuonModel phieumuonModel = list.get(position);
        holder.txtMaPM.setText(phieumuonModel.getMaPM());
        holder.txtTenNguoiTao.setText(phieumuonModel.getTenNguoiTao());
        holder.txtTenNguoiMuon.setText(phieumuonModel.getTenNguoiMuon());
        holder.txtGiaSach.setText(phieumuonModel.getGiasach().toString());
        holder.txtGiaThue.setText(phieumuonModel.getGiathue().toString());
        holder.txtNgayMuon.setText(phieumuonModel.getNgayThue());
        holder.txtNgayTra.setText(phieumuonModel.getNgayTra());
        holder.txtTenSach.setText(phieumuonModel.getTenSachPM());
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        holder.txtNgayTra.setText(simpleDateFormat.format(calendar.getTime()));
                        holder.txtNgayTra.setTextColor(3455);
                        String ngaytra = holder.txtNgayTra.getText().toString();
                        String ngaymuon = holder.txtNgayMuon.getText().toString();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate ngayTra = LocalDate.parse(ngaytra,formatter);
                        LocalDate ngayMuon = LocalDate.parse(ngaymuon,formatter);
                        long daysbetween = ChronoUnit.DAYS.between(ngayMuon,ngayTra);
                        String day = String.valueOf(daysbetween);
                        float giathue;
                        float tongngaymuon = Float.parseFloat(day);
                        float giasach = Float.parseFloat(holder.txtGiaSach.getText().toString());
                        giathue = giasach * tongngaymuon;

                        Map<String, Object> item = new HashMap<>();
                        item.put("ngaytra", simpleDateFormat.format(calendar.getTime()));
                        item.put("ma_phieumuon", holder.txtMaPM.getText().toString());
                        item.put("ten_nguoitao", holder.txtTenNguoiTao.getText().toString());
                        item.put("ten_nguoimuon", holder.txtTenNguoiMuon.getText().toString());
                        item.put("tensach_phieumuon", holder.txtTenSach.getText().toString());
                        item.put("giasach_phieumuon", holder.txtGiaSach.getText().toString());
                        item.put("ngaythue", holder.txtNgayMuon.getText().toString());
                        item.put("giathue_phieumuon", giathue);
                        db.collection("phieumuon")
                                .document(phieumuonModel.getId_PM())
                                .set(item)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
//                                    Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
//                                            Toast.LENGTH_LONG).show();
                                        Intent outIntent = new Intent("reloadPhieumuon");
                                        outIntent.putExtra("resultphieumuon", Integer.parseInt("1"));
                                        LocalBroadcastManager.getInstance(context).sendBroadcast(outIntent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }
                },nam,thang,ngay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaPM,txtTenNguoiTao,txtTenNguoiMuon,txtGiaSach,txtGiaThue,txtNgayMuon,txtTenSach,
        txtNgayTra;
        Button checkbox;
        CardView cardView;
        RecyclerView recyclerView;
        public ViewHolder(View view){
            super(view);
            txtMaPM = view.findViewById(R.id.maPhieumuonAdapter);
            txtTenNguoiTao = view.findViewById(R.id.tennguoitaoAdapter);
            txtTenNguoiMuon = view.findViewById(R.id.tenNguoiMuonAdapter);
            txtGiaSach = view.findViewById(R.id.giasachAdapter);
            txtGiaThue = view.findViewById(R.id.giathueAdapter);
            txtNgayMuon = view.findViewById(R.id.ngaymuonAdapter);
            txtNgayTra = view.findViewById(R.id.ngaytraAdapter);
            txtTenSach = view.findViewById(R.id.tensachpmAdapter);
            checkbox = view.findViewById(R.id.checkboxAdapter);
            cardView = view.findViewById(R.id.cardviewPhieumuon);
            recyclerView = view.findViewById(R.id.rcPhieumuonFrag);
        }
    }

}
