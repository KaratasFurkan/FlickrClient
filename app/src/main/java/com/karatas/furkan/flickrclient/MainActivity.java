package com.karatas.furkan.flickrclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
    private FlickrResponse flickrResponse;
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
        loadImages(parameters);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE); //to expand full width

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                parameters.put("method", Constants.METHOD_SEARCH);
                parameters.put("tags", searchText);
                loadImages(parameters);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                parameters.put("method", Constants.METHOD_RECENT);
                parameters.remove("tags");
                loadImages(parameters);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void loadImages(Map<String, String> parameters){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlickrApi flickrApi = retrofit.create(FlickrApi.class);

        Call<FlickrResponse> call = flickrApi.getPhotos(parameters);

        call.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
                flickrResponse = response.body();
                adapter = new RecyclerViewAdapter(flickrResponse.getPhotos().getPhotoList(), getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(onItemClickListener);
            }

            @Override
            public void onFailure(Call<FlickrResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerViewAdapter.ImageViewHolder viewHolder =
                    (RecyclerViewAdapter.ImageViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Intent intent = new Intent(getApplicationContext(), FullScreenActivity.class);
            intent.putExtra("url",
                    flickrResponse.getPhotos().getPhotoList()
                            .get(position).generateLargePhotoUrl());
            startActivity(intent);
        }
    };
}
