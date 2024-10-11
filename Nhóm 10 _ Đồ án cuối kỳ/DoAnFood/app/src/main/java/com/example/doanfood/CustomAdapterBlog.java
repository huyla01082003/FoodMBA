
package com.example.doanfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class CustomAdapterBlog extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Blog>lsData = new ArrayList<>();

    public CustomAdapterBlog(@NonNull Context context, int resource, ArrayList<Blog>lsData) {
        super(context, resource, lsData);
        this.context=context;
        this.resource=resource;
        this.lsData=lsData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Blog blog = lsData.get(position);
        if(convertView==null)
            convertView= LayoutInflater.from(context).inflate(resource,null);

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_username);
        tvName.setText(blog.getbName());
        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
        tvContent.setText(blog.getbContent());

        return convertView;

    }
}