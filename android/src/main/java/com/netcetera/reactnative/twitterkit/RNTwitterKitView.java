package com.netcetera.reactnative.twitterkit;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CustomTweetView;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//to investigate, possible issues
//it seems that 846231685750439936 default id is loaded from cache from time to time
//investigate cache on twitter
//todo
//1. clean the code and use only one thread
//2. make one log class to be able to show or not show logs
//3. add progress bar
//4. fix heart/like button
//User authorization required
//        com.twitter.sdk.android.core.TwitterAuthException: User authorization required
//        at com.twitter.sdk.android.tweetui.TweetRepository.getUserSession(TweetRepository.java:149)
//        at com.twitter.sdk.android.tweetui.TweetRepository.favorite(TweetRepository.java:107)
//        at com.twitter.sdk.android.tweetui.LikeTweetAction.onClick(LikeTweetAction.java:65)
//some important things
//1. must use customtweetview instead of compacttweetview
//2. must use xml for CompactTweetView, it seems it does not work if CustomTweetView is created from code
//3. set own imageloader
//4. must have these states in react view
//5. use relativelayout for better appeareance
//6. use invisible not gone

//there are 3 states
//state_wait_for_property_tweetid
//state_loading_from_server
//state_wait_to_load_resources_before_showing
public class RNTwitterKitView extends RelativeLayout {

    private static final String TAG = RNTwitterKitView.class.getCanonicalName();

    public RNTwitterKitView(Context context, Activity activity) {
        super(context);
        android.util.Log.d(TAG, "RNTwitterKitView");
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        initTweetContent(activity);
    }

    private void initTweetContent(Activity activity) {
        android.util.Log.d(TAG, "initTweetContent");
        //TweetUiWrapper.init(activity);
        tweetMainContainer = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.tweet_container, null);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//ViewGroup.LayoutParams.WRAP_CONTENT);
        //tweetMainContainer.setLayoutParams(layoutParams);
        this.addView(tweetMainContainer);

        loadingContainer = (RelativeLayout) tweetMainContainer.findViewById(R.id.loading_container);
        errorContainer = (RelativeLayout) tweetMainContainer.findViewById(R.id.error_container);
        tweetView = (CustomTweetView) findViewById(R.id.tweet_view);

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

    private RelativeLayout tweetMainContainer;

    private RelativeLayout errorContainer;
    private CustomTweetView tweetView = null;
    private RelativeLayout loadingContainer;

    private void setLoadingView() {
        android.util.Log.d(TAG, "setLoadingView");
        errorContainer.setVisibility(View.INVISIBLE);
        if (tweetView != null) {
            tweetView.setVisibility(View.INVISIBLE);
        }
        loadingContainer.setVisibility(View.VISIBLE);
    }

    //start

    private int counter = 0;
    private Timer timer;

    private void requestLayoutWithDelay() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
                           //@Override
                           public void run() {
                               //some delay to allow the tweet to fully load
                               if (counter == 2) {
                                   timer.cancel();
                                   tweetMainContainer.post(new Runnable() {
                                       @Override
                                       public void run() {
                                           errorContainer.setVisibility(View.INVISIBLE);
                                           loadingContainer.setVisibility(View.INVISIBLE);
                                           tweetView.setVisibility(View.VISIBLE);
                                           tweetView.requestLayout();
                                       }
                                   });
                               }
                               counter++;
                           }
                       }
                , 0, 1000);//Update text every second
    }

    //end
    private void setTweetView(Tweet tweet) {
        android.util.Log.d(TAG, "setTweetView, tweet.text = " + tweet.text + ", tweet.id = " + tweet.id);
        tweetView.setTweet(tweet);
        requestLayoutWithDelay();
    }

    private void setErrorView() {
        android.util.Log.d(TAG, "setErrorView");
        errorContainer.setVisibility(View.VISIBLE);
        if (tweetView != null) {
            tweetView.setVisibility(View.INVISIBLE);
        }
        loadingContainer.setVisibility(View.INVISIBLE);
    }

    private void loadTweet() {
        android.util.Log.d(TAG, "loadTweet, tweetId = " + tweetId);
        final List<Long> tweetIds = Arrays.asList(tweetId);//);
        TweetUtils.loadTweets(tweetIds, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                android.util.Log.d(TAG, "loadTweet, success");
                for (Tweet tweet : result.data) {
                    android.util.Log.d(TAG, "loadTweet, success, tweet.text = " + tweet.text);
                    setTweetView(tweet);

                    //debug
//                    Gson gson = new Gson();
//                    String json = gson.toJson(tweet);
//                    Log.d(TAG,"json " + tweetId + " = " + json);
                }
            }

            @Override
            public void failure(TwitterException exception) {
                android.util.Log.d(TAG, "loadTweet, failure");
                setErrorView();
            }
        });
    }

    private long tweetId = 0L;

    public void setTweetId(long tweetId) {
        android.util.Log.d(TAG, "setTweetId, tweetId = " + tweetId);
        this.tweetId = tweetId;
    }

}
