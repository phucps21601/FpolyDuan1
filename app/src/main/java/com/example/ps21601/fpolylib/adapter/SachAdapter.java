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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.model.SachModel;
import com.example.ps21601.fpolylib.model.TacgiaModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    Context context;
    ArrayList<SachModel> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> arrayTacgia;
    private ArrayAdapter<String> adapterStringTacgia;
    private ArrayList<String> arrayLoaiSach;
    private ArrayAdapter<String> adapterStringLoaiSach;
    private ArrayList<String> arrayNXB;
    private ArrayAdapter<String> adapterStringNXB;

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
            holder.btnEditSach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
                    View view = layoutInflater.inflate(R.layout.them_sach_dialog, null);
                    builder.setView(view);
                    Dialog dialog = builder.create();
                    dialog.show();


                    TextView txtMaSach = view.findViewById(R.id.edtMaSach);
                    TextView txtTenSach = view.findViewById(R.id.edtTenSach);
                    Spinner spnTacgia = view.findViewById(R.id.spinnerTenTacgia);
                    Spinner spnNXB = view.findViewById(R.id.spinnerTenNXB);
                    Spinner spnLoaiSach = view.findViewById(R.id.spinnerTenLoaiSach);
                    TextView tvnam_xb = view.findViewById(R.id.edtNamXB);
                    Button btnOKSach = view.findViewById(R.id.btnOkSach);
                    Button btnCancleSach = view.findViewById(R.id.btnCancleSach);

                    arrayTacgia = new ArrayList<>();
                    adapterStringTacgia = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                            arrayTacgia);
                    adapterStringTacgia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnTacgia.setAdapter(adapterStringTacgia);

                    arrayLoaiSach = new ArrayList<>();
                    adapterStringLoaiSach = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                            arrayLoaiSach);
                    adapterStringLoaiSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnLoaiSach.setAdapter(adapterStringLoaiSach);

                    arrayNXB = new ArrayList<>();
                    adapterStringNXB = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                            arrayNXB);
                    adapterStringNXB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnNXB.setAdapter(adapterStringNXB);
                    readDataLoaiSach();
                    readDataNXB();
                    readDataTacgia();

                    btnOKSach.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String maSach = txtMaSach.getText().toString();
                            String tenSach = txtTenSach.getText().toString();
                            String namXB = tvnam_xb.getText().toString();
                            String tentacgia = spnTacgia.getSelectedItem().toString();
                            String tenloaisach = spnLoaiSach.getSelectedItem().toString();
                            String tenNXB = spnNXB.getSelectedItem().toString();
                            Map<String, Object> item = new HashMap<>();
                            item.put("ma_sach", maSach);
                            item.put("ten_sach", tenSach);
                            item.put("ten_tacgia",tentacgia);
                            item.put("ten_loaisach",tenloaisach);
                            item.put("ten_nxb",tenNXB);
                            item.put("namxb_sach",namXB);
                            db.collection("sach")
                                    .document(sachModel.getId_sach())
                                    .set(item)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
//                                    Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
//                                            Toast.LENGTH_LONG).show();
                                            txtMaSach.setText(maSach);
                                            txtTenSach.setText(tenSach);
                                            tvnam_xb.setText(namXB);
                                            dialog.dismiss();
                                            Intent outIntent = new Intent("reloadSach");
                                            outIntent.putExtra("resultsach", Integer.parseInt("1"));
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

            holder.btnDeleteSach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle("Xóa")
                            .setMessage("Xóa sẽ mất vĩnh viễn!!")
                            .setNegativeButton("Hủy",null)
                            .setPositiveButton("Dồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.collection("sach")
                                            .document(sachModel.getId_sach())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Intent outIntent = new Intent("reloadSach");
                                                    outIntent.putExtra("resultsach", Integer.parseInt("1"));
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
}
