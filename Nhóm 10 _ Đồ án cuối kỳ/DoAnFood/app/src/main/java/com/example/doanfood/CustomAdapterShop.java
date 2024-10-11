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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomAdapterShop extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Shop>lsData = new ArrayList<>();

    public CustomAdapterShop(@NonNull Context context, int resource, ArrayList<Shop>lsData) {
        super(context, resource, lsData);
        this.context=context;
        this.resource=resource;
        this.lsData=lsData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Shop shop = lsData.get(position);
        if(convertView==null)
            convertView= LayoutInflater.from(context).inflate(resource,null);

        ImageView imgShop = (ImageView) convertView.findViewById(R.id.home_item_image);
        byte[] imgdata = shop.getImage();
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length);
        imgShop.setImageBitmap(imageBitmap);

        TextView tvName = (TextView) convertView.findViewById(R.id.home_item_name);
        tvName.setText(shop.getName());

        TextView tvAddress = (TextView) convertView.findViewById(R.id.home_item_address);
        tvAddress.setText(shop.getAddress());

        return convertView;

    }
}