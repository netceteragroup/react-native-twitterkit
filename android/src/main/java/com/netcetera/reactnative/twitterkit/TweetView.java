package com.netcetera.reactnative.twitterkit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.UIManagerModule;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.CustomTweetView;
import com.twitter.sdk.android.tweetui.ToggleImageButton;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class TweetView extends RelativeLayout {

    private static final String TAG = TweetView.class.getCanonicalName();

    private static final int MAIN_ID = 101;

    public TweetView(Context context, Activity activity) {
        super(context);
        mLayoutInflater = activity.getLayoutInflater();
        LogUtils.d(TAG, "TweetView");
        initTweetContent(activity);
        //ReactContext reactContext = (ReactContext)context;
        //reactContext.getCurrentActivity();
        setId(R.id.debug_id);//not working
    }

    private LayoutInflater mLayoutInflater;

    private void initTweetContent(Activity activity) {
        LogUtils.d(TAG, "initTweetContent");
        mLayoutInflater.inflate(R.layout.tweet_container, this, true);
        tweetMainContainer = this;
        tweetMainContainer.setBackgroundColor(activity.getResources().getColor(android.R.color.black));

        findViews();

        setLoadingView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isWaiting) {
                    if (tweetId > 0) {
                        isWaiting = false;
                        loadTweet();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    private boolean isWaiting = true;

    private View tweetMainContainer;

    private ImageView reloadButton;
    private RelativeLayout reloadContainer;
    private RelativeLayout errorContainer;
    private CompactTweetView tweetView;
    private RelativeLayout loadingContainer;

    private int counter = 0;
    private Timer timer;

    private void requestLayoutWithDelay() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
                           //@Override
                           public void run() {
                               //some delay to allow the tweet to fully load
                               if (counter == 1) {
                                   tweetMainContainer.post(new Runnable() {
                                       @Override
                                       public void run() {
                                           android.util.Log.d(TAG, "tweetView.setTweet");
                                           tweetView.setTweet(globalTweet);
                                       }
                                   });
                               } else if (counter == 2) {
                                   //timer.cancel();
                                   tweetMainContainer.post(new Runnable() {
                                       @Override
                                       public void run() {
                                           setTweetView();
                                           hideLikeButton();
                                       }
                                   });
                               } else if (counter == 3) {
                                   tweetMainContainer.post(new Runnable() {
                                       @Override
                                       public void run() {
                                           tweetView.setVisibility(View.VISIBLE);
                                           //UIManagerModule start
//                                           com.facebook.react.bridge.AssertionException: Expected to be called from the 'native_modules' thread!
//                                                   at com.facebook.react.bridge.SoftAssertions.assertCondition(SoftAssertions.java:37)
//                                           at com.facebook.react.bridge.queue.MessageQueueThreadImpl.assertIsOnThread(MessageQueueThreadImpl.java:99)
//                                           at com.facebook.react.bridge.ReactContext.assertOnNativeModulesQueueThread(ReactContext.java:278)
//                                           at com.facebook.react.uimanager.UIManagerModule.updateNodeSize(UIManagerModule.java:233)
//                                           at com.netcetera.reactnative.twitterkit.TweetView$2$3.run(TweetView.java:115)
                                           //ReactContext reactContext = ((ReactContext) getContext());
                                           //reactContext.getNativeModule(UIManagerModule.class).updateNodeSize(getId(), 400, 400);
                                           //UIManagerModule end
                                           RNTwitterKitViewManager.layoutShadowNode.setStyleHeight(tweetView.getMeasuredHeight());
                                           android.util.Log.d(TAG, "getHeight = " + tweetView.getHeight());
                                           android.util.Log.d(TAG, "getWidth = " + tweetView.getWidth());
                                           android.util.Log.d(TAG, "getMeasuredHeight = " + tweetView.getMeasuredHeight());
                                           android.util.Log.d(TAG, "getMeasuredWidth = " + tweetView.getMeasuredWidth());
                                       }
                                   });
                                   timer.cancel();
                               }
                               counter++;
                           }
                       }
                , 0, 1000);//Update text every second
    }

    private Tweet globalTweet = null;

    private void hideLikeButton() {
        ToggleImageButton likeButton = (ToggleImageButton) findViewById(R.id.tw__tweet_like_button);
        likeButton.setVisibility(View.GONE);
    }

    private void setTweetAndRequestLayout(Tweet tweet) {
        globalTweet = tweet;
        LogUtils.d(TAG, "setTweetView, tweet.text = " + tweet.text + ", tweet.id = " + tweet.id);
        tweetView.setTweet(null);
        requestLayoutWithDelay();
    }

    private void findViews() {
        loadingContainer = (RelativeLayout) tweetMainContainer.findViewById(R.id.loading_container);
        reloadContainer = (RelativeLayout) findViewById(R.id.reload_container);
        errorContainer = (RelativeLayout) tweetMainContainer.findViewById(R.id.error_container);
        tweetView = (CompactTweetView) findViewById(R.id.tweet_view);
        reloadButton = (ImageView) findViewById(R.id.reload_button);
        //tweetView = (CustomTweetView) mLayoutInflater.inflate(R.layout.direct_tweet, null);
    }

    private void setTweetView() {
        errorContainer.setVisibility(View.INVISIBLE);
        reloadContainer.setVisibility(View.INVISIBLE);
        loadingContainer.setVisibility(View.INVISIBLE);

        tweetView.setVisibility(View.INVISIBLE);
        removeAllViews();
        addView(tweetView);

        final int spec = View.MeasureSpec.makeMeasureSpec(
                tweetView.getHeight(),
                View.MeasureSpec.UNSPECIFIED);
        LogUtils.d(TAG, "spec = " + spec);
        measure(getWidth(), spec);

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
        final List<Long> tweetIds = Arrays.asList(tweetId);//);
        TweetUtils.loadTweets(tweetIds, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                LogUtils.d(TAG, "loadTweet, success");
                Tweet selectedTweet = null;
                for (Tweet tweet : result.data) {
                    LogUtils.d(TAG, "loadTweet, success, tweet.text = " + tweet.text);
                    selectedTweet = tweet;
                    setTweetAndRequestLayout(tweet);
                    //LogUtils.debugJson(tweet);
                }
                if (selectedTweet == null) {
                    setReloadView();
                    //setErrorOrReloadView();
                }
            }

            @Override
            public void failure(TwitterException exception) {
                LogUtils.d(TAG, "loadTweet, failure");
                setReloadView();
                //setErrorOrReloadView();
            }
        });
    }

    private long tweetId = 0L;

    public void setTweetId(long tweetId) {
        LogUtils.d(TAG, "setTweetId, tweetId = " + tweetId);
        this.tweetId = tweetId;
    }

}