//
//  RNTwitterKitViewManager.m
//  RNTwitterkit
//
//  Created by Andi Anton on 28/03/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNTwitterKitViewManager.h"
#import "RNTwitterKitView.h"
#import <React/RCTShadowView.h>
#import <React/RCTUIManager.h>
#import <TwitterKit/TwitterKit.h>


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
    [self createHeightChangeNotification];
    RNTwitterKitView *view = [[RNTwitterKitView alloc] init];
    view.delegate = self;
    view.twitterAPIClient = [self twitterAPIClient];
    
    return view;
}


- (void) createHeightChangeNotification {
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(receiveHeightChangeNotification:)
                                                 name:@"HeightChangeNotification"
                                               object:nil];
}

- (RCTShadowView *)shadowView
{
    self.sv = [RCTShadowView new];
    self.sv.intrinsicContentSize = CGSizeMake(300, 260);
    return self.sv;
}


- (void)receiveHeightChangeNotification:(NSNotification *)notification
{
    if ([[notification name] isEqualToString:@"HeightChangeNotification"]) {
        
        //getting the new height of tweet
        float tweetHeight = [[notification.object objectForKey:@"tweetHeight"] floatValue];
        
        //getting the tweet view
        UIView *tweetView = [notification.object objectForKey:@"tweeterView"];
        
        //create a new size fot the tweet view
        CGSize newTweetViewSize = CGSizeMake(300, tweetHeight);
        
        //getting the UI manager and set the intrinsic content size of the tweet view
        RCTUIManager *UIManager = [self.bridge uiManager];
        [UIManager setIntrinsicContentSize:newTweetViewSize forView:tweetView];
        
    }
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
