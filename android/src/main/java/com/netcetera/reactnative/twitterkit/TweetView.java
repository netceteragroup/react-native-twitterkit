package com.netcetera.reactnative.twitterkit;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.ToggleImageButton;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.ArrayList;

class TweetView extends RelativeLayout {



  public interface SizeChangeListener {
    void onSizeChanged(TweetView view, int width, int height);
  }

  private static final String TAG = TweetView.class.getCanonicalName();

  private View tweetMainContainer;

  private CompactTweetView tweetView;
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

    tweetView.setTweet(tweet);
    initializeTweetView();

    updateSize();
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

    int w = View.MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY);
    int h = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED);

    Log.d(TAG, "Measured " + tweetView.getMeasuredWidth() + ", " + tweetView.getMeasuredHeight());

    tweetView.measure(w, h);
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
    likeButton.setVisibility(View.GONE);
  }


  private void findViews() {
    loadingContainer = (RelativeLayout) tweetMainContainer.findViewById(R.id.loading_container);
    tweetView = (CompactTweetView) findViewById(R.id.tweet_view);
  }

  private void initializeTweetView() {
    loadingContainer.setVisibility(View.INVISIBLE);

    tweetView.setVisibility(View.VISIBLE);
  }


  private void setLoadingView() {
    LogUtils.d(TAG, "setLoadingView");
    tweetView.setVisibility(View.INVISIBLE);
    loadingContainer.setVisibility(View.VISIBLE);
  }

  private void handleError() {
    LogUtils.d(TAG, "handleError");
    tweetView.setVisibility(View.INVISIBLE);

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