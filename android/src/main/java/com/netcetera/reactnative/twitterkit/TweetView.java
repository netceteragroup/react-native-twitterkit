package com.netcetera.reactnative.twitterkit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

  private ImageView reloadButton;
  private RelativeLayout reloadContainer;
  private RelativeLayout errorContainer;
  private CompactTweetView tweetView;
  private RelativeLayout loadingContainer;
  private int reactTag;


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

    boolean laidOut = tweetView.isLaidOut();

    tweetView.setTweet(tweet);
    initializeTweetView();

    if (laidOut) {
      updateSize();
    } else {
      post(new Runnable() {
        @Override
        public void run() {
          updateSize();
        }
      });
    }

  }

  private void updateSize() {
    LogUtils.d(TAG, "updateSize");

    measureTweet();
    requestLayout();
    fireSizeChange(tweetView.getWidth(), tweetView.getMeasuredHeight());
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
    reloadContainer = (RelativeLayout) findViewById(R.id.reload_container);
    errorContainer = (RelativeLayout) tweetMainContainer.findViewById(R.id.error_container);
    tweetView = (CompactTweetView) findViewById(R.id.tweet_view);
    reloadButton = (ImageView) findViewById(R.id.reload_button);
  }

  private void initializeTweetView() {
    errorContainer.setVisibility(View.INVISIBLE);
    reloadContainer.setVisibility(View.INVISIBLE);
    loadingContainer.setVisibility(View.INVISIBLE);

    tweetView.setVisibility(View.VISIBLE);
  }

  private void measureTweet() {

    int w = View.MeasureSpec.makeMeasureSpec(tweetView.getWidth(), MeasureSpec.EXACTLY);
    int h = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED);

    tweetView.measure(w, h);
  }

  private void setLoadingView() {
    LogUtils.d(TAG, "setLoadingView");
    errorContainer.setVisibility(View.INVISIBLE);
    reloadContainer.setVisibility(View.INVISIBLE);
    tweetView.setVisibility(View.INVISIBLE);
    loadingContainer.setVisibility(View.VISIBLE);
  }

  private void setErrorView() {
    LogUtils.d(TAG, "setErrorView");
    reloadContainer.setVisibility(View.INVISIBLE);
    loadingContainer.setVisibility(View.INVISIBLE);
    tweetView.setVisibility(View.INVISIBLE);
    errorContainer.setVisibility(View.VISIBLE);
  }

  private int errorCounter = 0;

  private void setErrorOrReloadView() {
    LogUtils.d(TAG, "setErrorOrReloadView");
    if (errorCounter < 3) {
      setReloadView();
    } else {
      setErrorView();
    }
    errorCounter++;
  }

  private void setReloadView() {
    LogUtils.d(TAG, "setReloadView");
    loadingContainer.setVisibility(View.INVISIBLE);
    tweetView.setVisibility(View.INVISIBLE);
    errorContainer.setVisibility(View.INVISIBLE);
    reloadContainer.setVisibility(View.VISIBLE);
    reloadButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        setLoadingView();
        loadTweet();
      }
    });
  }

  private void loadTweet() {
    LogUtils.d(TAG, "loadTweet, tweetId = " + tweetId);

    TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
      @Override
      public void success(Result<Tweet> result) {
        LogUtils.d(TAG, "loadTweet, success");
        Tweet selectedTweet = result.data;
        setTweet(selectedTweet);
      }

      @Override
      public void failure(TwitterException exception) {
        LogUtils.d(TAG, "loadTweet, failure");
        setReloadView();
      }
    });
  }

}