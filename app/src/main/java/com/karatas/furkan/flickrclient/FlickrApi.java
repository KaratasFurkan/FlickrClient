package com.karatas.furkan.flickrclient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface FlickrApi {
    @GET("rest/")
    Call<FlickrResponse> getPhotos(@QueryMap Map<String, String> parameters);
}