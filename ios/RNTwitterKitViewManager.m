#import <React/RCTShadowView.h>
#import <React/RCTUIManager.h>
#import <TwitterKit/TwitterKit.h>

#import "RNTwitterKitViewManager.h"
#import "RNTwitterKitView.h"
#import "RNTweetShadowView.h"

@implementation RNTwitterKitViewManager

RCT_EXPORT_MODULE(TweetView);
RCT_REMAP_VIEW_PROPERTY(tweetid, TWEETID, NSString);
RCT_REMAP_VIEW_PROPERTY(showActionButtons, SHOWACTIONBUTTONS, BOOL);
RCT_REMAP_VIEW_PROPERTY(showBorder, SHOWBORDER, BOOL);
RCT_REMAP_VIEW_PROPERTY(primaryTextColor, PRIMARYTEXTCOLOR, NSNumber);
RCT_REMAP_VIEW_PROPERTY(linkTextColor, LINKTEXTCOLOR, NSNumber);
RCT_REMAP_VIEW_PROPERTY(tweetStyle, TWEETSTYLE, NSString);
RCT_REMAP_VIEW_PROPERTY(tweetTheme, TWEETTHEME, NSString);
RCT_REMAP_VIEW_PROPERTY(backgroundColor, BACKGROUNDCOLOR, NSNumber);

@synthesize bridge = _bridge;

- (UIView *)view
{
    RNTwitterKitView *view = [[RNTwitterKitView alloc] init];
    view.delegate = self;
    view.twitterAPIClient = [self twitterAPIClient];
    
    return view;
}



- (RCTShadowView *)shadowView
{
    return [RNTweetShadowView new];
}

- (void)tweetView:(RNTwitterKitView *)view requestsResize:(CGSize)newSize
{
    
    //getting the tweet view
    UIView *tweetView = view;
    
    //create a new size fot the tweet view
    CGSize newTweetViewSize = newSize;
    
    
    NSLog(@"new tweet size: %f,%f", newTweetViewSize.width, newTweetViewSize.height);
    //getting the UI manager and set the intrinsic content size of the tweet view
    RCTUIManager *UIManager = [self.bridge uiManager];
    
    [UIManager setSize:newTweetViewSize forView:tweetView];
}

- (TWTRAPIClient *)twitterAPIClient
{
    static TWTRAPIClient *client = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        client = [[TWTRAPIClient alloc] init];
    });
    
    return client;
}

@end
