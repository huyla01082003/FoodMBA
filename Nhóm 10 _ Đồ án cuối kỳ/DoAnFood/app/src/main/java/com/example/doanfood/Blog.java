package com.example.doanfood;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Blog implements Parcelable {
    public int bId;
    public String bContent, bName;

    public Blog(){}

    public Blog(int bId, String bName, String bContent) {
        this.bId = bId;
        this.bName = bName;
        this.bContent = bContent;
    }

    protected Blog(Parcel in) {
        bId = in.readInt();
        bName = in.readString();
        bContent = in.readString();
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

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbContent() {
        return bContent;
    }

    public void setbContent(String bContent) {
        this.bContent = bContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(bId);
        parcel.writeString(bName);
        parcel.writeString(bContent);
    }
}
