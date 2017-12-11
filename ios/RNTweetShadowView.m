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


- (CGSize)computeIntrinsicSize:(CGFloat)width
{
    static CGSize defaultSize;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        dispatch_sync(dispatch_get_main_queue(), ^{
            TWTRTweetView *view = [self tweetViewForMeasuring];
            defaultSize = [view sizeThatFits:CGSizeMake(width, CGFLOAT_MAX)]; // 200 is the minimum width required for a tweet
            defaultSize.width = UIViewNoIntrinsicMetric;
        });
    });
    
    return defaultSize;
}

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.intrinsicContentSize = [self computeIntrinsicSize:200];
    }
    return self;
}

@end
