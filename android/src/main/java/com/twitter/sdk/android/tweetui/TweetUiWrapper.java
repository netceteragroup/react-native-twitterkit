package com.twitter.sdk.android.tweetui;

import android.content.Context;

import com.squareup.picasso.Picasso;

public class TweetUiWrapper {

    public static void init(Context context){
        TweetUi tweetUi = TweetUi.getInstance();
        Picasso picasso = Picasso.with(context);
        tweetUi.setImageLoader(picasso);
    }

}
