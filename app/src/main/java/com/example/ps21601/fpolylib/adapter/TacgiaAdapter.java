package com.example.ps21601.fpolylib.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.model.TacgiaModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TacgiaAdapter extends RecyclerView.Adapter<TacgiaAdapter.ViewHolder> {
    Context context;
    ArrayList<TacgiaModel> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TacgiaAdapter tacgiaAdapter;


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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.tac_gia_dialog, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                EditText txtTenTacgia = view.findViewById(R.id.edtTenTacgia);
                EditText txtMaTacgia = view.findViewById(R.id.edtMaTacgia);
                TextView tiltleTacgia = view.findViewById(R.id.titleTacgia);
                tiltleTacgia.setText("Cập nhật tác giả");
                Button btnOKupdateTacgia = view.findViewById(R.id.btnOkTacgia);
                Button btnCanupdateTacgia = view.findViewById(R.id.btnCanTacgia);
                btnOKupdateTacgia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenTacgia = txtTenTacgia.getText().toString();
                        String maTacgia = txtMaTacgia.getText().toString();
                        Map<String, Object> item = new HashMap<>();
                        item.put("ma_tacgia", maTacgia);
                        item.put("ten_tacgia", tenTacgia);
                            db.collection("tacgia")
                                    .document(tacgiaModel.getTacgia_id())
                                    .set(item)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
//                                    Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
//                                            Toast.LENGTH_LONG).show();
                                            txtMaTacgia.setText(maTacgia);
                                            txtTenTacgia.setText(tenTacgia);
                                            dialog.dismiss();
                                            Intent outIntent = new Intent("reloadTacgia");
                                            outIntent.putExtra("result", Integer.parseInt("1"));
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
                btnCanupdateTacgia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        holder.btnDeleteTacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa")
                        .setMessage("Xóa sẽ mất vĩnh viễn!!")
                        .setNegativeButton("Hủy",null)
                        .setPositiveButton("Dồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("tacgia")
                                        .document(tacgiaModel.getTacgia_id())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Intent outIntent = new Intent("reloadTacgia");
                                                outIntent.putExtra("result", Integer.parseInt("1"));
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
         TextView txtMatacgia,txtTentacgia;
         ImageButton btnEditTacgia,btnDeleteTacgia;
            CardView cardView;
        RecyclerView recyclerView;
        public ViewHolder(View view){
            super(view);
            txtMatacgia = view.findViewById(R.id.maTacgiaAdapter);
            txtTentacgia = view.findViewById(R.id.tenTacgiaAdapter);
            btnEditTacgia = view.findViewById(R.id.btnEditTacgia);
            btnDeleteTacgia = view.findViewById(R.id.btnDeleteTacgia);
            cardView = view.findViewById(R.id.cardviewTacgia);
            recyclerView = view.findViewById(R.id.rcTacgiaFrag);
        }
    }



}
