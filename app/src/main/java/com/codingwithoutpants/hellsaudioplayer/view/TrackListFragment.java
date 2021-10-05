package com.codingwithoutpants.hellsaudioplayer.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codingwithoutpants.hellsaudioplayer.R;

public class TrackListFragment extends Fragment {
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
}
