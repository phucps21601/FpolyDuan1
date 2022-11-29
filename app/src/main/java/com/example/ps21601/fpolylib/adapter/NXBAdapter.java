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
import com.example.ps21601.fpolylib.model.NXBModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NXBAdapter extends RecyclerView.Adapter<NXBAdapter.ViewHolder> {
    Context context;
    ArrayList<NXBModel> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public NXBAdapter(Context context, ArrayList<NXBModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override



    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.nxb_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NXBModel nxbModel = list.get(position);
        holder.txtMaNXB.setText(nxbModel.getMa_NXB());
        holder.txtTenNXB.setText(nxbModel.getTen_NXB());
        holder.btnEditNXB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.them_nxb_dialog, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                EditText txtTenNXB = view.findViewById(R.id.edTenNXB);
                EditText txtMaNXB = view.findViewById(R.id.edMaNXB);
                Button btnOKupdateNXB = view.findViewById(R.id.btnOKNXB);
                btnOKupdateNXB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenNXB = txtTenNXB.getText().toString();
                        String maNXB = txtMaNXB.getText().toString();
                        Map<String, Object> item = new HashMap<>();
                        item.put("ma_nxb", maNXB);
                        item.put("ten_nxb", tenNXB);
                        db.collection("nxb")
                                .document(nxbModel.getNxb_id())
                                .set(item)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
//                                    Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
//                                            Toast.LENGTH_LONG).show();
                                        txtMaNXB.setText(maNXB);
                                        txtTenNXB.setText(tenNXB);
                                        dialog.dismiss();
                                        Intent outIntent = new Intent("reloadNXB");
                                        outIntent.putExtra("resultNXB", Integer.parseInt("1"));
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
            }
        });
        holder.btnDeleteNXB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa")
                        .setMessage("Xóa sẽ mất vĩnh viễn!!")
                        .setNegativeButton("Hủy",null)
                        .setPositiveButton("Dồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("nxb")
                                        .document(nxbModel.getNxb_id())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Intent outIntent = new Intent("reloadNXB");
                                                outIntent.putExtra("resultNXB", Integer.parseInt("1"));
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
        TextView txtMaNXB, txtTenNXB;
        ImageButton btnEditNXB, btnDeleteNXB;
        CardView cardView;
        RecyclerView recyclerView;
        public ViewHolder(View view){
            super(view);
            txtMaNXB = view.findViewById(R.id.maNXBAdapter);
            txtTenNXB = view.findViewById(R.id.tenNXBAdapter);
            btnEditNXB = view.findViewById(R.id.btnEditNXB);
            btnDeleteNXB = view.findViewById(R.id.btnDeleteNXB);
            cardView = view.findViewById(R.id.cardviewNXB);
            recyclerView = view.findViewById(R.id.rcNXBFrag);
        }
    }
}
