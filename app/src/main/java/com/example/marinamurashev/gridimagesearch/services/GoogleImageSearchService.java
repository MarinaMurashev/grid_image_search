package com.example.marinamurashev.gridimagesearch.services;


public class GoogleImageSearchService {
    
    private static final String URL = "https://ajax.googleapis.com/ajax/services/search/images?";
    private static final String QUERY_PARAM_NAME = "q";
    private static final String VERSION_PARAM = "v=1.0";
    private static final String RETURN_NUMBER_PARAM = "rsz=8";

    private String fullUrl;
    
    public GoogleImageSearchService(String queryParamValue){
        fullUrl = URL
                + VERSION_PARAM + "&" 
                + RETURN_NUMBER_PARAM + "&" 
                + QUERY_PARAM_NAME + "=" + queryParamValue;
    }


    public String getFullUrl() {
        return fullUrl;
    }
}

