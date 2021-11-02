package com.codingwithoutpants.hellsaudioplayer.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codingwithoutpants.hellsaudioplayer.R;
import com.codingwithoutpants.util.WaitSplash;

public class TrackListFragment extends MediaFragment {
    /**
     * Constructor
     * @param waitSplash WaitSplash object
     */
    public TrackListFragment(@NonNull WaitSplash waitSplash) {
        super(waitSplash);
    }

    /**
     * Create a view from the layout resource
     * @param inflater The LayoutInflater object that can be used to inflate any views in the
     *                 fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be
     *                  attached to. The fragment should not add the view itself, but this can be
     *                  used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @MainThread
    @Override
    @NonNull
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View retVal = inflater.inflate(R.layout.fragment_tracks, container);
        return retVal;
    }// end onCreateView()

    /**
     * Load data from storage into the display.
     * By the time this function is called, the display components have been set up.
     * This method is not guaranteed to be running in the UI thread when it is called.
     */
    @Override
    protected void loadPermittedData() {
        Log.d("TRK", "Loading data.");
    }
}
