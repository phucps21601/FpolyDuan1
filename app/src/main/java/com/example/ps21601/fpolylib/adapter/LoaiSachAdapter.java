package com.example.ps21601.fpolylib.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.model.LoaiSachModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    Context context;
    ArrayList<LoaiSachModel> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LoaiSachAdapter loaiSachAdapter;


    public LoaiSachAdapter(Context context, ArrayList<LoaiSachModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.loai_sach_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSachModel loaiSachModel = list.get(position);
        holder.txtMaLoaiSach.setText(loaiSachModel.getMa_loaisach());
        holder.txtTenLoaiSach.setText(loaiSachModel.getTen_loaisach());
        holder.btnEditLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.them_loai_sach_dialog, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                EditText txtMaLoaiSach = view.findViewById(R.id.edtMaLoaiSach);
                EditText txtTenLoaiSach = view.findViewById(R.id.edtTenLoaiSach);
                TextView tiltleLoaiSach = view.findViewById(R.id.titleLoaiSach);
                tiltleLoaiSach.setText("Cập nhật loại sách ");
                Button btnOKupdateLoaisach = view.findViewById(R.id.btnOkLoaiSach);
                Button btnCanupdateLoaisach = view.findViewById(R.id.btnCanLoaiSach);
                btnOKupdateLoaisach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenloaisach = txtTenLoaiSach.getText().toString();
                        String maloaisach = txtMaLoaiSach.getText().toString();
                        Map<String, Object> item = new HashMap<>();
                        item.put("ma_loaisach", maloaisach);
                        item.put("ten_loaisach", tenloaisach);
                        db.collection("loaisach")
                                .document(loaiSachModel.getLoaisach_id())
                                .set(item)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
//                                    Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
//                                            Toast.LENGTH_LONG).show();
                                        txtMaLoaiSach.setText(maloaisach);
                                        txtTenLoaiSach.setText(tenloaisach);
                                        dialog.dismiss();
                                        Intent outIntent = new Intent("reloadLoaisach");
                                        outIntent.putExtra("resultloaisach", Integer.parseInt("1"));
                                        LocalBroadcastManager.getInstance(context).sendBroadcast(outIntent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });


                    }
                });
                btnCanupdateLoaisach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        holder.btnDeleteLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa")
                        .setMessage("Xóa sẽ mất vĩnh viễn!!")
                        .setNegativeButton("Hủy",null)
                        .setPositiveButton("Dồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("loaisach")
                                        .document(loaiSachModel.getLoaisach_id())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Intent outIntent = new Intent("reloadLoaisach");
                                                outIntent.putExtra("resultloaisach", Integer.parseInt("1"));
                                                LocalBroadcastManager.getInstance(context).sendBroadcast(outIntent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaLoaiSach,txtTenLoaiSach;
        ImageButton btnEditLoaiSach,btnDeleteLoaiSach;
        CardView cardView;
        RecyclerView recyclerView;
        public ViewHolder(View view){
            super(view);
            txtMaLoaiSach = view.findViewById(R.id.maLoaiSachAdapter);
            txtTenLoaiSach = view.findViewById(R.id.tenLoaiSachAdapter);
            btnEditLoaiSach = view.findViewById(R.id.btnEditLoaiSach);
            btnDeleteLoaiSach = view.findViewById(R.id.btnDeleteLoaiSach);
            cardView = view.findViewById(R.id.cardviewLoaiSach);
            recyclerView = view.findViewById(R.id.rcLoaiSachFrag);
        }
    }



}