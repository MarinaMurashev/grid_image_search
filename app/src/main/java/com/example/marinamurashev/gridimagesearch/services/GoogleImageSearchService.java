package com.example.marinamurashev.gridimagesearch.services;


import android.util.Log;

import com.example.marinamurashev.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.marinamurashev.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleImageSearchService {
    
    private static final String URL = "https://ajax.googleapis.com/ajax/services/search/images?";
    private static final String QUERY_PARAM_NAME = "q";
    private static final String VERSION_PARAM = "v=1.0";
    private static final String RETURN_NUMBER_PARAM = "rsz=8";

    private String fullUrl;
    private ImageResultsAdapter imageResultsAdapter;
    
    public GoogleImageSearchService(ImageResultsAdapter adapter, String queryParamValue){
        this.imageResultsAdapter = adapter;
        this.fullUrl = URL
                + VERSION_PARAM + "&" 
                + RETURN_NUMBER_PARAM + "&" 
                + QUERY_PARAM_NAME + "=" + queryParamValue;
    }
    
    public void getImages(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(fullUrl, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJSON = null;
                try {
                    imageResultsJSON = response.getJSONObject("responseData").getJSONArray("results");
                    imageResultsAdapter.clear();
                    imageResultsAdapter.addAll(ImageResult.fromJSONArray(imageResultsJSON));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG", "Failed network response");
            }
        });
        
    }
}

