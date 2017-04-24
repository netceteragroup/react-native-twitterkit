//
//  RNTwitterKitView.m
//  RNTwitterkit
//
//  Created by Andi Anton on 28/03/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNTwitterKitView.h"
#import <TwitterKit/TwitterKit.h>

@interface RNTwitterKitView()

@property (nonatomic, strong) TWTRAPIClient *client;
@property (nonatomic, strong) TWTRTweetView *tweetView;
@property (nonatomic) BOOL twitterDidLoad;

@end


@implementation RNTwitterKitView {
    //TWTRTweetView *tweetView;
    NSString *strTweetID;
    UIActivityIndicatorView *activityIndicator;
    NSTimer *timer;
}

- (instancetype)init
{
    self = [super init];
    
    self.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    
    self.tweetView = [[TWTRTweetView alloc] init];
    self.tweetView.frame = self.frame;
    self.tweetView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    
    self.client = [[TWTRAPIClient alloc] init];
    
    activityIndicator = [[UIActivityIndicatorView alloc] initWithFrame:CGRectZero];
    
    activityIndicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyleGray;
    
    activityIndicator.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    
    /*
    NSLayoutConstraint *xCenterConstraint = [NSLayoutConstraint constraintWithItem:activityIndicator attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterX multiplier:1.0 constant:0];
    [self addConstraint:xCenterConstraint];
    
    NSLayoutConstraint *yCenterConstraint = [NSLayoutConstraint constraintWithItem:activityIndicator attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterY multiplier:1.0 constant:0];
    [self addConstraint:yCenterConstraint];
    
    NSLayoutConstraint *widthConstraint = [NSLayoutConstraint constraintWithItem:activityIndicator attribute:NSLayoutAttributeWidth relatedBy:NSLayoutRelationEqual toItem:nil attribute:NSLayoutAttributeNotAnAttribute multiplier:1.0 constant:32.0f];
    [self addConstraint:widthConstraint];
    
    NSLayoutConstraint *heightConstraint = [NSLayoutConstraint constraintWithItem:activityIndicator attribute:NSLayoutAttributeHeight relatedBy:NSLayoutRelationEqual toItem:nil attribute:NSLayoutAttributeNotAnAttribute multiplier:1.0 constant:32.0f];
    [self addConstraint:heightConstraint];
     */
    
    [self addSubview:self.tweetView];
    [self addSubview:activityIndicator];
    [activityIndicator startAnimating];
    
    self.twitterDidLoad = NO;
    
    [self createFrameInfoTimer];
    return self;
}


-(void)createFrameInfoTimer {
    timer = [NSTimer scheduledTimerWithTimeInterval:.01f
                                             target:self
                                           selector:@selector(checkFrameInfoDone:)
                                           userInfo:nil
                                            repeats:YES];
}


//////frame handler/////////
-(void)checkFrameInfoDone:(NSTimer*)timer {
    if(self.twitterDidLoad){
        if(self.frame.size.width > 0 || self.frame.size.height > 0){
            if([timer isValid]){
                [timer invalidate];
            }
            
            [self adjustViewsLayout];
        }
    }
}


-(void)adjustViewsLayout {
    //adjust twitter view
    CGSize desiredSize = [self.tweetView sizeThatFits:CGSizeMake(self.frame.size.width, CGFLOAT_MAX)];
    NSLog(@"SIZE = %f   %f", desiredSize.width, desiredSize.height);
    
    CGRect selfViewRect = CGRectMake(self.frame.origin.x, self.frame.origin.y, desiredSize.width, desiredSize.height);
    
    self.frame = selfViewRect;
}


- (void)setTWEETID:(NSString *)tweetID {
    NSLog(@"tweetID = %@\n", tweetID);
    [self loadTweetWithID:tweetID];
}


-(void)loadTweetWithID:(NSString*)tweetID {
    
    [self.client loadTweetWithID:tweetID completion:^(TWTRTweet *tweet, NSError *error)
    {
        if (tweet) {
            /*
            CGSize desiredSize = [self.tweetView sizeThatFits:CGSizeMake(self.frame.size.width, CGFLOAT_MAX)];
            NSLog(@"SIZE = %f   %f", desiredSize.width, desiredSize.height);
            
            CGRect selfViewRect = CGRectMake(self.frame.origin.x, self.frame.origin.y, desiredSize.width, self.frame.size.width-desiredSize.height);
            
            self.frame = selfViewRect;
            */
            
            [self.tweetView configureWithTweet:tweet];
            [activityIndicator stopAnimating];
            activityIndicator.hidden = YES;
            self.twitterDidLoad = YES;
        } else {
            NSLog(@"Failed to load tweet: %@", [error localizedDescription]);
            [activityIndicator stopAnimating];
            activityIndicator.hidden = YES;
        }
    }];
}


@end
