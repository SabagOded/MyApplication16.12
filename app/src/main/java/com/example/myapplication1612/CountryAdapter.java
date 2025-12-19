package com.example.myapplication1612;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter
        extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private final List<Country> countries = new ArrayList<>();
    private final List<Country> allCountries = new ArrayList<>();
    static class CountryViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        TextView txtNativeName;
        ImageView imgFlag;
        public CountryViewHolder(@NonNull View itemView){
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtNativeName = itemView.findViewById(R.id.txtNativeName);
            imgFlag = itemView.findViewById(R.id.imgFlag);
        }

    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        Country c = countries.get(position);
        holder.txtName.setText(c.getName());
        holder.txtNativeName.setText(c.getNativeName());
        Glide.with(holder.itemView.getContext())
                .load(c.getFlagUrl())
                .into(holder.imgFlag);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setCountries(List<Country> newCountries){
        allCountries.clear();
        countries.clear();

        if(newCountries != null){
            allCountries.addAll(newCountries);
            countries.addAll(newCountries);
        }
        notifyDataSetChanged();
    }

    public void filter(String query){
        countries.clear();

        if (query == null || query.trim().isEmpty()){
            countries.addAll(allCountries);
        } else {
            String q = query.toLowerCase().trim();

            for (Country c : allCountries) {
                String name = c.getName() != null ? c.getName() : "";
                String nativeName = c.getNativeName() != null ? c.getNativeName() : "";

                if (name.toLowerCase().contains(q) || nativeName.toLowerCase().contains(q)){
                    countries.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }


}
