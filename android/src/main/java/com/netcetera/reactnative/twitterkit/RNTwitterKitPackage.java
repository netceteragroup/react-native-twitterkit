
package com.netcetera.reactnative.twitterkit;

import android.content.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class RNTwitterKitPackage implements ReactPackage {

    private static final String CONSUMER_KEY = "ZJOFGUJEzg1VG25lss7hyo5vK";//"c8CJhztXrhCoxHoyNSGshF6lh";
    private static final String CONSUMER_SECRET = "bj6ywtDPaRV5M90s9Z6CK07Uc2qZYtzSTXWrQrMHdwBUYNQ5k6";//"CeMLg5nt9ei3ckdn98NpJCtdLXTgJjV2FUeEK08slK33wI6hHp";

    public static void initFabric(Context reactContext){
        TwitterAuthConfig authConfig
                = new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET);

        final Fabric fabric = new Fabric.Builder(reactContext)
                .kits(new Twitter(authConfig))
                .build();

        Fabric.with(fabric);
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
      return Collections.emptyList();
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
      return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(
            ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new ReactTweetViewManager()
        );
    }
}