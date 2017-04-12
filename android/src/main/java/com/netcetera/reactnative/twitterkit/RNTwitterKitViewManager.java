package com.netcetera.reactnative.twitterkit;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.image.ReactImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Card;
import com.twitter.sdk.android.core.models.Coordinates;
import com.twitter.sdk.android.core.models.HashtagEntity;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.MentionEntity;
import com.twitter.sdk.android.core.models.Place;
import com.twitter.sdk.android.core.models.SymbolEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.core.models.TweetEntities;
import com.twitter.sdk.android.core.models.UrlEntity;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.models.UserBuilder;
import com.twitter.sdk.android.core.models.UserEntities;
import com.twitter.sdk.android.core.models.VideoInfo;
import com.twitter.sdk.android.tweetui.TweetUiWrapper;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RNTwitterKitViewManager extends SimpleViewManager<RNTwitterKitView> {

    private static final String TAG = RNTwitterKitViewManager.class.getCanonicalName();

    public static final String REACT_CLASS = "RNTwitterKitViewManager";

    private
    @Nullable
    AbstractDraweeControllerBuilder mDraweeControllerBuilder;
    private final
    @Nullable
    Object mCallerContext;
    private
    @Nullable
    ThemedReactContext mThemedReactContext;

    public RNTwitterKitViewManager(
            AbstractDraweeControllerBuilder draweeControllerBuilder,
            Object callerContext) {
        mDraweeControllerBuilder = draweeControllerBuilder;
        mCallerContext = callerContext;
    }

    public RNTwitterKitViewManager() {
        // Lazily initialize as FrescoModule have not been initialized yet
        mDraweeControllerBuilder = null;
        mCallerContext = null;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    //private boolean hasFinished = false;

    //public static Tweet globalTweet = null;

    private Tweet createFakeTweet3(){
//        this.sharedTweet =  new TweetBuilder().setId(123L).setText(EXPECTED_TWEET_TEXT).build();
//        final TweetEntities entities = new TweetEntities(null, null, new ArrayList<MediaEntity>(),
//                null, null);
        Tweet sharedTweet = new TweetBuilder()
                .setId(3L)
                .setUser(new UserBuilder()
                        .setId(User.INVALID_ID)
                        .setName("name")
                        .setScreenName("namename")
                        .setVerified(false)
                        .build()
                )
                .setText("Preloaded text of a Tweet that couldn't be loaded.")
                .setCreatedAt("Wed Jun 06 20:07:10 +0000 2012")
                .build();
        return sharedTweet;
    }

    @Override
    public RNTwitterKitView createViewInstance(ThemedReactContext context) {
        TweetUiWrapper.init(context);
        //Tweet fakeTweet = createFakeTweet(context);
        Tweet fakeTweet = createFakeTweet3();
        RNTwitterKitView view = new RNTwitterKitView(context, context.getCurrentActivity());

//        int tweetId = 20;
//        Log.d(TAG, "createViewInstanceloadTweet = " + tweetId);
//        TweetUtilsWrapper.loadTweets(tweetId, new Callback<List<Tweet>>() {
//            @Override
//            public void success(Result<List<Tweet>> result) {
//                Log.d(TAG, "createViewInstanceloadTweet success");
//                for (Tweet tweet : result.data) {
//                    globalTweet = tweet;
//                    Log.d(TAG, "createViewInstancetweet = " + tweet.text);
//                }
//                hasFinished = true;
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//                Log.d(TAG, "createViewInstancefailure = " + exception.getMessage());
//            }
//        });

//        while(true){
//            Thread.yield();
//            if(hasFinished)
//                break;
//        }

        //try{Thread.sleep(10000);}catch(Exception e){};
        return view;
    }

    @ReactProp(name = "tweetid")
    public void setTweetId(RNTwitterKitView view, String strTweetId) {
        Log.d(TAG, "setTweetId");
        long tweetId = Long.parseLong(strTweetId);
        view.setTweetId(tweetId);
    }

    private Tweet createFakeTweet2(){
        String STRING_FORMAT = "yyyy-MM-dd HH:mm:ss";
        Gson gson = new GsonBuilder().setDateFormat(STRING_FORMAT).create();
        String hardcodedTweet = "{\"created_at\":\"Sat Sep 13 21:49:26 +0000 2014\",\"display_text_range\":[0,51],\"entities\":{\"hashtags\":[],\"media\":[{\"id\":510908130125828096,\"id_str\":\"510908130125828096\",\"media_url\":\"http://pbs.twimg.com/media/BxccPMkCUAAt-EW.jpg\",\"media_url_https\":\"https://pbs.twimg.com/media/BxccPMkCUAAt-EW.jpg\",\"sizes\":{\"large\":{\"h\":768,\"resize\":\"fit\",\"w\":1024},\"medium\":{\"h\":450,\"resize\":\"fit\",\"w\":600},\"small\":{\"h\":255,\"resize\":\"fit\",\"w\":340},\"thumb\":{\"h\":150,\"resize\":\"crop\",\"w\":150}},\"source_status_id\":0,\"type\":\"photo\",\"display_url\":\"pic.twitter.com/NGaemPYZUM\",\"expanded_url\":\"https://twitter.com/endform/status/510908133917487104/photo/1\",\"url\":\"http://t.co/NGaemPYZUM\",\"indices\":[29,51]}],\"symbols\":[],\"urls\":[],\"user_mentions\":[]},\"extended_entities\":{\"media\":[{\"id\":510908130125828096,\"id_str\":\"510908130125828096\",\"media_url\":\"http://pbs.twimg.com/media/BxccPMkCUAAt-EW.jpg\",\"media_url_https\":\"https://pbs.twimg.com/media/BxccPMkCUAAt-EW.jpg\",\"sizes\":{\"large\":{\"h\":768,\"resize\":\"fit\",\"w\":1024},\"medium\":{\"h\":450,\"resize\":\"fit\",\"w\":600},\"small\":{\"h\":255,\"resize\":\"fit\",\"w\":340},\"thumb\":{\"h\":150,\"resize\":\"crop\",\"w\":150}},\"source_status_id\":0,\"type\":\"photo\",\"display_url\":\"pic.twitter.com/NGaemPYZUM\",\"expanded_url\":\"https://twitter.com/endform/status/510908133917487104/photo/1\",\"url\":\"http://t.co/NGaemPYZUM\",\"indices\":[29,51]}]},\"favorite_count\":23,\"favorited\":false,\"id\":510908133917487104L,\"id_str\":\"510908133917487104\",\"in_reply_to_status_id\":0,\"in_reply_to_user_id\":0,\"lang\":\"en\",\"possibly_sensitive\":false,\"quoted_status_id\":0,\"retweet_count\":0,\"retweeted\":false,\"source\":\"u003ca hrefu003d\"http://twitter.com/download/android\" relu003d\"nofollow\"u003eTwitter for Androidu003c/au003e\",\"text\":\"New bike! Rides like a dream http://t.co/NGaemPYZUM\",\"truncated\":false,\"user\":{\"contributors_enabled\":false,\"created_at\":\"Mon Nov 30 23:00:16 +0000 2009\",\"default_profile\":false,\"default_profile_image\":false,\"description\":\"Not checking this often. Send me an email instead endform@gmailnn(((((((((AndrÃƒÆ’Ã‚Â©⏎)))))))))) n Founder of my bad attitude n All tweets are legal advice\",\"entities\":{\"description\":{\"urls\":[]},\"url\":{\"urls\":[{\"display_url\":\"twitter.com/endform\",\"expanded_url\":\"https://twitter.com/endform\",\"url\":\"https://t.co/tttWrJYNwa\",\"indices\":[0,23]}]}},\"favourites_count\":5955,\"follow_request_sent\":false,\"followers_count\":684,\"friends_count\":973,\"geo_enabled\":true,\"id\":93731096,\"id_str\":\"93731096\",\"is_translator\":false,\"lang\":\"en\",\"listed_count\":27,\"location\":\"Berkeley, CA\",\"name\":\"AndrÃƒÆ’Ã‚Â©⏎☠️\",\"profile_background_color\":\"696969\",\"profile_background_image_url\":\"http://pbs.twimg.com/profile_background_images/682984361/4fe79d9def960180abbd686ba7d7c81b.jpeg\",\"profile_background_image_url_https\":\"https://pbs.twimg.com/profile_background_images/682984361/4fe79d9def960180abbd686ba7d7c81b.jpeg\",\"profile_background_tile\":false,\"profile_banner_url\":\"https://pbs.twimg.com/profile_banners/93731096/1473028022\",\"profile_image_url\":\"http://pbs.twimg.com/profile_images/659160260846817280/fJhiy36p_normal.jpg\",\"profile_image_url_https\":\"https://pbs.twimg.com/profile_images/659160260846817280/fJhiy36p_normal.jpg\",\"profile_link_color\":\"4A913C\",\"profile_sidebar_border_color\":\"000000\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"0084B4\",\"profile_use_background_image\":false,\"protected\":false,\"screen_name\":\"endform\",\"show_all_inline_media\":false,\"statuses_count\":3823,\"time_zone\":\"Pacific Time (US u0026 Canada)\",\"url\":\"https://t.co/tttWrJYNwa\",\"utc_offset\":-25200,\"verified\":false},\"withheld_copyright\":false}";
        Tweet tweet = gson.fromJson(hardcodedTweet.toString(), Tweet.class);
        return tweet;
    }
    
    private Tweet createFakeTweet(ThemedReactContext context){

        User user = new User(
                false,//boolean contributorsEnabled,
                "Sun Feb 06 09:54:11 +0000 2011",//String createdAt,
                false,//boolean defaultProfile,
                false,//boolean defaultProfileImage,
                "",//String description,
                "",//String emailAddress,
                null,//UserEntities entities,
                1,//int favouritesCount,
                false,//boolean followRequestSent,
                1,//int followersCount,
                1,//int friendsCount,
                true,//boolean geoEnabled,
                1,//long id,
                "248135355",//String idStr,
                false,//boolean isTranslator,
                "en",//String lang,
                1,//int listedCount,
                "",//String location,
                "                          ",//aici, String name,
                "ABB8C2",//String profileBackgroundColor,
                "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//String profileBackgroundImageUrl,
                "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//String profileBackgroundImageUrlHttps,
                true,//boolean profileBackgroundTile,
                "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//String profileBannerUrl,
                "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//String profileImageUrl,
                "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//String profileImageUrlHttps,
                "7FDBB6",//String profileLinkColor,
                "EEEEEE",//String profileSidebarBorderColor,
                "EEEEEE",//String profileSidebarFillColor,
                "EEEEEE",//String profileTextColor,
                true,//boolean profileUseBackgroundImage,
                false,//boolean protectedUser,
                "",//String screenName,
                false,//boolean showAllInlineMedia,
                null,//Tweet status,
                1,//int statusesCount,
                "Pacific Time (US \u0026 Canada)",//String timeZone,
                "https://t.co/Ul0WySa1he",//String url,
                -25200,//int utcOffset,
                false,//boolean verified,
                null,//List<String> withheldInCountries,
                ""//String withheldScope
                );
        List< UrlEntity > urls = new ArrayList<>();
        List< MentionEntity > userMentions  = new ArrayList<>();
        List< MediaEntity > media  = new ArrayList<>();
        List< HashtagEntity > hashtags  = new ArrayList<>();
        List< SymbolEntity > symbols  = new ArrayList<>();
        hashtags.add (new HashtagEntity("a",1,1));
        hashtags.add (new HashtagEntity("b",2,2));
        MediaEntity.Size large = new MediaEntity.Size(768,1024,"fit");
        MediaEntity.Size medium = new MediaEntity.Size(450,600,"fit");
        MediaEntity.Size small = new MediaEntity.Size(255,340,"fit");
        MediaEntity.Size thumb = new MediaEntity.Size(150,150,"crop");
        MediaEntity.Sizes sizes = new MediaEntity.Sizes(large,medium,small,thumb);
        MediaEntity mediaEntity = new MediaEntity(
                    "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//aici,http://pbs.twimg.com/media/C75rO5XVQAAdxYK.jpg,String url,
                    "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//aici,http://pbs.twimg.com/media/C75rO5XVQAAdxYK.jpg,String expandedUrl,
                    "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//aici,http://pbs.twimg.com/media/C75rO5XVQAAdxYK.jpg,String displayUrl,
                    29,//int start,
                    51,//int end,
                    846231683653255168L,//long id,
                    "846231683653255168",//String idStr,
                    "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//aici,http://pbs.twimg.com/media/C75rO5XVQAAdxYK.jpg,String mediaUrl,
                    "http://fruits.com/wp-content/uploads/2014/10/Fruits_Thumbnails_apples_1.jpg",//aici,http://pbs.twimg.com/media/C75rO5XVQAAdxYK.jpg,String mediaUrlHttps,
                    sizes,//MediaEntity.Sizes sizes,
                    0,//long sourceStatusId,
                    "pic.twitter.com/bc466VL8sh",//String sourceStatusIdStr,
                    "photo", //String type,
                    null,//VideoInfo videoInfo,
                    ""//String altText
                    );
        media.add(mediaEntity);
        TweetEntities entities = new TweetEntities(
                urls,
                userMentions,
                media,
                hashtags,
                symbols);

        List<UrlEntity> eUrlEntities = new ArrayList<>();
        List<MentionEntity> eMentionEntitiers = new ArrayList<>();
        List<MediaEntity> eMediaEntities = new ArrayList<>();
        List<HashtagEntity> eHashtagEntities = new ArrayList<>();
        List<SymbolEntity> eSymbolEntities = new ArrayList<>();
        TweetEntities extendedEntities = new TweetEntities(
                eUrlEntities,
                eMentionEntitiers,
                media,
                eHashtagEntities,
                eSymbolEntities
        );

        Tweet tweet = new Tweet(null,//Coordinates coordinates,
                "Wed Jun 06 20:07:10 +0000 2012",//String createdAt,
                null, // Object currentUserRetweet,
                entities, //TweetEntities entities,
                entities,// TweetEntities extendedEtities,
                1, // Integer favoriteCount,
                false, //boolean favorited,
                "filterLevel",// String filterLevel,
                1,// long id,
                "idStr",// String idStr,
                "inReplyToScreenName",//String inReplyToScreenName,
                1,// long inReplyToStatusId,
                "inReplyToStatusIdStr",// String inReplyToStatusIdStr,
                1,//long inReplyToUserId,
                "inReplyToUserIdStr",// String inReplyToUserIdStr,
                "en",// String lang,
                null,// Place place,
                false,//boolean possiblySensitive,
                null,//Object scopes,
                1,//long quotedStatusId,
                "quotedStatusIdStr",//String quotedStatusIdStr,
                null,//Tweet quotedStatus,
                1,//int retweetCount,
                false,//boolean retweeted,
                null,//Tweet retweetedStatus,
                "web",//String source,
                "loading",//String text,
                null,//List<Integer> displayTextRange,
                false,//boolean truncated,
                user,//User user,
                false,//boolean withheldCopyright,
                null,//List<String> withheldInCountries,
                "withheldScope",//String withheldScope,
                null//Card card
        );
        return tweet;
    }

}
