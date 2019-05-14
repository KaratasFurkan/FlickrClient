package com.karatas.furkan.flickrclient;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FlickrApi {

    @GET("rest/?method=flickr.photos.getRecent&api_key=15a9793153b71f937ee835c596dfbd7a&format=json&nojsoncallback=1&auth_token=72157678235273657-c49b34bdc08e0558&api_sig=a69d0ee682afabcdef4ef6e8884df74b")
    Call<FlickrResponse> getRecentPhotos();
}