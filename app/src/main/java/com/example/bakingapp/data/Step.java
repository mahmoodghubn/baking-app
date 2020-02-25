package com.example.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable
{

    @SerializedName("id")
    private String ID;

    public String getVideoURL() {
        return videoURL;
    }

    @SerializedName("shortDescription")
    private String shortDescription;

    public String getRecipeDescription() {
        return recipeDescription;
    }

    @SerializedName("description")
    private String recipeDescription;

    @SerializedName("videoURL")
    private String videoURL;

    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    private Step(Parcel in)
    {
        ID = in.readString();
        shortDescription = in.readString();
        recipeDescription = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>()
    {
        @Override
        public Step createFromParcel(Parcel in)
        {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size)
        {
            return new Step[size];
        }
    };


    @Override
    public int describeContents()
    {
        return 0;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(ID);
        out.writeString(shortDescription);
        out.writeString(recipeDescription);
        out.writeString(videoURL);
        out.writeString(thumbnailURL);
    }





}
