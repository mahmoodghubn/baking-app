package com.example.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Ingredient base class
 * will store all of the attributes that the ingredient has to offer within the json file
 */
public class Ingredient implements Parcelable
{
    //quantity of that ingredient
    @SerializedName("quantity")
    private double quantity;
    //the way it is being measured
    @SerializedName("measure")
    private String measure;
    //the ingredient name
    @SerializedName("ingredient")
    private String ingredient;


    private Ingredient(Parcel in)
    {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>()
    {
        @Override
        public Ingredient createFromParcel(Parcel in)
        {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size)
        {
            return new Ingredient[size];
        }
    };



    public String getIngredient()
    {
        return ingredient;
    }



    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeDouble(quantity);
        out.writeString(measure);
        out.writeString(ingredient);
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }
}
