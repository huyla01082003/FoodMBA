package com.example.doanfood;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Comments implements Parcelable {
    public int sId, cId;
    public String cContent;

    public Comments(){}

    public Comments(int sId, int cId, String cContent) {
        this.sId = sId;
        this.cId = cId;
        this.cContent = cContent;
    }

    protected Comments(Parcel in) {
        sId = in.readInt();
        cId = in.readInt();
        cContent = in.readString();
    }

    public static final Creator<Comments> CREATOR = new Creator<Comments>() {
        @Override
        public Comments createFromParcel(Parcel in) {
            return new Comments(in);
        }

        @Override
        public Comments[] newArray(int size) {
            return new Comments[size];
        }
    };

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(sId);
        parcel.writeInt(cId);
        parcel.writeString(cContent);
    }
}
