package com.example.ps21601.fpolylib.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.model.SachModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;


public class PhieumuonFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FloatingActionButton btnFloatingButtonPhieumuon;
    RecyclerView recyclerView;
    private SachModel sachModel = null;
    private EditText txtTenSach, txtMaSach,txtnamXB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phieumuon, container, false);
    }
}