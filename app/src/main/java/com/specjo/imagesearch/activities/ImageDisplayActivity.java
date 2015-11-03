package com.specjo.imagesearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.specjo.imagesearch.R;
import com.specjo.imagesearch.models.ImageResult;
import com.specjo.imagesearch.views.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        getSupportActionBar().hide();

        ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("result");

        final TouchImageView ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
        Picasso.with(this).load(imageResult.fullUrl).into(ivImageResult, new Callback() {
            @Override
            public void onSuccess() {
                ivImageResult.setZoom(1.0f); // Image doesn't seem to show up without this.
            }

            @Override
            public void onError() {
                Toast.makeText(ImageDisplayActivity.this, "Could not retrieve image :(", Toast.LENGTH_LONG).show();
            }
        });
    }
}
