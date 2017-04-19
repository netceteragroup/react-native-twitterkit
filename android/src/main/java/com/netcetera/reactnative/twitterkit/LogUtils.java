package com.netcetera.reactnative.twitterkit;

public class LogUtils {

    public static void d(String TAG, String message) {
        android.util.Log.d(TAG,message);
    }

    //    public static void debugTweetAsJson(Tweet tweet){
//        debug
//        Gson gson = new Gson();
//        String json = gson.toJson(tweet);
//        android.util.Log.d(TAG,"json " + tweetId + " = " + json);
//    }
}
