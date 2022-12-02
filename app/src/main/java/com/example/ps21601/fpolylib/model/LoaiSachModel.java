package com.example.ps21601.fpolylib.model;

import java.io.Serializable;

public class LoaiSachModel implements Serializable {
    private String ma_loaisach,ten_loaisach,loaisach_id;

    public LoaiSachModel(String ma_loaisach, String ten_loaisach) {
        this.ma_loaisach = ma_loaisach;
        this.ten_loaisach = ten_loaisach;
    }

    public String getMa_loaisach() {
        return ma_loaisach;
    }

    public void setMa_loaisach(String ma_loaisach) {
        this.ma_loaisach = ma_loaisach;
    }

    public String getTen_loaisach() {
        return ten_loaisach;
    }

    public void setTen_loaisach(String ten_loaisach) {
        this.ten_loaisach = ten_loaisach;
    }

    public String getLoaisach_id() {
        return loaisach_id;
    }

    public void setLoaisach_id(String loaisach_id) {
        this.loaisach_id = loaisach_id;
    }
}
