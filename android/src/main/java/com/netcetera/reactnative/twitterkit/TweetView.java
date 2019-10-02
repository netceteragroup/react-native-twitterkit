package com.netcetera.reactnative.twitterkit;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.ToggleImageButton;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.ArrayList;

//19.06.2017 - like button is hidden
class TweetView extends RelativeLayout {

  public interface SizeChangeListener {
    void onSizeChanged(TweetView view, int width, int height);
  }

  private static final String TAG = TweetView.class.getCanonicalName();

  private View tweetMainContainer;

  private com.twitter.sdk.android.tweetui.TweetView tweetView = null;
  private RelativeLayout loadingContainer;
  private int reactTag;

  private boolean postponedResize = false;

  private Tweet tweet = null;
  private long tweetId = Long.MIN_VALUE;

  private ArrayList<SizeChangeListener> sizeChangeListeners = new ArrayList<>();

  public TweetView(Context context) {
    super(context);
    LogUtils.d(TAG, "TweetView");
    initTweetContent();
    setId(R.id.debug_id);//not working
  }

  private void initTweetContent() {
    LogUtils.d(TAG, "initTweetContent");
    LayoutInflater.from(getContext()).inflate(R.layout.tweet_container, this, true);
    tweetMainContainer = this;

    findViews();
    setLoadingView();
  }

  public void setTweet(Tweet tweet) {
    this.tweet = tweet;
    if (tweet != null) {
      setTweetIdInternally(tweet.getId());
    }
    if(tweetView == null){
      tweetView = new com.twitter.sdk.android.tweetui.TweetView(getContext(), tweet);
      RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      layoutParams.addRule(CENTER_IN_PARENT);
      tweetView.setLayoutParams(layoutParams);
      tweetView.setTweetActionsEnabled(true);
      addView(tweetView);
    }
    initializeTweetView();

    updateSize();
    hideLikeButton();
  }

  public void respondToNewProps() {
    if (this.tweetId == Long.MIN_VALUE) {
      handleError();
    }
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);

    if (postponedResize) {
      postponedResize = false;
      updateSize();
    }
  }

  private void updateSize() {
    LogUtils.d(TAG, "updateSize");

    if (getWidth() <= 0) {
      Log.d(TAG, "width is still 0, postponing updateSize()");
      postponedResize = true;
      return;
    }

    measureTweet();
    requestLayout();


    fireSizeChange(getWidth(), tweetView.getMeasuredHeight());
  }


  private void measureTweet() {

    if(tweetView != null) {
      int w = View.MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY);
      int h = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED);

      Log.d(TAG, "Measured " + tweetView.getMeasuredWidth() + ", " + tweetView.getMeasuredHeight());

      tweetView.measure(w, h);
    }
  }

  public void setTweetId(long tweetId) {
    // triggers a load
    LogUtils.d(TAG, "setTweetId, tweetId = " + tweetId);
    if (tweetId != this.tweetId) {
      setTweetIdInternally(tweetId);
      loadTweet();
    }
  }

  public void addSizeChangeListener(@NonNull SizeChangeListener l) {
    sizeChangeListeners.add(l);
  }

  public void removeSizeChangeListener(@NonNull SizeChangeListener l) {
    sizeChangeListeners.remove(l);
  }

  protected void fireSizeChange(int width, int height) {
    for (SizeChangeListener l : sizeChangeListeners) {
      l.onSizeChanged(this, width, height);
    }
  }

  public int getReactTag() {
    return reactTag;
  }

  public void setReactTag(int reactTag) {
    this.reactTag = reactTag;
  }

  private void setTweetIdInternally(long tweetId) {
    this.tweetId = tweetId;
  }


  private void hideLikeButton() {
    ToggleImageButton likeButton = (ToggleImageButton) findViewById(R.id.tw__tweet_like_button);
    if(likeButton != null) {
      likeButton.setVisibility(View.GONE);
    }
  }


  private void findViews() {
    loadingContainer = (RelativeLayout) tweetMainContainer.findViewById(R.id.loading_container);
  }

  private void initializeTweetView() {
    loadingContainer.setVisibility(View.INVISIBLE);
    if(tweetView != null) {
      tweetView.setVisibility(View.VISIBLE);
      tweetView.requestLayout();
    }
  }


  private void setLoadingView() {
    LogUtils.d(TAG, "setLoadingView");
    if(tweetView != null) {
      tweetView.setVisibility(View.INVISIBLE);
    }
    loadingContainer.setVisibility(View.VISIBLE);
  }

  private void handleError() {
    LogUtils.d(TAG, "handleError");
    if(tweetView != null) {
      tweetView.setVisibility(View.INVISIBLE);
    }

    WritableMap evt = Arguments.createMap();
    evt.putString("message", "Could not load tweet");

    ReactContext ctx = (ReactContext) getContext();
    ctx.getJSModule(RCTEventEmitter.class).receiveEvent(
            getId(),
            "onLoadError",
            evt);

  }

  private void handleSuccess() {
    WritableMap evt = Arguments.createMap();

    ReactContext ctx = (ReactContext) getContext();
    ctx.getJSModule(RCTEventEmitter.class).receiveEvent(
            getId(),
            "onLoadSuccess",
            evt);
  }

  private int errorCounter = 0;

  private void loadTweet() {
    LogUtils.d(TAG, "loadTweet, tweetId = " + tweetId);

    TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
      @Override
      public void success(Result<Tweet> result) {
        LogUtils.d(TAG, "loadTweet, success");
        Tweet selectedTweet = result.data;
        setTweet(selectedTweet);
        handleSuccess();
      }

      @Override
      public void failure(TwitterException exception) {
        LogUtils.d(TAG, "loadTweet, failure");
        // TODO send message
        handleError();
      }
    });
  }

}