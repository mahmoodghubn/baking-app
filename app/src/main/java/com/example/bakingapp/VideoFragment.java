package com.example.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.data.Recipe;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoFragment extends Fragment {
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private int position;
    private long playbackPosition = 0;
    private Recipe recipe;
    private TextView detailTextView;

    public VideoFragment() {

    }

    VideoFragment(int position, Recipe recipe ) {
        this.position = position;
        this.recipe = recipe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable("recipe");
            currentWindow = savedInstanceState.getInt("currentWindow");
            playbackPosition = savedInstanceState.getLong("playback");
            playWhenReady = savedInstanceState.getBoolean("play");
        }
        View rootView = inflater.inflate(R.layout.video_fragment, container, false);
        mPlayerView = rootView.findViewById(R.id.exoplayer);
        detailTextView = rootView.findViewById(R.id.detail_text_view);
        detailTextView.setText(recipe.getSteps().get(position).getRecipeDescription());
        initializePlayer();
        return rootView;
    }


    private void initializePlayer() {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            MediaSource mediaSource = buildMediaSource();

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(currentWindow, playbackPosition);
            mExoPlayer.addListener(new PlayerEventListener());
        }
    }

    private MediaSource buildMediaSource() {

        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), "exoplayer-codelab");
        ProgressiveMediaSource.Factory mediaSourceFactory =
                new ProgressiveMediaSource.Factory(dataSourceFactory);

        ConcatenatingMediaSource mediaSource = new ConcatenatingMediaSource();
        for (int i = 0; i < recipe.getSteps().size(); i++) {

            String video = recipe.getSteps().get(i).getVideoURL();
            if (!video.equals("")) {
                Uri uri1 = Uri.parse(video).buildUpon()
                        .build();
                mediaSource.addMediaSource(mediaSourceFactory.createMediaSource(uri1));
                if (i<position){
                    currentWindow++;
                }
            }
        }

        return mediaSource;
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            playWhenReady = mExoPlayer.getPlayWhenReady();
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private class PlayerEventListener implements Player.EventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED ||
                    !playWhenReady) {

                mPlayerView.setKeepScreenOn(false);
            } else {
                // This prevents the screen from getting dim/lock
                mPlayerView.setKeepScreenOn(true);
            }
        }

        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            mExoPlayer.setPlayWhenReady(playWhenReady);
            playWhenReady = false;

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
        }

        @Override
        public void onPositionDiscontinuity(int reason) {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable("recipe", recipe);
        currentState.putInt("currentWindow", currentWindow);
        currentState.putBoolean("play", playWhenReady);
        currentState.putLong("playback", playbackPosition);
    }
}
