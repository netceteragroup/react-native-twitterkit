//
//  RNTwitterKitView.h
//  RNTwitterkit
//
//  Created by Andi Anton on 28/03/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

//#import "RCTView.h"
#import <Foundation/Foundation.h>
#import <React/RCTView.h>
#import <React/RCTWebView.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTUtils.h>
#import <React/UIView+React.h>
#import <TwitterKit/TwitterKit.h>

@class RNTwitterKitView;

@protocol RNTwitterKitViewDelegate <NSObject>
@optional

- (void)tweetView:(RNTwitterKitView *)view didChangeIntrinsicSize:(CGSize)newSize;
@end

@interface RNTwitterKitView : RCTView<TWTRTweetViewDelegate>

@property(nonatomic, weak) id<RNTwitterKitViewDelegate> delegate;
@end
