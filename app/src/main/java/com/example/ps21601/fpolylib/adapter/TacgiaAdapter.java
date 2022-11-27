package com.example.ps21601.fpolylib.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ps21601.fpolylib.R;
import com.example.ps21601.fpolylib.model.SachModel;
import com.example.ps21601.fpolylib.model.TacgiaModel;

import java.util.ArrayList;

public class TacgiaAdapter extends BaseAdapter {
    private ArrayList<TacgiaModel> list;

    public TacgiaAdapter(ArrayList<TacgiaModel> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewgroup) {
        View view = _view;
        if (view == null) {
            view = View.inflate(_viewgroup.getContext(), R.layout.tacgia_item, null);
            TextView txtTenTacgia = view.findViewById(R.id.tenTacgiaAdapter);
            TextView txtMaTacgia = view.findViewById(R.id.maTacgiaAdapter);
            ImageButton btnEditTacgia = view.findViewById(R.id.btnEditTacgia);
            ImageButton btnDeleteTacgia = view.findViewById(R.id.btnDeleteTacgia);
            ViewHolder holder = new ViewHolder(txtMaTacgia,txtTenTacgia,btnEditTacgia,btnDeleteTacgia);
            view.setTag(holder);
        }
        TacgiaModel tacgiaModel = (TacgiaModel) getItem(_i);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtMatacgia.setText(tacgiaModel.getMa_tacgia());
        holder.txtTentacgia.setText(tacgiaModel.getTen_tacgia());
        holder.btnEditTacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterClickEvent adapterClickEvent = (AdapterClickEvent) _viewgroup.getContext();
                adapterClickEvent.onEditTacgiaClick(tacgiaModel);
            }
        });
        holder.btnDeleteTacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterClickEvent adapterClickEvent = (AdapterClickEvent) _viewgroup.getContext();
                adapterClickEvent.onEditTacgiaClick(tacgiaModel);
            }
        });
        return view;
    }

    public static class ViewHolder{
        final TextView txtMatacgia,txtTentacgia;
        final ImageButton btnEditTacgia,btnDeleteTacgia;
        public ViewHolder(TextView txtMatacgia,TextView txtTentacgia,ImageButton
                                  btnEditTacgia,ImageButton btnDeleteTacgia){
            this.txtMatacgia = txtMatacgia;
            this.txtTentacgia = txtTentacgia;
            this.btnEditTacgia = btnEditTacgia;
            this.btnDeleteTacgia = btnDeleteTacgia;
        }
    }
}
