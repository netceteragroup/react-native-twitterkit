package com.netcetera.reactnative.twitterkit;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
public class EventsHelper {

    public static final String TWITTER_VIDEO_PLAYER_ACTIVITY_DESTROYED = "twitter_video_player_activity_destroyed";

    public static void sendTwitterVideoPlayerActivityDestroyedEvent(Context context) {
        Intent intent = new Intent(TWITTER_VIDEO_PLAYER_ACTIVITY_DESTROYED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


}
