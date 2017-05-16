package com.netcetera.reactnative.twitterkit;

import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.progressbar.ReactProgressBarViewManager;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.YogaNodeAPI;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by arnia on 16/05/2017.
 */

public class TweetShadowNode extends LayoutShadowNode implements YogaMeasureFunction {

    private static final String TAG = TweetShadowNode.class.getCanonicalName();

    private int mHeight;
    private int mWidth;

    public TweetShadowNode() {
        setMeasureFunction(this);
    }

    //added setter and getter
    private String mStyle = RNTwitterKitViewManager.DEFAULT_STYLE;


    public @Nullable
    String getStyle() {
        return mStyle;
    }

    @ReactProp(name = RNTwitterKitViewManager.PROP_STYLE)
    public void setStyle(@Nullable String style) {
        mStyle = style == null ? RNTwitterKitViewManager.DEFAULT_STYLE : style;
    }
    //end

    @Override
    public long measure(
            YogaNodeAPI node,
            float width,
            YogaMeasureMode widthMode,
            float height,
            YogaMeasureMode heightMode) {
        LogUtils.d(TAG, "TweetShadowNode.measure");
        TweetView tweetView = RNTwitterKitViewManager.createTweetView(getThemedContext());
//final int spec = View.MeasureSpec.makeMeasureSpec(
//        ViewGroup.LayoutParams.WRAP_CONTENT,
//        View.MeasureSpec.UNSPECIFIED);
//        tweetView.measure(spec, spec);
        mHeight = tweetView.getMeasuredHeight();
        mWidth = tweetView.getMeasuredWidth();
        return YogaMeasureOutput.make(mWidth, mHeight);
    }
}