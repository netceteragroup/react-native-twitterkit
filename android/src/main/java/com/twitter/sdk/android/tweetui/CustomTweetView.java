package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.util.AttributeSet;

import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by admin on 12/04/2017.
 */

public class CustomTweetView extends CompactTweetView{

    public CustomTweetView(Context context, Tweet tweet) {
        super(context, tweet);
    }

    public CustomTweetView(Context context, Tweet tweet, int styleResId) {
        super(context, tweet, styleResId);
    }

    CustomTweetView(Context context, Tweet tweet, int styleResId,
                     DependencyProvider dependencyProvider) {
        super(context, tweet, styleResId, dependencyProvider);
    }

    public CustomTweetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTweetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };
}