package com.netcetera.reactnative.twitterkit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.react.uimanager.ThemedReactContext;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.Arrays;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * Created by admin on 28/03/2017.
 */

public class TweetUtilsWrapper {
    /**
     * Loads a List of Tweets by id. Returns Tweets in the order requested.
     * @param tweetId
     * @param cb callback
     */
    public static void loadTweets(final long tweetId, final Callback<List<Tweet>> cb) {
        final List<Long> tweetIds = Arrays.asList(tweetId);//846231685750439936L);//510908133917487104L);
        TweetUtils.loadTweets(tweetIds, cb);
    }

}
