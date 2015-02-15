package com.example.marinamurashev.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.marinamurashev.gridimagesearch.R;
import com.example.marinamurashev.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.marinamurashev.gridimagesearch.models.ImageResult;
import com.example.marinamurashev.gridimagesearch.models.Setting;
import com.example.marinamurashev.gridimagesearch.services.GoogleImageSearchService;
import com.example.marinamurashev.gridimagesearch.utilities.EndlessScrollListener;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {
    private Setting setting = new Setting();
    private EditText etQuery;
    private GridView gvResults;
    
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    
    public static final String IMAGE_RESULT_EXTRA = "image result";
    public static final String SETTING_EXTRA = "setting";
    private final int SETTINGS_REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        displayActionBarIcon();
        setupViews();
        
        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
        
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                
                ImageResult result = imageResults.get(position);
                i.putExtra(IMAGE_RESULT_EXTRA, result);
                
                startActivity(i);
            }
        });
        
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount); 
            }
        });
    }

    private void customLoadMoreDataFromApi(int page) {
        Toast.makeText(this, String.valueOf(page), Toast.LENGTH_LONG).show();
    }

    private void displayActionBarIcon() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            i.putExtra(SETTING_EXTRA, setting);
            startActivityForResult(i, SETTINGS_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onImageSearch(View view) {
        String query = etQuery.getText().toString();
        GoogleImageSearchService service = new GoogleImageSearchService(aImageResults, setting, query);
        service.getImages();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SETTINGS_REQUEST_CODE) {
            setting = (Setting) data.getExtras().getSerializable(SettingsActivity.SETTING_EXTRA);
        }
    }
}
