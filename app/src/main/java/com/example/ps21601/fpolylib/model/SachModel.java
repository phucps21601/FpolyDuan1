package com.example.ps21601.fpolylib.model;

public class SachModel {
    private String ten_sach,namxb_sach,ten_loaisach,ten_NXB,ten_tacgia,hinh,id_sach;

    public SachModel(String ten_sach, String namxb_sach, String ten_loaisach, String ten_NXB, String ten_tacgia, String hinh
    ,String id_sach) {
        this.ten_sach = ten_sach;
        this.namxb_sach = namxb_sach;
        this.ten_loaisach = ten_loaisach;
        this.ten_NXB = ten_NXB;
        this.ten_tacgia = ten_tacgia;
        this.hinh = hinh;
        this.id_sach = id_sach;
    }

    public String getTen_sach() {
        return ten_sach;
    }

    public void setTen_sach(String ten_sach) {
        this.ten_sach = ten_sach;
    }

    public String getNamxb_sach() {
        return namxb_sach;
    }

    public void setNamxb_sach(String namxb_sach) {
        this.namxb_sach = namxb_sach;
    }

    public String getTen_loaisach() {
        return ten_loaisach;
    }

    public void setTen_loaisach(String ten_loaisach) {
        this.ten_loaisach = ten_loaisach;
    }

    public String getTen_NXB() {
        return ten_NXB;
    }

    public void setTen_NXB(String ten_NXB) {
        this.ten_NXB = ten_NXB;
    }

    public String getId_sach() {
        return id_sach;
    }

    public void setId_sach(String id_sach) {
        this.id_sach = id_sach;
    }

    public String getTen_tacgia() {
        return ten_tacgia;
    }

    public void setTen_tacgia(String ten_tacgia) {
        this.ten_tacgia = ten_tacgia;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
