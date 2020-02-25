package com.example.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Recipe base class
 * will store all of the attributes that the recipe has to offer within the json file
 */
public class Recipe implements Parcelable {
    //recipe id
    @SerializedName("id")
    private int Id;

    //recipe name
    @SerializedName("name")
    private String name;

    //recipe ingredients,will map an array of ingredients
    @SerializedName("ingredients")
    private ArrayList<Ingredient> ingredients;

    public ArrayList<Step> getSteps() {
        return steps;
    }

    //recipe steps,will map an array of steps to make the recipe
    @SerializedName("steps")
    private ArrayList<Step> steps;

    //number of servings
    @SerializedName("servings")
    private int serving;


    private Recipe(Parcel in) {
        Id = in.readInt();
        name = in.readString();
        serving = in.readInt();
        ingredients= new ArrayList<>();
        Ingredient[] ingredientArray= in.createTypedArray(Ingredient.CREATOR);
        Collections.addAll(ingredients,ingredientArray);
        steps= new ArrayList<>();
        Step[] stepArray= in.createTypedArray(Step.CREATOR);
        Collections.addAll(steps,stepArray);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(Id);
        out.writeString(name);
        out.writeInt(serving);
        Ingredient[] ingredientArray = new Ingredient[ingredients.size()];
        int i=0;
        for (Ingredient ing:ingredients){
            ingredientArray[i] = ing;
            i++;
        }
        out.writeTypedArray(ingredientArray, 0);
        Step[] stepArray = new Step[steps.size()];
        i=0;
        for (Step ing:steps){
            stepArray[i] = ing;
            i++;
        }
        out.writeTypedArray(stepArray, 1);
    }
}
