package com.codingwithoutpants.hellsaudioplayer.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.codingwithoutpants.hellsaudioplayer.R;
import com.codingwithoutpants.util.WaitSplash;

public abstract class MediaFragment extends Fragment {

    final private ActivityResultLauncher<String> permReqLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    (isGranted)->{
                        if (isGranted) {
                            loadPermittedData();
                        }
                    }
            );

    private final WaitSplash _waitSplash;

    /**
     * Constructor
     * @param waitSplash WaitSplash for use in blocking operations
     */
    public MediaFragment(@NonNull WaitSplash waitSplash) {
        super();
        _waitSplash = waitSplash;
    }

    /**
     * Gets reference to the wait splash.
     * @return WaitSplash object
     */
    protected @NonNull WaitSplash getWaitSplash() { return _waitSplash; }

    /**
     * Called when the Fragment is visible to the user.
     */
    @Override
    public void onStart() {
        super.onStart();

        initiateDataLoad();
    }

    /**
     * Call this method when you want to load the underlying data.
     * This method checks for necessary permissions to access files
     * before calling loadPermittedData()
     */
    protected void initiateDataLoad() {
        Context c = getActivity();

        if (c != null) {
            int permStatus = ContextCompat.checkSelfPermission(
                    c, Manifest.permission.READ_EXTERNAL_STORAGE
            );

            if (permStatus == PackageManager.PERMISSION_GRANTED) {
                loadPermittedData();
            } else {
                showNeedPermsDlg((dlg, id)->
                        permReqLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE));
            }
        }
    }

    private void showNeedPermsDlg(@NonNull DialogInterface.OnClickListener onOK) {
        FragmentActivity activity = getActivity();

        if (activity != null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.need_perms_msg)
                    .setPositiveButton(R.string.dlg_ok, onOK)
                    .create()
                    .show();
        }
    }

    /**
     * Load data from storage into the display.
     * By the time this function is called, the display components have been set up.
     * This method is not guaranteed to be running in the UI thread when it is called.
     */
    protected abstract void loadPermittedData();
}
