//
//  RNTwitterKitViewManager.m
//  RNTwitterkit
//
//  Created by Andi Anton on 28/03/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNTwitterKitViewManager.h"
#import "RNTwitterKitView.h"


@implementation RNTwitterKitViewManager

RCT_EXPORT_MODULE(TweetView);
RCT_REMAP_VIEW_PROPERTY(tweetid, TWEETID, NSString);

@synthesize bridge = _bridge;

- (UIView *)view
{
    return [[RNTwitterKitView alloc] init];
}

@end
