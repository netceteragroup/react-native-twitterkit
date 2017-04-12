package com.twitter.sdk.android.tweetui;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;

/**
 * Created by admin on 10/04/2017.
 */

public class TweetUiWrapper {

    public static void init(Context context){

        TweetUi tweetUi = TweetUi.getInstance();
        Picasso picasso = Picasso.with(context);
        tweetUi.setImageLoader(picasso);

//        TwitterCore.getInstance().
//        TwitterCore twitterCore = TwitterCore.getInstance();
//        TweetRepository tweetRepository = new TweetRepository(tweetUi.getFabric().getMainHandler(),
//                twitterCore.getSessionManager());
//        tweetUi.setTweetRepository(tweetRepository);

        //TweetRepository tweetRepository = new TweetRepository();
        //tweetUi.setTweetRepository();
    }

}
