package com.example.myapplication1612;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {

    @SerializedName("name")
    private Name name;

    @SerializedName("flags")
    private Flags flags;

    @SerializedName("borders")
    private List<String> borders;

    public String getName() {
        return name.getCommonName();
    }

    public String getFlagUrl() {
        return flags.getPngUrl();
    }

    public List<String> getBorders() {
        return borders;
    }

    public String getNativeName(){
        return name.getNativeCommonName();
    }

}
