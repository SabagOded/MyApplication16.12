package com.example.myapplication1612;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE = "https://restcountries.com/v3.1/";
    private RecyclerView recyclerCountries;
    private CountryAdapter adapter;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerCountries = findViewById(R.id.recyclerCountries);
        adapter = new CountryAdapter();
        recyclerCountries.setLayoutManager(new LinearLayoutManager(this));
        recyclerCountries.setAdapter(adapter);
        fetchCountries();

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }
        });
        }

    private void fetchCountries() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CountryApiService service = retrofit.create(CountryApiService.class); //כאן נבצע את הקריאה למידע שלנו

        Call<List<Country>> call = service.getAllCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    //נבדוק אם הוא הצליח, ובנוסף נבדוק שהוא לא ריק
                    List<Country> countryList = response.body();
                    adapter.setCountries(countryList);
                }
                else{
                    Log.d("result: ","Response not successfully.");
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.d("result: ", "API Call failed: " + t.getMessage());
            }
        });
    }
}