package com.karatas.furkan.flickrclient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlickrResponse {

    private Photos photos;
    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public class Photos{
        private int page;
        private int pages;
        private int perpage;
        private int total;
        @SerializedName("photo")
        private List<Photo> photoList = null;

        public List<Photo> getPhotoList() {
            return photoList;
        }
    }

    public class Photo{
        private String id;
        private String secret;
        private String server;
        private int farm;
        private String title;
        private String owner;
        private int ispublic;
        private int isfriend;
        private int isfamily;

        public String generateUrl(){
            return "https://farm" + farm + ".staticflickr.com/" + server + "/" +
                    id + "_" + secret + ".jpg";
        }

        public String getTitle(){
            return title;
        }
    }
}