package com.karatas.furkan.flickrclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullScreenActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        Intent intent = getIntent();
        Glide
                .with(this)
                .load(intent.getStringExtra("url"))
                .thumbnail(Glide.with(this).load(R.drawable.placeholder))
                .into((ImageView) findViewById(R.id.full_screen_image));
    }
}
