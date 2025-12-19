package com.example.myapplication1612;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Name {

    @SerializedName("common")
    private String commonName;

    @SerializedName("nativeName")
    private Map<String, NativeName> nativeName;


    public String getCommonName() {
        return commonName;
    }

    public String getNativeCommonName() {
        if (nativeName == null || nativeName.isEmpty()) {
            return commonName;
        }
        for (NativeName nn : nativeName.values()) {
            if (nn != null && nn.getCommonName() != null) {
                return nn.getCommonName();
            }
        }
        return commonName;
    }
}
