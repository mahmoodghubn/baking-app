package com.example.bakingapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bakingapp.data.Step;

import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private Context context;
    private ArrayList<Step> mStepsData = new ArrayList<>();

    private final StepAdapterOnClickHandler mClickHandler;

    public interface StepAdapterOnClickHandler {
        void onClickStep(Step stepDate, int stepPosition);
    }

    StepAdapter(StepAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mStepTextView;
        final TextView mDetailTextView;
        final ImageButton mStepDetailImageButton;
        final ViewGroup stepItemContainer;

        StepAdapterViewHolder(View view) {
            super(view);
            mStepTextView = view.findViewById(R.id.step_text_view);
            mDetailTextView = view.findViewById(R.id.detail_text_view);
            mStepDetailImageButton = view.findViewById(R.id.show_detail_button);
            stepItemContainer = view.findViewById(R.id.step_item_container);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Step oneStep = mStepsData.get(adapterPosition);
            int position = 0;
            for (Step step : mStepsData) {
                if (!step.getVideoURL().equals("")) {
                    if (step.getVideoURL().equals(oneStep.getVideoURL())) {
                        break;
                    }
                    position++;
                }
            }
            mClickHandler.onClickStep(oneStep, position);
        }
    }

    @NonNull
    @Override
    public StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StepAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final StepAdapterViewHolder filmAdapterViewHolder, final int position) {


        filmAdapterViewHolder.mStepTextView.setText(mStepsData.get(position).getShortDescription());
        filmAdapterViewHolder.mDetailTextView.setText(mStepsData.get(position).getRecipeDescription());
        filmAdapterViewHolder.mStepDetailImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(filmAdapterViewHolder.stepItemContainer);
                if (filmAdapterViewHolder.mDetailTextView.getVisibility() == View.GONE) {
                    filmAdapterViewHolder.mDetailTextView.setVisibility(View.VISIBLE);
                }else {
                    filmAdapterViewHolder.mDetailTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mStepsData) return 0;
        return mStepsData.size();
    }

    void setStepData(ArrayList<Step> data) {

        if (data == null) {
            mStepsData.clear();
        } else {
            mStepsData = data;
        }
        notifyDataSetChanged();
    }

}
