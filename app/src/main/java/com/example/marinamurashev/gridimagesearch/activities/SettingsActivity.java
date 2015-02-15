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
    
    public static final String SETTING_EXTRA = "setting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        setting = (Setting) getIntent().getSerializableExtra(SearchActivity.SETTING_EXTRA);
    }

    public void onSubmit(View v) {
        setSettingFromView();
        
        Intent i = new Intent();
        i.putExtra(SETTING_EXTRA, setting);
        
        setResult(RESULT_OK, i);
        finish(); 
    }
    
    private void setSettingFromView(){
        Spinner sImageSize = (Spinner) findViewById(R.id.sImageSize);
        setting.setSize(sImageSize.getSelectedItem().toString());
        Spinner sImageColor = (Spinner) findViewById(R.id.sImageColor);
        setting.setColor(sImageColor.getSelectedItem().toString());
        Spinner sImageType = (Spinner) findViewById(R.id.sImageType);
        setting.setType(sImageType.getSelectedItem().toString());
        EditText etImageSite = (EditText) findViewById(R.id.etImageSite);
        setting.setSite(etImageSite.getText().toString());
    }
}
