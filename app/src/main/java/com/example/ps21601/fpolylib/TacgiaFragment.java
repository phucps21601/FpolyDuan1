package com.example.ps21601.fpolylib;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ps21601.fpolylib.adapter.AdapterClickEvent;
import com.example.ps21601.fpolylib.adapter.TacgiaAdapter;
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
 * Use the {@link TacgiaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TacgiaFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FloatingActionButton btnFloatingButtonTacgia;
    private ListView lv;
    private TacgiaModel tacgiaModel = null;
    private EditText txtTenTacgia,txtMaTacgia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tacgia, container, false);

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lvTacgiaFrag);
        btnFloatingButtonTacgia = view.findViewById(R.id.floatingButtonTacgia);
        btnFloatingButtonTacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem();
            }
        });
    }

    public void DialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tac_gia_dialog, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        txtTenTacgia = view.findViewById(R.id.edtTenTacgia);
        txtMaTacgia = view.findViewById(R.id.edtMaTacgia);
        Button btnOkTacgia = view.findViewById(R.id.btnOkTacgia);
        Button btnCanTacgia = view.findViewById(R.id.btnCanTacgia);

        btnOkTacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTacgia = txtTenTacgia.getText().toString();
                String maTacgia = txtMaTacgia.getText().toString();
                // Create a new user with a first and last name
                Map<String, Object> item = new HashMap<>();
                item.put("ma_tacgia",maTacgia);
                item.put("ten_tacgia", tenTacgia);
                if(tacgiaModel == null){
                    db.collection("tacgia")
                            .add(item)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    readData();
                                    tacgiaModel = null;
                                    txtMaTacgia.setText(null);
                                    txtTenTacgia.setText(null);
                                    dialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                }
                else{
                    db.collection("tacgia")
                            .document(tacgiaModel.getMa_tacgia())
                            .set(item)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
//                                    Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
//                                            Toast.LENGTH_LONG).show();
                                    readData();
                                    tacgiaModel = null;
                                    txtMaTacgia.setText(null);
                                    txtTenTacgia.setText(null);
                                    dialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
//                                            Toast.LENGTH_LONG).show();
                                    tacgiaModel = null;
                                }
                            });
                }

                // Add a new document with a generated ID
                db.collection("course")
                        .add(item)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
//                                Toast.makeText(FirebaseActivity.this,"Inserted",
//                                        Toast.LENGTH_LONG).show();
                                readData();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(FirebaseActivity.this,"" + e.getMessage(),
//                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        btnCanTacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
    public void readData(){
        db.collection("tacgia")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<TacgiaModel> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = document.getData();
                                String tenTacgia = map.get("ten_tacgia").toString();
                                String maTacgia = map.get("ma_tacgia").toString();
                                TacgiaModel tacgia = new TacgiaModel(maTacgia,tenTacgia);
//                                tacgia.setMa_tacgia(document.getId());
                                list.add(tacgia);
                            }
                            TacgiaAdapter adapter = new TacgiaAdapter(list);
                            lv.setAdapter(adapter);
                        }

                    }
                });
    }
    @Override
    public void onEditTacgiaClick(TacgiaModel tacgiaModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tac_gia_dialog, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
//        txtMaTacgia.setText(tacgiaModel.getMa_tacgia());
//        txtTenTacgia.setText(tacgiaModel.getTen_tacgia());
    }

    @Override
    public void onDeleteTacgiaClick(TacgiaModel tacgiaModel) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa")
                .setMessage("Xóa sẽ mất vĩnh viễn!!")
                .setNegativeButton("Hủy",null)
                .setPositiveButton("Dồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("tacgia")
                                .document(tacgiaModel.getMa_tacgia())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        TacgiaFragment tacgiaFragment = new TacgiaFragment();
                                        tacgiaFragment.readData();
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

}