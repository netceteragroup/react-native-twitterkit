package com.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.view.WindowManager;

import com.facebook.react.ReactActivity;
import com.netcetera.reactnative.twitterkit.EventsHelper;

public class MainActivity extends ReactActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "example";
    }

    //to be added in main project start
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mTwitterVideoPlayerActivityDestroyedEvent);
        super.onPause();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mTwitterVideoPlayerActivityDestroyedEvent, new IntentFilter(EventsHelper.TWITTER_VIDEO_PLAYER_ACTIVITY_DESTROYED));
        super.onResume();
    }

    private BroadcastReceiver mTwitterVideoPlayerActivityDestroyedEvent = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            enterNormalScreenPortrait();
        }
    };

    private void enterNormalScreenPortrait() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    //to be added in main project end
}
