package com.example.bakingapp.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import com.example.bakingapp.data.Recipe;

public interface JSONInterface
{
    String BASE_URL = "https:d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();

}


























