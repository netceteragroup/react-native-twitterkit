package com.netcetera.reactnative.twitterkit;

import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.YogaNodeAPI;

import java.util.HashSet;
import java.util.Set;

/**
 * Inspired from ProgressBarShadowNode
 */

public class TweetShadowNode extends LayoutShadowNode implements YogaMeasureFunction {

    private int mHeight;
    private int mWidth;

    public TweetShadowNode() {
        setMeasureFunction(this);
    }

    @Override
    public long measure(
            YogaNodeAPI node,
            float width,
            YogaMeasureMode widthMode,
            float height,
            YogaMeasureMode heightMode) {
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