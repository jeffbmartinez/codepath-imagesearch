package com.specjo.imagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeff on 11/2/15.
 */
public class ImageResult implements Serializable {
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public int width;
    public int height;

    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
            this.width = json.getInt("width");
            this.height = json.getInt("height");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<ImageResult> fromJSONArray(JSONArray array) {
        List<ImageResult> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject imageJsonObject = array.getJSONObject(i);
                results.add(new ImageResult(imageJsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
