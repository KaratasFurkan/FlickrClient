package com.karatas.furkan.flickrclient;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FlickrApi {

    @GET("rest/?method=flickr.photos.getRecent&api_key=6d0a61a8a0d0105537fd86293e4d905c&format=json&nojsoncallback=1&auth_token=72157678209036077-ce55c986840f8a3b&api_sig=1bdcbc286b36e517f4dae43603029452")
    Call<FlickrResponse> getRecentPhotos();
}