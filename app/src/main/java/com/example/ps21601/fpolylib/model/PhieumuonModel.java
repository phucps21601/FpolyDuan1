package com.example.ps21601.fpolylib.model;

public class PhieumuonModel {
    private String maPM,tenNguoiTao,tenNguoiMuon,tenSachPM,ngayThue,ngayTra,id_PM;
    private float giathue,giasach;

    public PhieumuonModel(String maPM, String tenNguoiTao, String tenNguoiMuon, String tenSachPM, String ngayThue, String ngayTra, float giathue, float giasach) {
        this.maPM = maPM;
        this.tenNguoiTao = tenNguoiTao;
        this.tenNguoiMuon = tenNguoiMuon;
        this.tenSachPM = tenSachPM;
        this.ngayThue = ngayThue;
        this.ngayTra = ngayTra;
        this.giathue = giathue;
        this.giasach = giasach;
    }

    public String getMaPM() {
        return maPM;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public String getTenNguoiTao() {
        return tenNguoiTao;
    }

    public void setTenNguoiTao(String tenNguoiTao) {
        this.tenNguoiTao = tenNguoiTao;
    }

    public String getTenNguoiMuon() {
        return tenNguoiMuon;
    }

    public void setTenNguoiMuon(String tenNguoiMuon) {
        this.tenNguoiMuon = tenNguoiMuon;
    }

    public String getTenSachPM() {
        return tenSachPM;
    }

    public void setTenSachPM(String tenSachPM) {
        this.tenSachPM = tenSachPM;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getId_PM() {
        return id_PM;
    }

    public void setId_PM(String id_PM) {
        this.id_PM = id_PM;
    }

    public Float getGiathue() {
        return giathue;
    }

    public void setGiathue(float giathue) {
        this.giathue = giathue;
    }

    public Float getGiasach() {
        return giasach;
    }

    public void setGiasach(float giasach) {
        this.giasach = giasach;
    }
}
