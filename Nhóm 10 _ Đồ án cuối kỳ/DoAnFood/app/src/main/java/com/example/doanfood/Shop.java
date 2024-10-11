package com.example.doanfood;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Shop implements Parcelable {
    int id;
    String name;
    String address;
    String number;
    String category;
    Date open, close;
    byte[] image;
    byte[] imageMap;

    public Shop(){}

    public Shop(int id, String name, String address, String number, String category, Date open, Date close, byte[] image, byte[] imageMap) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.number = number;
        this.category = category;
        this.open = open;
        this.close = close;
        this.image = image;
        this.imageMap = imageMap;
    }

    protected Shop(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        number = in.readString();
        category = in.readString();
        image = in.readBlob();
        imageMap = in.readBlob();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setOpen(Date open) {
        this.open = open;
    }

    public void setClose(Date close) {
        this.close = close;
    }

    public Date getOpen() {
        return open;
    }

    public Date getClose() {
        return close;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImageMap() {
        return imageMap;
    }

    public void setImageMap(byte[] imageMap) {
        this.imageMap = imageMap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(number);
        dest.writeString(category);
        dest.writeBlob(image);
        dest.writeBlob(imageMap);
    }
}
