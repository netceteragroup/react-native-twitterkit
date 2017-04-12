package com.netcetera.reactnative.twitterkit;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetUiWrapper;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 28/03/2017.
 */

public class RNTwitterKitView extends LinearLayout{//TweetView{

    private static final String TAG = RNTwitterKitView.class.getCanonicalName();

    public RNTwitterKitView(Context context, Activity activity) {
        super(context);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        setOrientation(VERTICAL);
        setLayoutParams(layoutParams);
        initTweetContent(activity);
    }

    //tweet start
    private void initTweetContent(Activity activity){
        TweetUiWrapper.init(activity);
        tweetMainContainer = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.tweet_container, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//ViewGroup.LayoutParams.WRAP_CONTENT);
        tweetMainContainer.setLayoutParams(layoutParams);
        this.addView(tweetMainContainer);

        loadingContainer = (RelativeLayout)findViewById(R.id.loading_container);
        errorContainer = (RelativeLayout)findViewById(R.id.error_container);
        tweetView = (CompactTweetView)findViewById(R.id.tweet_view);

        setLoadingView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadTweet();
            }
        }).start();
    }

    private RelativeLayout tweetMainContainer;

    private RelativeLayout errorContainer;
    private CompactTweetView tweetView;
    private RelativeLayout loadingContainer;

    private void setLoadingView(){
        errorContainer.setVisibility(View.INVISIBLE);
        tweetView.setVisibility(View.INVISIBLE);
        loadingContainer.setVisibility(View.VISIBLE);
    }

    private void setTweetView(){
        errorContainer.setVisibility(View.INVISIBLE);
        tweetView.setVisibility(View.VISIBLE);
        loadingContainer.setVisibility(View.INVISIBLE);
    }

    private void setErrorView(){
        errorContainer.setVisibility(View.VISIBLE);
        tweetView.setVisibility(View.INVISIBLE);
        loadingContainer.setVisibility(View.INVISIBLE);
    }

    private void loadTweet(){
        final List<Long> tweetIds = Arrays.asList(tweetId);//);

        TweetUtils.loadTweets(tweetIds, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                for (Tweet tweet : result.data) {
                    setTweetView();
                    tweetView.setTweet(tweet);
                    tweetView.requestLayout();
                    //debug
//                    Gson gson = new Gson();
//                    String json = gson.toJson(tweet);
//                    Log.d(TAG,"json " + tweetId + " = " + json);
                }
            }

            @Override
            public void failure(TwitterException exception) {
                setErrorView();
            }
        });
    }
    //tweet end

    private long tweetId = 20L;

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
//        Log.d(TAG, "setTweetId = " + tweetId);
//        TweetUtilsWrapper.loadTweets(tweetId, new Callback<List<Tweet>>() {
//            @Override
//            public void success(Result<List<Tweet>> result) {
//                Log.d(TAG, "success");
//                for (Tweet tweet : result.data) {
//                    Log.d(TAG, "tweet = " + tweet.toString());
//                    setTweet(tweet);
//                }
//                Toast.makeText(getContext(), "success",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//                Log.d(TAG, "failure = " + exception.getMessage());
//                Toast.makeText(getContext(), "load_tweet_error",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
