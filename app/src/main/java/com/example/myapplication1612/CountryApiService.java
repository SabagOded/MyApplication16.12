package com.example.myapplication1612;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryApiService {

    @GET ("all?fields=name,flags,borders")
    Call<List<Country>> getAllCountries();

}
