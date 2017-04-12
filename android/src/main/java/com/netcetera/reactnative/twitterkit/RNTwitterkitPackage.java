
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
import io.fabric.sdk.android.Logger;

public class RNTwitterKitPackage implements ReactPackage {
    public final static String CRASHLYTICS_KEY_CRASHES = "are_crashes_enabled";

    private static final String CONSUMER_KEY = "ZJOFGUJEzg1VG25lss7hyo5vK";//"c8CJhztXrhCoxHoyNSGshF6lh";
    private static final String CONSUMER_SECRET = "bj6ywtDPaRV5M90s9Z6CK07Uc2qZYtzSTXWrQrMHdwBUYNQ5k6";//"CeMLg5nt9ei3ckdn98NpJCtdLXTgJjV2FUeEK08slK33wI6hHp";

    private boolean areCrashesEnabled(){
        return true;
    }

    public static void initFabric(Context reactContext){
        //public static void initFabric(ReactApplicationContext context){
        TwitterAuthConfig authConfig
                = new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET);

        final Fabric fabric = new Fabric.Builder(reactContext)
                .kits(new Twitter(authConfig))
//                .logger(new Logger() {
//                    @Override
//                    public boolean isLoggable(String s, int i) {
//                        return true;
//                    }
//
//                    @Override
//                    public int getLogLevel() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setLogLevel(int i) {
//
//                    }
//
//                    @Override
//                    public void d(String s, String s1, Throwable throwable) {
//                        android.util.Log.d(s, s1);
//                    }
//
//                    @Override
//                    public void v(String s, String s1, Throwable throwable) {
//                        android.util.Log.v(s, s1);
//                    }
//
//                    @Override
//                    public void i(String s, String s1, Throwable throwable) {
//                        android.util.Log.i(s, s1);
//                    }
//
//                    @Override
//                    public void w(String s, String s1, Throwable throwable) {
//                        android.util.Log.w(s, s1);
//                    }
//
//                    @Override
//                    public void e(String s, String s1, Throwable throwable) {
//                        android.util.Log.e(s, s1);
//                    }
//
//                    @Override
//                    public void d(String s, String s1) {
//                        android.util.Log.d(s, s1);
//                    }
//
//                    @Override
//                    public void v(String s, String s1) {
//                        android.util.Log.v(s, s1);
//                    }
//
//                    @Override
//                    public void i(String s, String s1) {
//                        android.util.Log.i(s, s1);
//                    }
//
//                    @Override
//                    public void w(String s, String s1) {
//                        android.util.Log.w(s, s1);
//                    }
//
//                    @Override
//                    public void e(String s, String s1) {
//                        android.util.Log.e(s, s1);
//                    }
//
//                    @Override
//                    public void log(int i, String s, String s1) {
//                        android.util.Log.v(s, "logiss, " + s1);
//                    }
//
//                    @Override
//                    public void log(int i, String s, String s1, boolean b) {
//                        android.util.Log.w(s, "logissb, " + s1);
//                    }
//                })
//                .debuggable(true)
                .build();

        Fabric.with(fabric);

    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        //initFabric(reactContext);
      return Arrays.<NativeModule>asList(new RNTwitterKitModule(reactContext));
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
      return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(
            ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new RNTwitterKitViewManager()
        );
    }
}