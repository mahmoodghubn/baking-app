package com.example.bakingapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.data.Ingredient;

import java.text.MessageFormat;
import java.util.ArrayList;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.DetailAdapterViewHolder> {

    private Context context;
    private ArrayList<Ingredient> mIngredientsData = new ArrayList<>();

    private final IngredientAdapterOnClickHandler mClickHandler;

    public interface IngredientAdapterOnClickHandler {
        void onClickIngredient(Ingredient ingredientDate);
    }

    IngredientAdapter(IngredientAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class DetailAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mIngredientTextView;
        final TextView mMeasurementTextView;

        DetailAdapterViewHolder(View view) {
            super(view);
            mIngredientTextView = view.findViewById(R.id.ingredient_text_view);
            mMeasurementTextView = view.findViewById(R.id.show_detail_button);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Ingredient oneIngredient = mIngredientsData.get(adapterPosition);
            mClickHandler.onClickIngredient(oneIngredient);
        }
    }

    @NonNull
    @Override
    public DetailAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new DetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailAdapterViewHolder filmAdapterViewHolder, final int position) {


        filmAdapterViewHolder.mIngredientTextView.setText(mIngredientsData.get(position).getIngredient());
        filmAdapterViewHolder.mMeasurementTextView.setText(MessageFormat.format("{0}{1}", mIngredientsData.get(position).getQuantity(), mIngredientsData.get(position).getMeasure()));

    }

    @Override
    public int getItemCount() {
        if (null == mIngredientsData) return 0;
        return mIngredientsData.size();
    }

    void setIngredientData(ArrayList<Ingredient> data) {

        if (data == null) {
            mIngredientsData.clear();
        } else {
            mIngredientsData.addAll(data);
        }
        notifyDataSetChanged();
    }

}
