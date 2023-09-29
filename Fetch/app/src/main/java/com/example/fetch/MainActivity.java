package com.example.fetch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

class ApiItem {
    int id;
    int listId;
    String name;
}

interface ApiInterface {
    @GET("hiring.json")
    Call<List<ApiItem>> getItems();
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvItemLists = findViewById(R.id.rvItemLists);
        rvItemLists.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        api.getItems().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<ApiItem>> call, Response<List<ApiItem>> response) {
                List<ItemList> itemLists = response.body()
                        .stream()
                        .filter(apiItem -> apiItem.name != null && !apiItem.name.isBlank())
                        .collect(Collectors.groupingBy(apiItem -> apiItem.listId))
                        .entrySet()
                        .stream()
                        .map(entry ->
                                new ItemList(
                                        entry.getKey(),
                                        entry.getValue()
                                                .stream()
                                                .map(apiItem -> new Item(apiItem.id, apiItem.name))
                                                .sorted(Comparator.comparing(Item::getName))
                                                .collect(Collectors.toList())
                                )
                        )
                        .sorted(Comparator.comparing(ItemList::getListId))
                        .collect(Collectors.toList());

                rvItemLists.setAdapter(new ItemListAdapter(itemLists));
            }

            @Override
            public void onFailure(Call<List<ApiItem>> call, Throwable t) {

            }
        });
    }
}