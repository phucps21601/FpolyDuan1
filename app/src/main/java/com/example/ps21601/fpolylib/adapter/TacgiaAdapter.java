package com.example.ps21601.fpolylib.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps21601.fpolylib.MainActivity;
import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.TacgiaFragment;
import com.example.ps21601.fpolylib.model.SachModel;
import com.example.ps21601.fpolylib.model.TacgiaModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TacgiaAdapter extends RecyclerView.Adapter<TacgiaAdapter.ViewHolder> {
    Context context;
    ArrayList<TacgiaModel> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public TacgiaAdapter(Context context, ArrayList<TacgiaModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.tacgia_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TacgiaModel tacgiaModel = list.get(position);
        holder.txtMatacgia.setText(tacgiaModel.getMa_tacgia());
        holder.txtTentacgia.setText(tacgiaModel.getTen_tacgia());
        holder.btnEditTacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnDeleteTacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
         TextView txtMatacgia,txtTentacgia;
         ImageButton btnEditTacgia,btnDeleteTacgia;
            CardView cardView;
        public ViewHolder(View view){
            super(view);
            txtMatacgia = view.findViewById(R.id.maTacgiaAdapter);
            txtTentacgia = view.findViewById(R.id.tenTacgiaAdapter);
            btnEditTacgia = view.findViewById(R.id.btnEditTacgia);
            btnDeleteTacgia = view.findViewById(R.id.btnDeleteTacgia);
            cardView = view.findViewById(R.id.cardviewTacgia);
        }
    }

}
