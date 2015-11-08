package ro.hacktm.smarthome;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.onyxbeacon.OnyxBeaconApplication;
import com.onyxbeacon.OnyxBeaconManager;
import com.onyxbeacon.listeners.OnyxBeaconsListener;
import com.onyxbeaconservice.IBeacon;

import java.util.ArrayList;
import java.util.List;

import ro.hacktm.smarthome.common.SlidingTabLayout;


public class MainActivity extends ActionBarActivity implements OnyxBeaconsListener {

    ActionBar actionBar;
    SlidingTabLayout mSlidingTabLayout;
    ViewPager mViewPager;

    private ContentReceiver mContentReceiver;
    //Define OnyxBeaconManager object as class member:
    private OnyxBeaconManager manager;

    List<IBeacon> lista = new ArrayList<>();
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mContentReceiver);
        manager.setForegroundMode(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", lista + "");
        manager = OnyxBeaconApplication.getOnyxBeaconManager(this);
        manager.setForegroundMode(true);
        registerReceiver(mContentReceiver, new IntentFilter("ro.hacktm.smarthome.content"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        Context context = this.getApplicationContext(); // if you are working with an activity

        manager = OnyxBeaconApplication.getOnyxBeaconManager(context);
        if (manager.isBluetoothAvailable()) {
           // mManager.setCouponEnabled(true);
            manager.setAPIContentEnabled(true);
        } else {
            manager.enableBluetooth();
        }*/


        mContentReceiver = ContentReceiver.getInstance();
        mContentReceiver.setOnyxBeaconsListener(this);
        //manager.setForegroundMode(true);

        // Set up the action bar.
        actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(false);
        // Specify that the Home/Up button should not be enabled, since there is
        // no hierarchical
        // parent.
       // actionBar.setHomeButtonEnabled(false);
        actionBar.setElevation(0);
        // Specify that we will be displaying tabs in the action bar.
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        actionBar.setCustomView(R.layout.header);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.statusBarGreen));
        }
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        AppSectionsPagerAdapter cpa = new AppSectionsPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        mViewPager.setAdapter(cpa);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs_layout);
        mSlidingTabLayout.setViewPager(mViewPager);
        //setting indicator and divider color
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(android.R.color.white);    //define any color in xml resources and set it here, I have used white
            }

            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(R.color.green);
            }
        });
/*        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    // Hide the keyboard.
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);
                }
            }
        });*/


    }

    @Override
    public void didRangeBeaconsInRegion(List<IBeacon> list) {
        //lista.addAll(list);
        Log.i("MainActivity", list + "");
    }

    @Override
    public void onError(int i, Exception e) {

    }

/*    @Override
    public void didRangeBeaconsInRegion(List<IBeacon> list) {
        System.out.println("aaaaaaaaaaaaaa");
    }

    @Override
    public void onError(int i, Exception e) {
        System.out.println("aaaaaaaaaaaaaa");
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the primary sections of the app.
     */
    class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        Context context;
        int nr_taburi = 2;
        String[] taburi = new String[]{"Usi", "Camera"};

        public AppSectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }


        @Override
        public android.support.v4.app.Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        // The first section of the app is the most interesting -- it
                        // offers
                        // a launchpad into the other demonstrations in this example
                        // application.
                        return new UsiFragment();
                    case 1:
                        return new CasaFragment();
                    default:
                        // The other sections of the app are dummy placeholders.
                        android.support.v4.app.Fragment fragment = new CasaFragment();
                        return fragment;
                }
           /* else
                    if (nr_taburi == 3){
                        switch (i) {
                            case 0:
                                // The first section of the app is the most interesting -- it
                                // offers
                                // a launchpad into the other demonstrations in this example
                                // application.
                                return new MainAmendaCirculatie();
                            case 1:
                                return new MainAmendaParcare();
                            default:
                                // The other sections of the app are dummy placeholders.
                                android.support.v4.app.Fragment fragment = new MainSms();
                                return fragment;
                        }
                    }*/
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return taburi[position];
        }


        @Override
        public int getCount() {
            return nr_taburi;
        }
    }
}
