package com.example.ps21601.fpolylib.model;

public class ThanhVienModel {
    private String username,password,ngaysinh, ma_user,diachi,id_thanhvien;

    public ThanhVienModel(String username, String password, String ngaysinh, String ma_user, String diachi
    , String id_thanhvien) {
        this.username = username;
        this.password = password;
        this.ngaysinh = ngaysinh;
        this.ma_user = ma_user;
        this.diachi = diachi;
        this.id_thanhvien = id_thanhvien;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId_thanhvien() {
        return id_thanhvien;
    }

    public void setId_thanhvien(String id_thanhvien) {
        this.id_thanhvien = id_thanhvien;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getMa_user() {
        return ma_user;
    }

    public void setMa_user(String ma_user) {
        this.ma_user = ma_user;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
