package com.example.doanfood;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class CustomAdapterComment extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Comments>lsData = new ArrayList<>();

    public CustomAdapterComment(@NonNull Context context, int resource, ArrayList<Comments>lsData) {
        super(context, resource, lsData);
        this.context=context;
        this.resource=resource;
        this.lsData=lsData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Comments comment = lsData.get(position);
        if(convertView==null)
            convertView= LayoutInflater.from(context).inflate(resource,null);

        TextView tvContent = (TextView) convertView.findViewById(R.id.tvComment);
        tvContent.setText(comment.getcContent());

        return convertView;

    }
}