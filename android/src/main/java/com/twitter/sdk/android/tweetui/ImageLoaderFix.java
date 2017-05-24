package com.twitter.sdk.android.tweetui;

import android.content.Context;

import com.squareup.picasso.Picasso;

/**
 * Sometimes the Picasso image loader is not (yet) initialized before a tweet is shown,
 * causing the image not to be displayed. This fix makes sure the image loader is in place.
 *
 */
public class ImageLoaderFix {


  public static void apply(Context context) {
    TweetUi tweetUi = TweetUi.getInstance();
    Picasso picasso = Picasso.with(context);
    tweetUi.setImageLoader(picasso);
  }
}
