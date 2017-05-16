package com.netcetera.reactnative.twitterkit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextInlineImageShadowNode;
import com.twitter.sdk.android.tweetui.TweetUiWrapper;
//see ReactTextViewManager  as example
public class RNTwitterKitViewManager extends BaseViewManager<TweetView,TweetShadowNode> {

    private static final String TAG = RNTwitterKitViewManager.class.getCanonicalName();

    /* package */ static final String DEFAULT_STYLE = "Normal";

    public static final String PROP_STYLE = "styleAttr";
    /* package */ static final String PROP_INDETERMINATE = "indeterminate";
    /* package */ static final String PROP_PROGRESS = "progress";
    /* package */ static final String PROP_ANIMATING = "animating";


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

    @ReactProp(name = "tweetid")
    public void setTweetId(TweetView view, String strTweetId) {
        Log.d(TAG, "setTweetId");
        long tweetId = Long.parseLong(strTweetId);
        view.setTweetId(tweetId);
    }

    @Override
    public TweetView createViewInstance(ThemedReactContext context) {
        TweetUiWrapper.init(context);
        TweetView view = new TweetView(context, context.getCurrentActivity());
        return view;
    }

    //start TweetShadowNode methods
    @Override
    public void updateExtraData(TweetView view, Object extraData) {
        view.requestLayout();
        view.invalidate();
//        ReactTextUpdate update = (ReactTextUpdate) extraData;
//        if (update.containsImages()) {
//            Spannable spannable = update.getText();
//            TextInlineImageSpan.possiblyUpdateInlineImageSpans(spannable, view);
//        }
//        view.setText(update);
    }

    public static TweetShadowNode layoutShadowNode;

    @Override
    public TweetShadowNode createShadowNodeInstance() {
        layoutShadowNode = new TweetShadowNode();
        layoutShadowNode.setStyleHeight(100);
        return layoutShadowNode;
    }

    @Override
    public Class<TweetShadowNode> getShadowNodeClass() {
        return TweetShadowNode.class;
    }

    private static Object sTweetViewCtorLock = new Object();

    /**
     * We create ProgressBars on both the UI and shadow threads. There is a race condition in the
     * ProgressBar constructor that may cause crashes when two ProgressBars are constructed at the
     * same time on two different threads. This static ctor wrapper protects against that.
     */
    public static TweetView createTweetView(Context context) {
        synchronized (sTweetViewCtorLock) {
            return new TweetView(context, null);
        }
    }
}
