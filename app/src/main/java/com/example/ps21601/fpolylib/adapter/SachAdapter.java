package com.example.ps21601.fpolylib.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.model.SachModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;



public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    Context context;
    ArrayList<SachModel> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public SachAdapter(Context context, ArrayList<SachModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.sach_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            SachModel sachModel = list.get(position);
            holder.txtMaSach.setText(sachModel.getMa_sach());
            holder.txtTenSach.setText(sachModel.getTen_sach());
            holder.txtTenLoaiSach1.setText(sachModel.getTen_loaisach());
            holder.txtTenTacgia1.setText(sachModel.getTen_tacgia());
            holder.txtTenNXB1.setText(sachModel.getTen_NXB());
            holder.txtnamXB.setText(sachModel.getNamxb_sach());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaSach, txtTenSach,txtTenLoaiSach1,txtTenTacgia1,txtTenNXB1,txtnamXB;
        ImageButton btnEditSach, btnDeleteSach;
        CardView cardView;
        RecyclerView recyclerView;
        public ViewHolder(View view){
            super(view);
            txtMaSach = view.findViewById(R.id.maSachAdapter);
            txtTenSach = view.findViewById(R.id.tenSachAdapter);
            txtTenLoaiSach1 = view.findViewById(R.id.tenLoaiSachAdapter1);
            txtTenTacgia1 = view.findViewById(R.id.tenTacgiaAdapter1);
            txtTenNXB1 = view.findViewById(R.id.tenNXBAdapter1);
            txtnamXB = view.findViewById(R.id.namXBAdapter1);
            btnEditSach = view.findViewById(R.id.btnEditSach);
            btnDeleteSach = view.findViewById(R.id.btnDeleteSach);
            cardView = view.findViewById(R.id.cardviewSach);
            recyclerView = view.findViewById(R.id.rcSachFrag);
        }
    }
}
