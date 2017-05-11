package com.netcetera.reactnative.twitterkit;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextInlineImageShadowNode;
import com.twitter.sdk.android.tweetui.TweetUiWrapper;

public class RNTwitterKitViewManager extends SimpleViewManager<TweetView> {

    private static final String TAG = RNTwitterKitViewManager.class.getCanonicalName();

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
        return "TweetView";
    }

    @Override
    public TweetView createViewInstance(ThemedReactContext context) {
        TweetUiWrapper.init(context);
        TweetView view = new TweetView(context, context.getCurrentActivity());
        return view;
    }

    @ReactProp(name = "tweetid")
    public void setTweetId(TweetView view, String strTweetId) {
        Log.d(TAG, "setTweetId");
        long tweetId = Long.parseLong(strTweetId);
        view.setTweetId(tweetId);
    }

    @Override
    public LayoutShadowNode createShadowNodeInstance() {
        return new LayoutShadowNode();
    }

    @Override
    public Class<LayoutShadowNode> getShadowNodeClass() {
        return LayoutShadowNode.class;
    }
    //ReactTextInlineImageShado
}
