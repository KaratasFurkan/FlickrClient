package com.karatas.furkan.flickrclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<FlickrResponse.Photo> photos = Arrays.asList();
    private Map<String, String> parameters = new HashMap<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter = new RecyclerViewAdapter(photos,this);
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parameters.put("method", Constants.METHOD_RECENT);
        parameters.put("api_key",Constants.API_KEY);
        parameters.put("format",Constants.FORMAT);
        parameters.put("per_page",Constants.PER_PAGE);
        parameters.put("nojsoncallback",Constants.NOJSONCALLBACK);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlickrApi flickrApi = retrofit.create(FlickrApi.class);

        Call<FlickrResponse> call = flickrApi.getPhotos(parameters);

        call.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
                FlickrResponse flickrResponse = response.body();
                adapter = new RecyclerViewAdapter(flickrResponse.getPhotos().getPhotoList(), getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FlickrResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
