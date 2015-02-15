package com.example.marinamurashev.gridimagesearch.services;


import android.util.Log;

import com.example.marinamurashev.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.marinamurashev.gridimagesearch.models.ImageResult;
import com.example.marinamurashev.gridimagesearch.models.Setting;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleImageSearchService {
    
    private static final String URL = "https://ajax.googleapis.com/ajax/services/search/images?";
    private static final String QUERY_PARAM_NAME = "q";
    private static final String COLOR_PARAM_NAME = "imgcolor";
    private static final String SIZE_PARAM_NAME = "imgsz";
    private static final String TYPE_PARAM_NAME = "imgtype";
    private static final String SITE_PARAM_NAME = "as_sitesearch";
    private static final String PAGE_PARAM_NAME = "start";
    private static final String VERSION_PARAM = "v=1.0";
    private static final String RETURN_NUMBER_PARAM_NAME = "rsz";

    private String fullUrl;
    private ImageResultsAdapter imageResultsAdapter;
    private Setting setting;
    private int offset = 0;
    private int resultSize = 8;
    
    public GoogleImageSearchService(ImageResultsAdapter adapter, Setting setting, String queryParamValue){
        this.imageResultsAdapter = adapter;
        this.setting = setting;
        this.fullUrl = buildFullUrl(queryParamValue);
        this.offset = 0;
        applySettingsToUrl();
    }

    public GoogleImageSearchService(ImageResultsAdapter adapter, Setting setting, String queryParamValue, int offset){
        this.imageResultsAdapter = adapter;
        this.setting = setting;
        this.offset = offset;
        this.fullUrl = buildFullUrl(queryParamValue);
        applySettingsToUrl();
    }
    
    private String buildFullUrl(String queryString){
        return URL
            + VERSION_PARAM + "&"
            + RETURN_NUMBER_PARAM_NAME + "=" + String.valueOf(resultSize) + "&"
            + QUERY_PARAM_NAME + "=" + queryString + "&"
            + PAGE_PARAM_NAME + "=" + String.valueOf(offset);
    }

    private void applySettingsToUrl() {
        if(setting.getColor() != null){
            fullUrl += "&" + COLOR_PARAM_NAME + "=" + setting.getColor();
        }
        if(setting.getSize() != null){
            fullUrl += "&" + SIZE_PARAM_NAME + "=" + setting.getSize();
        }
        if(setting.getType() != null){
            fullUrl += "&" + TYPE_PARAM_NAME + "=" + setting.getType();
        }
        if(setting.getSite() != null){
            fullUrl += "&" + SITE_PARAM_NAME + "=" + setting.getSite();
        }
    }

    public void getImages(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(fullUrl, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJSON = null;
                try {
                    imageResultsJSON = response.getJSONObject("responseData").getJSONArray("results");
                    if(offset == 0) imageResultsAdapter.clear();
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

