package com.netcetera.reactnative.twitterkit;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
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

//fixed
//1. added progress bar
//2. it seems that 846231685750439936 default id is loaded from cache from time to time, investigate cache on twitter
//todo
//0. decide about refresh and error
//1. clean the code and use only one thread
//2. make one log class to be able to show or not show logs
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
public class TweetView extends RelativeLayout {

    private static final String TAG = TweetView.class.getCanonicalName();

    public TweetView(Context context, Activity activity) {
        super(context);
        LogUtils.d(TAG, "RNTwitterKitView");
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        initTweetContent(activity);
    }

    private void initTweetContent(Activity activity) {
        LogUtils.d(TAG, "initTweetContent");
        //TweetUiWrapper.init(activity);
        tweetMainContainer = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.tweet_container, null);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//ViewGroup.LayoutParams.WRAP_CONTENT);
        //tweetMainContainer.setLayoutParams(layoutParams);
        this.addView(tweetMainContainer);

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

    private RelativeLayout tweetMainContainer;

    private ImageView reloadButton;
    private RelativeLayout reloadContainer;
    private RelativeLayout errorContainer;
    private CustomTweetView tweetView;
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
                                           tweetView.setTweet(globalTweet);
                                       }
                                   });
                               } else if (counter == 2) {
                                   timer.cancel();
                                   tweetMainContainer.post(new Runnable() {
                                       @Override
                                       public void run() {
                                           setTweetView();
                                           //RNTwitterKitModule.sendToJs(getContext());
                                       }
                                   });
                               }
                               counter++;
                           }
                       }
                , 0, 1000);//Update text every second
    }

    private Tweet globalTweet = null;

    private void setTweetAndRequestLayout(Tweet tweet) {
        globalTweet = tweet;
        LogUtils.d(TAG, "setTweetView, tweet.text = " + tweet.text + ", tweet.id = " + tweet.id);
        tweetView.setTweet(null);
        requestLayoutWithDelay();
    }

    private void findViews(){
        loadingContainer = (RelativeLayout) tweetMainContainer.findViewById(R.id.loading_container);
        reloadContainer = (RelativeLayout) findViewById(R.id.reload_container);
        errorContainer = (RelativeLayout) tweetMainContainer.findViewById(R.id.error_container);
        tweetView = (CustomTweetView) findViewById(R.id.tweet_view);
        reloadButton = (ImageView)findViewById(R.id.reload_button);
    }

    private void setTweetView(){
        errorContainer.setVisibility(View.INVISIBLE);
        reloadContainer.setVisibility(View.INVISIBLE);
        loadingContainer.setVisibility(View.INVISIBLE);
        tweetView.setVisibility(View.VISIBLE);
        tweetView.requestLayout();
    }

    private void setLoadingView() {
        LogUtils.d(TAG, "setLoadingView");
        errorContainer.setVisibility(View.INVISIBLE);
        reloadContainer.setVisibility(View.INVISIBLE);
        tweetView.setVisibility(View.INVISIBLE);
        loadingContainer.setVisibility(View.VISIBLE);
    }

    private void setErrorView(){
        LogUtils.d(TAG, "setErrorView");
        reloadContainer.setVisibility(View.INVISIBLE);
        loadingContainer.setVisibility(View.INVISIBLE);
        tweetView.setVisibility(View.INVISIBLE);
        errorContainer.setVisibility(View.VISIBLE);
    }

    private int errorCounter = 0;

    private void setErrorOrReloadView() {
        LogUtils.d(TAG, "setErrorOrReloadView");
        if(errorCounter < 3){
            setReloadView();
        } else {
            setErrorView();
        }
        errorCounter ++;
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
                if(selectedTweet == null){
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

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {

            LogUtils.d(TAG, "width = " + getWidth() + ", height = " + getHeight() + ", left = " + getLeft() + ", top = " + getTop() + "right = " + getRight() + ", bottom = " + getBottom());

            measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

}
