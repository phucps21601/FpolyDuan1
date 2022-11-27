package com.example.ps21601.fpolylib.model;

import java.io.Serializable;

public class TacgiaModel implements Serializable {
    private String ma_tacgia,ten_tacgia;

    public TacgiaModel(String ma_tacgia, String ten_tacgia) {
        this.ma_tacgia = ma_tacgia;
        this.ten_tacgia = ten_tacgia;
    }

    public String getMa_tacgia() {
        return ma_tacgia;
    }

    public void setMa_tacgia(String ma_tacgia) {
        this.ma_tacgia = ma_tacgia;
    }

    public String getTen_tacgia() {
        return ten_tacgia;
    }

    public void setTen_tacgia(String ten_tacgia) {
        this.ten_tacgia = ten_tacgia;
    }
}
