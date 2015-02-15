package com.example.marinamurashev.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.marinamurashev.gridimagesearch.R;
import com.example.marinamurashev.gridimagesearch.models.Setting;

public class SettingsActivity extends ActionBarActivity {
    private Setting setting;
    private Spinner sImageSize;
    private Spinner sImageColor;
    private Spinner sImageType;
    private EditText etImageSite;
    
    public static final String SETTING_EXTRA = "setting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupViews();
        setting = (Setting) getIntent().getSerializableExtra(SearchActivity.SETTING_EXTRA);
    }

    public void onSubmit(View v) {
        setSettingFromView();
        
        Intent i = new Intent();
        i.putExtra(SETTING_EXTRA, setting);
        
        setResult(RESULT_OK, i);
        finish(); 
    }
    
    private void setupViews(){
        sImageSize = (Spinner) findViewById(R.id.sImageSize);
        sImageColor = (Spinner) findViewById(R.id.sImageColor);
        sImageType = (Spinner) findViewById(R.id.sImageType);
        etImageSite = (EditText) findViewById(R.id.etImageSite);
    }
    
    private void setSettingFromView(){
        String no_filter = getResources().getString(R.string.no_filter);
        
        String size = sImageSize.getSelectedItem().toString();
        if(size != no_filter) setting.setSize(size);

        String color = sImageColor.getSelectedItem().toString();
        if(color != no_filter) setting.setColor(color);
        
        String type = sImageType.getSelectedItem().toString();
        if(type != no_filter) setting.setType(type);
        
        String site = etImageSite.getText().toString();
        if(site != null) setting.setSite(site);
    }
}
