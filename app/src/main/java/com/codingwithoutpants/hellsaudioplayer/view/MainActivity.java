package com.codingwithoutpants.hellsaudioplayer.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.codingwithoutpants.hellsaudioplayer.R;
import com.codingwithoutpants.util.WaitSplash;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Constructor;

/**
 * Main Activity.  All others bow down before this one.
 */
public class MainActivity extends AppCompatActivity {

    final private WaitSplash _waitSplash = new WaitSplash();

    /**
     * Called when the activity is to be created.
     * Initializes display
     * @param savedInstanceState Bundle: if the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). Note: Otherwise
     *                           it is null. This value may be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.root);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* CHRIS REMOVE
        View bottomSheet = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);*/

        /* CHRIS REMOVE
        // initialize bottom sheet behavior
        behavior.setHideable(true);
        behavior.setDraggable(false);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);*/

        TabLayout tabs = findViewById(R.id.tabs);
        ViewPager2 topViewPager = findViewById(R.id.topViewPager);
        MainStateAdapter pageAdapter = new MainStateAdapter(
                _waitSplash,
                getSupportFragmentManager(),
                getLifecycle());
        topViewPager.setAdapter(pageAdapter);

        new TabLayoutMediator(tabs, topViewPager,
                (tab, position) -> tab.setText(pageAdapter.getTitleResId(position))).attach();

        toolbar.setOnMenuItemClickListener(item -> {

            runOnUiThread(()->{
                _waitSplash.show(getSupportFragmentManager(), "WSplash");
                new Thread(()->{
                    try{Thread.sleep(3000);} catch(Exception ex){}
                    runOnUiThread(()->_waitSplash.dismiss());
                }).start();
            });

            /* CHRIS REMOVE
            try {
                int newState = behavior.getState() == BottomSheetBehavior.STATE_HIDDEN ?
                        BottomSheetBehavior.STATE_COLLAPSED : BottomSheetBehavior.STATE_HIDDEN;
                behavior.setState(newState);
            }
            catch(Exception ex){
                int i = 0;
            }
             */
            return true;
        });

        // resize scrollview to accommodate player controls
        View playerPanel = findViewById(R.id.playerPanel);
        View topScrollView = findViewById(R.id.topScrollView);
        root.post(()->{
            int playerPanelHeight = playerPanel.getMeasuredHeight();
            CoordinatorLayout.LayoutParams sclp =
                    (CoordinatorLayout.LayoutParams) topScrollView.getLayoutParams();
            sclp.bottomMargin = playerPanelHeight;
            topScrollView.setLayoutParams(sclp);
        });
    }// end onCreate()

    /**
     * Called when it's time to attach menu options to the action bar.
     * @param menu menu to attach to
     * @return true if successful; false otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    protected static class TabInfo {
        int _titleResId;
        Class<? extends Fragment> _fragmentClass;

        public TabInfo(int titleResId, @NonNull Class<? extends Fragment> fragmentClass) {
            _titleResId = titleResId;
            _fragmentClass = fragmentClass;
        }

        public int getTitleResId() { return _titleResId; }

        @NonNull
        public Class<? extends Fragment> getFragmentClass() { return _fragmentClass; }
    }// end class TabInfo

    protected static class MainStateAdapter extends FragmentStateAdapter {
        final private TabInfo[] _tabInfo = {
                new TabInfo(R.string.albums_label, AlbumListFragment.class),
                new TabInfo(R.string.tracks_label, TrackListFragment.class)
        };

        private final WaitSplash _waitSplash;

        /**
         * Constructor
         * @param waitSplash WaitSplash object
         * @param fragmentManager FragmentManager: of ViewPager2's host
         * @param lifecycle Lifecycle: of ViewPager2's host
         */
        public MainStateAdapter(
                @NonNull WaitSplash waitSplash,
                @NonNull FragmentManager fragmentManager,
                @NonNull Lifecycle lifecycle
        ) {
            super(fragmentManager, lifecycle);
            _waitSplash = waitSplash;
        }

        public int getTitleResId(int position) {
            return _tabInfo[position].getTitleResId();
        }

        /**
         * Provide a new Fragment associated with the specified position.
         * <p>
         * The adapter will be responsible for the Fragment lifecycle:
         * <ul>
         *     <li>The Fragment will be used to display an item.</li>
         *     <li>The Fragment will be destroyed when it gets too far from the viewport, and its state
         *     will be saved. When the item is close to the viewport again, a new Fragment will be
         *     requested, and a previously saved state will be used to initialize it.
         * </ul>
         *
         * @param position position, indexed at 0
         * @see ViewPager2#setOffscreenPageLimit
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment retVal = null;
            try {
                Constructor<? extends Fragment> constructor = _tabInfo[position].getFragmentClass()
                        .getConstructor(WaitSplash.class);
                retVal = constructor.newInstance(_waitSplash);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (retVal == null) {
                retVal = new Fragment();
            }
            return retVal;
        }// end createFragment()

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return _tabInfo.length;
        }
    }// end class MainStateAdapter
}// end class MainActivity