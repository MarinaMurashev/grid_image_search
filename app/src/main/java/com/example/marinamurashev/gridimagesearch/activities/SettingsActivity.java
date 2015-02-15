package com.example.marinamurashev.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

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
        updateViewBasedOnSetting();
    }

    private void updateViewBasedOnSetting() {
        setSpinnerToValue(sImageSize, setting.getSize());
        setSpinnerToValue(sImageColor, setting.getColor());
        setSpinnerToValue(sImageType, setting.getType());
        etImageSite.setText(setting.getSite());
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
        setArrayAdapterFor(sImageSize, R.array.image_size_array);
        
        sImageColor = (Spinner) findViewById(R.id.sImageColor);
        setArrayAdapterFor(sImageColor, R.array.image_color_array);
        
        sImageType = (Spinner) findViewById(R.id.sImageType);
        setArrayAdapterFor(sImageType, R.array.image_type_array);
        
        etImageSite = (EditText) findViewById(R.id.etImageSite);

        TextView tvSettingsTitle = (TextView) findViewById(R.id.tvSettingsTitle);
        tvSettingsTitle.setText(Html.fromHtml("<b>" + getString(R.string.settings_title) + "</b>"));
    }
    
    private void setArrayAdapterFor(Spinner spinner, int arrayResource){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResource, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);
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

    public void setSpinnerToValue(Spinner spinner, String value) {
        if(value != null) {
            int index = 0;
            SpinnerAdapter adapter = spinner.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).equals(value)) {
                    index = i;
                    break; // terminate loop
                }
            }
            spinner.setSelection(index);
        }
    }
}
