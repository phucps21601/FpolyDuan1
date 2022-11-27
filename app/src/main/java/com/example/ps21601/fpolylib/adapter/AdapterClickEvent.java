package com.example.ps21601.fpolylib.adapter;

import com.example.ps21601.fpolylib.model.TacgiaModel;

public interface AdapterClickEvent {
    public void onEditTacgiaClick(TacgiaModel tacgiaModel);
    public void onDeleteTacgiaClick(TacgiaModel tacgiaModel);
}
