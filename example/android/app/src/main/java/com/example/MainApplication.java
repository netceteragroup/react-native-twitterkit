package com.example;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.netcetera.reactnative.twitterkit.EventsHelper;
import com.netcetera.reactnative.twitterkit.ReactTwitterKitPackage;
import com.twitter.sdk.android.tweetui.PlayerActivity;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication, Application.ActivityLifecycleCallbacks {

    private static final String TAG = MainApplication.class.getCanonicalName();

    // set your keys here!

    private static final String CONSUMER_KEY = "ZJOFGUJEzg1VG25lss7hyo5vK";
    private static final String CONSUMER_SECRET = "bj6ywtDPaRV5M90s9Z6CK07Uc2qZYtzSTXWrQrMHdwBUYNQ5k6";

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new ReactTwitterKitPackage(CONSUMER_KEY, CONSUMER_SECRET)
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
        registerActivityLifecycleCallbacks(this);
    }

    //to be added in main project start
    //add registerActivityLifecycleCallbacks in onCreate
    //ActivityLifecycleCallbacks start
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //for the moment it is not needed to monitor
        //if com.twitter.sdk.android.tweetui.PlayerActivity have been started
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof PlayerActivity) {
            //commented logs to ease adding code in main app
            //LogUtils.d(TAG,"event_activity_destroyed");
            EventsHelper.sendTwitterVideoPlayerActivityDestroyedEvent(activity);
        }
    }

    //these methods can be empty
    @Override
    public void onActivityStarted(Activity activity) {
    }

    //tweet view full screen start
    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }
    //ActivityLifecycleCallbacks end
    //to be added in main project end
}
