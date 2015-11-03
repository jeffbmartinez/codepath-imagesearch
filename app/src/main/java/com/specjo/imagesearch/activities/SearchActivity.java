package com.specjo.imagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.specjo.imagesearch.R;
import com.specjo.imagesearch.adapters.ImageResultsAdapter;
import com.specjo.imagesearch.models.ImageResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    private EditText etQuery;
    private GridView gvResults;

    private List<ImageResult> imageResults;

    private ImageResultsAdapter aImageResults;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_filters, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
    }

    public void onImageSearch(View view) {
        String query = etQuery.getText().toString();
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();

        // Grab search filters from preferences
        // http://guides.codepath.com/android/Persisting-Data-to-the-Device#shared-preferences

        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchUrl, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject responseData = response.getJSONObject("responseData");
                    JSONArray resultsJson = responseData.getJSONArray("results");

                    imageResults.clear();
                    aImageResults.addAll(ImageResult.fromJSONArray(resultsJson));
                } catch (JSONException e) {
                    Toast.makeText(SearchActivity.this, "Couldn't get images :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showImageIntent = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                showImageIntent.putExtra("result", result);

                startActivity(showImageIntent);
            }
        });
    }

    public void onSettingsAction(MenuItem item) {
        Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();

        Intent showSettings = new Intent(this, FiltersActivity.class);
        startActivity(showSettings);
    }
}
