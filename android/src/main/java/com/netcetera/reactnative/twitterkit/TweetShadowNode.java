package com.netcetera.reactnative.twitterkit;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNodeAPI;
import com.twitter.sdk.android.core.models.Tweet;

/**
 * Inspired from ProgressBarShadowNode
 */

public class TweetShadowNode extends LayoutShadowNode implements YogaMeasureFunction {

  private TweetView mTweetView;

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
    return measure(null, node, width, widthMode, height, heightMode);
  }

  @Override
  public void onCollectExtraUpdates(UIViewOperationQueue uiViewOperationQueue) {
    super.onCollectExtraUpdates(uiViewOperationQueue);

    uiViewOperationQueue.enqueueUpdateExtraData(getReactTag(), getReactTag());
  }

  public synchronized long measure(
          Tweet tweet,
          YogaNodeAPI node,
          float width,
          YogaMeasureMode widthMode,
          float height,
          YogaMeasureMode heightMode
  ) {
    if (mTweetView == null) {
      mTweetView = ReactTweetViewManager.createTweetView(getThemedContext());
    }

    if (tweet != null) {
      mTweetView.setTweet(tweet);
    }
    mTweetView.measure(yogaToAndroid(widthMode, width), yogaToAndroid(heightMode, height));

    int measuredWidth = mTweetView.getMeasuredWidth();
    int measuredHeight = mTweetView.getMeasuredHeight();
    return YogaMeasureOutput.make(measuredWidth, measuredHeight);
  }

  private static int yogaToAndroid(YogaMeasureMode mode, float value) {
    int m;
    switch (mode) {
      case AT_MOST: m = View.MeasureSpec.AT_MOST; break;
      case EXACTLY: m = View.MeasureSpec.EXACTLY; break;
      case UNDEFINED:
      default:
        m = View.MeasureSpec.UNSPECIFIED;
    }

    int v;
    if (value == Float.NaN) {
      v = ViewGroup.LayoutParams.WRAP_CONTENT;
    } else {
      v = (int) value;
    }

    return View.MeasureSpec.makeMeasureSpec(v, m);
  }
}