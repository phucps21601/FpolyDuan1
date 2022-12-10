package com.example.ps21601.fpolylib.model;

import java.io.Serializable;

public class ThongKeModel implements Serializable {
    private float giathue_thongke, phieumuon_thongke;

    public float getGiathue_thongke() {
        return giathue_thongke;
    }

    public void setGiathue_thongke(float giathue_thongke) {
        this.giathue_thongke = giathue_thongke;
    }

    public float getPhieumuon_thongke() {
        return phieumuon_thongke;
    }

    public void setPhieumuon_thongke(float phieumuon_thongke) {
        this.phieumuon_thongke = phieumuon_thongke;
    }
}

