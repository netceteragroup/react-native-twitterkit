package com.netcetera.reactnative.twitterkit;

import androidx.annotation.NonNull;
import android.util.Log;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.twitter.sdk.android.tweetui.ImageLoaderFix;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Design notes regargind auto-resize of the tweet:
 *
 * - setTweetId call on the manager results into a loadTweet method call in TweetView
 * - loadTweet method command's results (Tweet) loaded
 * - as soon as that happens, a onSizeChanged event gets posted to the manager
 * - the manager applies the new size via the UIManager
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
    ImageLoaderFix.apply(context);
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
    view.respondToNewProps();
  }

  @Override
  public TweetShadowNode createShadowNodeInstance() {
    return new TweetShadowNode();
  }

  @Override
  public Class<TweetShadowNode> getShadowNodeClass() {
    return TweetShadowNode.class;
  }

  public @Nullable
  Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(
            "onLoadError", MapBuilder.of("registrationName", "onLoadError"),
            "onLoadSuccess", MapBuilder.of("registrationName", "onLoadSuccess")
    );
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
