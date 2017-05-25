#import "RNTweetShadowView.h"

@implementation RNTweetShadowView

- (TWTRTweetView *)tweetViewForMeasuring
{
    static TWTRTweetView *tweetView = nil;;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        tweetView = [[TWTRTweetView alloc] initWithTweet:nil style:TWTRTweetViewStyleCompact];
    });
    return tweetView;
}


- (CGSize)defaultIntrinsicSize
{
    static CGSize defaultSize;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        TWTRTweetView *view = [self tweetViewForMeasuring];
        defaultSize = [view sizeThatFits:CGSizeMake(200, CGFLOAT_MAX)]; // 200 is the minimum width required for a tweet
    });
    
    return defaultSize;
}

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.intrinsicContentSize = [self defaultIntrinsicSize];
    }
    return self;
}

@end
