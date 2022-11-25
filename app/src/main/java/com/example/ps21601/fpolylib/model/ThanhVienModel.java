package com.example.ps21601.fpolylib.model;

public class ThanhVienModel {
    private String username,password,ngaysinh,email,diachi,id_thanhvien;

    public ThanhVienModel(String username, String password, String ngaysinh, String email, String diachi
    ,String id_thanhvien) {
        this.username = username;
        this.password = password;
        this.ngaysinh = ngaysinh;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
