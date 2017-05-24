package com.netcetera.reactnative.twitterkit;

import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Design notes:
 *
 * - setTweetId call on the manager results into a loadTweet method call in TweetView
 * - loadTweet method command's results (Tweet) loaded
 * - as soon as that happens,
 * - shadow node re-calculates the layout and passes on the loaded Tweet to the actual tweet, along with the measured layout
 *
 */

public class ReactTweetViewManager
        extends BaseViewManager<TweetView, TweetShadowNode>
        implements TweetView.SizeChangeListener {

  private static final String TAG = ReactTweetViewManager.class.getCanonicalName();


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
    TweetView tweetView = createTweetView(context);
    tweetView.addSizeChangeListener(this);

    return tweetView;
  }

  @Override
  public void onDropViewInstance(TweetView view) {
    view.removeSizeChangeListener(this);
    super.onDropViewInstance(view);
  }


  @NonNull
  public static TweetView createTweetView(ThemedReactContext context) {
    return new TweetView(context);
  }

  //start TweetShadowNode methods
  @Override
  public void updateExtraData(TweetView view, Object extraData) {
    view.setReactTag((Integer) extraData);
  }

  @Override
  public TweetShadowNode createShadowNodeInstance() {
    return new TweetShadowNode();
  }

  @Override
  public Class<TweetShadowNode> getShadowNodeClass() {
    return TweetShadowNode.class;
  }

  @Override
  public void onSizeChanged(TweetView view, final int width, final int height) {
    Log.d(TAG, "TweetView changed size: " + width + ", " + height);
    ReactContext ctx = (ReactContext) view.getContext();
    final UIManagerModule uiManager = ctx.getNativeModule(UIManagerModule.class);
    final int reactTag = view.getReactTag();

    ctx.runOnNativeModulesQueueThread(new Runnable() {
      @Override
      public void run() {
        uiManager.updateNodeSize(reactTag, width, height);
      }
    });
  }
}
