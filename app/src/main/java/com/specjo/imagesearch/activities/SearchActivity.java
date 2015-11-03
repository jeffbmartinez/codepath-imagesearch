package com.specjo.imagesearch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.specjo.imagesearch.listeners.EndlessScrollListener;
import com.specjo.imagesearch.models.ImageResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    private final static String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images";
    public static final int QUERY_PAGE_SIZE = 8;

    private EditText etQuery;
    private GridView gvResults;

    private List<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;

    private EndlessScrollListener endlessScrollListener;

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

        endlessScrollListener = new EndlessScrollListener() {

            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        };
        gvResults.setOnScrollListener(endlessScrollListener);
    }

    public void customLoadMoreDataFromApi(int offset) {
        if (offset >= QUERY_PAGE_SIZE) {
            Toast.makeText(this, "Google says no more images allowed :(", Toast.LENGTH_SHORT).show();
        } else {
            searchForPictures(offset, false);
        }
    }

    public void onImageSearch(View view) {
        endlessScrollListener.reset();
        searchForPictures(1, true);
    }

    private void searchForPictures(int page, final boolean clearExisting) {
        String query = etQuery.getText().toString();

        String searchUrl = buildQueryUrl(query);
        searchUrl += "&start=" + ((page - 1) * QUERY_PAGE_SIZE);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchUrl, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject responseData = response.getJSONObject("responseData");
                    JSONArray resultsJson = responseData.getJSONArray("results");

                    if (clearExisting) {
                        imageResults.clear();
                    }
                    aImageResults.addAll(ImageResult.fromJSONArray(resultsJson));
                } catch (JSONException e) {
                    Toast.makeText(SearchActivity.this, "Couldn't get images :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String buildQueryUrl(String query) {
        StringBuilder queryUrl = new StringBuilder(BASE_URL);
        queryUrl.append("?q=");
        queryUrl.append(query);

        queryUrl.append("&v=1.0");
        queryUrl.append("&rsz=" + QUERY_PAGE_SIZE);

        queryUrl.append(getSearchUrlPart("imgsz", FiltersActivity.SIZE, R.array.entries_image_size_preference));
        queryUrl.append(getSearchUrlPart("imgcolor", FiltersActivity.COLOR, R.array.entries_image_color_preference));
        queryUrl.append(getSearchUrlPart("imgtype", FiltersActivity.TYPE, R.array.entries_image_type_preference));
        queryUrl.append(getSearchUrlPart("as_filetype", FiltersActivity.FILETYPE, R.array.entries_image_filetype_preference));

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String savedSiteFilter = pref.getString(FiltersActivity.SITE_FILTER, "");
        if (!savedSiteFilter.isEmpty()) {
            queryUrl.append("&as_sitesearch=");
            queryUrl.append(savedSiteFilter);
        }

        return queryUrl.toString();
    }

    private String getSearchUrlPart(String urlKey, String preferenceKey, int arrayResource) {
        StringBuilder urlPart = new StringBuilder("");

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int savedValue = pref.getInt(preferenceKey, FiltersActivity.NO_FILTER);
        if (savedValue != FiltersActivity.NO_FILTER) {
            String[] values = getResources().getStringArray(arrayResource);

            urlPart.append("&");
            urlPart.append(urlKey);
            urlPart.append("=");
            urlPart.append(values[savedValue]);
        }

        return urlPart.toString();
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
        Intent showSettings = new Intent(this, FiltersActivity.class);
        startActivity(showSettings);
    }
}
