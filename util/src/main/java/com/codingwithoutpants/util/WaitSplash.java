package com.codingwithoutpants.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Modal wait splash
 */
public class WaitSplash {
    final private WSDlg _innerDlg = new WSDlg();
    final private Object _waitLock = new Object();
    private int _numWaiting;
    private boolean _isVisible;

    /**
     * Constructor
     */
    public WaitSplash() {
        _innerDlg.setCancelable(false);
    }

    /**
     * Display the wait splash
     * @param fm fragment manager
     * @param tag tag
     */
    public void show(@NonNull FragmentManager fm, @NonNull String tag) {
        synchronized (_waitLock) {
            if (!_isVisible) {
                _innerDlg.show(fm, tag);
                _isVisible = true;
            }
            ++_numWaiting;
        }
    }

    /**
     * Dismiss the wait splash.
     */
    public void dismiss() {
        synchronized (_waitLock) {
            --_numWaiting;
            if (_numWaiting < 0) {
                _numWaiting = 0;
            }

            if (_numWaiting < 1) {
                _isVisible = false;
                _innerDlg.dismiss();
            }
        }
    }

    /**
     * Inner dialog class that handles layout details.  Needs to be public for Android system to properly instantiate at runtime,
     * but developers should not need to use this class directly.
     * Recommended to use {@link #WaitSplash} class instead.
     */
    public static class WSDlg extends AppCompatDialogFragment {
        /**
         * Called to have the fragment instantiate its user interface view.
         * This is optional, and non-graphical fragments can return null. This will be called between
         * {@link #onCreate(Bundle)} and {@link #onViewCreated(View, Bundle)}.
         *
         * <p>It is recommended to <strong>only</strong> inflate the layout in this method and move
         * logic that operates on the returned View to {@link #onViewCreated(View, Bundle)}.
         *
         * <p>If you return a View from here, you will later be called in
         * {@link #onDestroyView} when the view is being released.
         *
         * @param inflater           The LayoutInflater object that can be used to inflate
         *                           any views in the fragment,
         * @param container          If non-null, this is the parent view that the fragment's
         *                           UI should be attached to.  The fragment should not add the view itself,
         *                           but this can be used to generate the LayoutParams of the view.
         * @param savedInstanceState If non-null, this fragment is being re-constructed
         *                           from a previous saved state as given here.
         * @return Return the View for the fragment's UI, or null.
         */
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_wait, container, false);
        }
    }
}
