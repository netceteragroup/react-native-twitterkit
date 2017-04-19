package com.netcetera.reactnative.twitterkit;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.twitter.sdk.android.tweetui.TweetUiWrapper;

public class RNTwitterKitViewManager extends SimpleViewManager<RNTwitterKitView> {

    private static final String TAG = RNTwitterKitViewManager.class.getCanonicalName();

    public static final String REACT_CLASS = "RNTwitterKitViewManager";

    private
    @Nullable
    AbstractDraweeControllerBuilder mDraweeControllerBuilder;
    private final
    @Nullable
    Object mCallerContext;
    private
    @Nullable
    ThemedReactContext mThemedReactContext;

    public RNTwitterKitViewManager(
            AbstractDraweeControllerBuilder draweeControllerBuilder,
            Object callerContext) {
        mDraweeControllerBuilder = draweeControllerBuilder;
        mCallerContext = callerContext;
    }

    public RNTwitterKitViewManager() {
        // Lazily initialize as FrescoModule have not been initialized yet
        mDraweeControllerBuilder = null;
        mCallerContext = null;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public RNTwitterKitView createViewInstance(ThemedReactContext context) {
        TweetUiWrapper.init(context);
        RNTwitterKitView view = new RNTwitterKitView(context, context.getCurrentActivity());
        return view;
    }

    @ReactProp(name = "tweetid")
    public void setTweetId(RNTwitterKitView view, String strTweetId) {
        Log.d(TAG, "setTweetId");
        long tweetId = Long.parseLong(strTweetId);
        view.setTweetId(tweetId);
    }

}
