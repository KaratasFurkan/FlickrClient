package com.karatas.furkan.flickrclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder> {
    private List<FlickrResponse.Photo> photos;
    private Context context;
    private View.OnClickListener onItemClickListener;

    public RecyclerViewAdapter(List<FlickrResponse.Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView photoTitle;
        public ImageViewHolder(View v) {
            super(v);
            photo = v.findViewById(R.id.photo);
            photoTitle = v.findViewById(R.id.photo_title);
            v.setTag(this);
            v.setOnClickListener(onItemClickListener);
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }


    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Glide
                .with(context)
                .load(photos.get(position).generateUrl())
                .thumbnail(Glide.with(context).load(R.drawable.placeholder))
                .into(holder.photo);

        holder.photoTitle.setText(photos.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }
}
