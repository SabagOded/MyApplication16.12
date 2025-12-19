package com.example.myapplication1612;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class NativeName {

    @SerializedName("common")
    private String commonName;

    public String getCommonName(){
        return commonName;
    }
}
