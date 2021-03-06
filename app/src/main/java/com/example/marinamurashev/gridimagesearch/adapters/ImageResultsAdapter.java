package com.example.marinamurashev.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
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
    
    private static class ViewHolder {
        TextView tvTitle;
        ImageView ivImage;
    }
    
    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageResult = getItem(position);

        ViewHolder viewHolder;
        
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_image_result, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.ivImage.setImageResource(0);
        viewHolder.tvTitle.setText(Html.fromHtml(imageResult.getTitle()));
        Picasso.with(getContext()).load(imageResult.getThumbUrl()).into(viewHolder.ivImage);
        
        return convertView;
    }
}
