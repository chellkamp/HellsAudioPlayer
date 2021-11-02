package com.codingwithoutpants.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * Currently just made for FrameLayout
 */
public class WaitSplash {

    private final Activity _activity;
    private final ViewGroup _vg;
    private final View _splashContainer;

    private int _waitCount;

    private final Object _splashCLock = new Object();

    /**
     * Constructor.  Call from UI thread.
     * @param vg viewgroup that visual components will be attached to
     */
    public WaitSplash(@NonNull Activity activity, @NonNull ViewGroup vg) {
        synchronized (_splashCLock) {
            _activity = activity;
            _vg = vg;
            LayoutInflater inf = LayoutInflater.from(vg.getContext());
            _splashContainer = inf.inflate(R.layout.dialog_wait, _vg, false);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            _vg.addView(_splashContainer, lp);
            _splashContainer.setVisibility(View.GONE);
        }
    }// end constructor

    public void startWait() {
        _activity.runOnUiThread(this::runStartWait);
    }

    private void runStartWait() {
        synchronized (_splashCLock) {
            ++_waitCount;
            _splashContainer.setVisibility(View.VISIBLE);
        }
    }

    public void stopWait() {
        _activity.runOnUiThread(this::runStopWait);
    }

    private void runStopWait() {
        synchronized (_splashCLock) {
            --_waitCount;
            if (_waitCount < 0) {
                _waitCount = 0;
            }
            _splashContainer.setVisibility(View.GONE);
        }
    }
}
