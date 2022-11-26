package com.example.ps21601.fpolylib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ps21601.fpolylib.model.SachModel;

import java.util.ArrayList;

public class SachAdapter extends BaseAdapter {
    private ArrayList<SachModel> list;

    public SachAdapter(ArrayList<SachModel> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public static class ViewHolder{
        final EditText txtMaSach,txtTenSach,txtNamXB,txtTen_Loaisach,txtten_NXB,txtTen_Tacgia,txtHinh;
        final ImageButton btnEdit,btnDelete;
        public ViewHolder(EditText txtMa_sach,EditText txtTenSach,EditText txtNamXB,EditText txtTen_Loaisach,
                          EditText txtten_NXB,EditText txtTen_Tacgia,EditText txtHinh,ImageButton
                           btnEdit,ImageButton btnDelete){
            this.txtMaSach = txtMa_sach;
            this.txtTenSach = txtTenSach;
            this.txtNamXB = txtNamXB;
            this.txtTen_Loaisach = txtTen_Loaisach;
            this.txtten_NXB = txtten_NXB;
            this.txtTen_Tacgia = txtTen_Tacgia;
            this.txtHinh = txtHinh;
            this.btnEdit = btnEdit;
            this.btnDelete = btnDelete;
        }
    }
}
