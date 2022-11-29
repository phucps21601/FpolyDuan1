package com.example.ps21601.fpolylib.model;

import java.io.Serializable;

public class NXBModel implements Serializable {
    private String ma_NXB,ten_NXB,nxb_id;

    public NXBModel(String ma_NXB, String ten_NXB) {
        this.ma_NXB = ma_NXB;
        this.ten_NXB = ten_NXB;
    }

    public String getMa_NXB() {
        return ma_NXB;
    }

    public void setMa_NXB(String ma_NXB) {
        this.ma_NXB = ma_NXB;
    }

    public String getTen_NXB() {
        return ten_NXB;
    }

    public void setTen_NXB(String ten_NXB) {
        this.ten_NXB = ten_NXB;
    }

    public String getNxb_id() {
        return nxb_id;
    }

    public void setNxb_id(String nxb_id) {
        this.nxb_id = nxb_id;
    }
}
