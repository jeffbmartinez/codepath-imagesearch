package com.specjo.imagesearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.specjo.imagesearch.R;
import com.specjo.imagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        getSupportActionBar().hide();

        ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("result");

        ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
        Picasso.with(this).load(imageResult.fullUrl).into(ivImageResult);
    }
}
