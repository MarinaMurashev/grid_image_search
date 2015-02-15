package com.example.marinamurashev.gridimagesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marinamurashev.gridimagesearch.R;
import com.example.marinamurashev.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageResult = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        
        ivImage.setImageResource(0);
        tvTitle.setText(imageResult.getTitle());
        Picasso.with(getContext()).load(imageResult.getThumbUrl()).into(ivImage);
        
        return convertView;
    }
}
