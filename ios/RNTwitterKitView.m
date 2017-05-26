//
//  RNTwitterKitView.m
//  RNTwitterkit
//
//  Created by Andi Anton on 28/03/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNTwitterKitView.h"
#import <TwitterKit/TwitterKit.h>

#define UIColorFromRGB(rgbValue) \
[UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 \
green:((float)((rgbValue & 0x00FF00) >>  8))/255.0 \
blue:((float)((rgbValue & 0x0000FF) >>  0))/255.0 \
alpha:1.0]

@interface RNTwitterKitView()

//tweetID
@property (nonatomic, strong) NSString *tweetIDString;

//views
@property (nonatomic, strong) TWTRTweetView *tweetView;
@property (nonatomic) BOOL twitterDidLoadWithSuccess;
@property (nonatomic) BOOL twitterDidFailWithError;
@property (nonatomic, weak) UILabel *twitLoadErrorMessageLabel;
@property (nonatomic, weak) UIButton *twitReloadButton;

//action buttons
@property (nonatomic) BOOL actionButtonsFlagChanged;
@property (nonatomic) BOOL showActionButtons;

//show border
@property (nonatomic) BOOL showBorderFlagChanged;
@property (nonatomic) BOOL showBorder;

//primary text color
@property (nonatomic) BOOL primaryTextColorFlagChanged;
@property (nonatomic) NSNumber *primaryTextColor;

//link text color
@property (nonatomic) BOOL linkTextColorFlagChanged;
@property (nonatomic) NSNumber *linkTextColor;

//tweet style
@property (nonatomic) BOOL tweetStyleFlagChanged;
@property (nonatomic) NSString *tweetStyle;

//tweet theme
@property (nonatomic) BOOL tweetThemeFlagChanged;
@property (nonatomic) NSString *tweetTheme;

//background color
@property (nonatomic) BOOL backgroundColorFlagChanged;
@property (nonatomic) NSNumber *tweetBackgroundColor;

//tweeter height after loading tweet
@property (nonatomic) NSNumber *tweetHeight;
@property (nonatomic) NSNumber *tweetWidth;

@property (nonatomic) BOOL posponedResize;
@end


@implementation RNTwitterKitView {
    NSString *strTweetID;
    UIActivityIndicatorView *activityIndicator;
    NSTimer *timer;
    NSTimer *tweetHeightChangedTimer;
}

#pragma mark - init
- (instancetype)init
{
    self = [super init];
    [self setupViews];
    self.posponedResize = NO;
    return self;
}


#pragma mark - JS properties setter methods
///////////////// RN PROPERTIES //////////////
//tweetID
- (void)setTWEETID:(NSString *)tweetID
{
    NSLog(@"tweetID = %@\n", tweetID);
    self.tweetIDString = [[NSString alloc] initWithString:tweetID];
    [self loadTweetWithID:self.tweetIDString];
}

//actionButtons
- (void)setSHOWACTIONBUTTONS:(BOOL)showActionButtons
{
    self.actionButtonsFlagChanged = YES;
    self.showActionButtons = showActionButtons;
}

//showBorder
- (void)setSHOWBORDER:(BOOL)showBorder
{
    self.showBorderFlagChanged = YES;
    self.showBorder = showBorder;
}

//primaryTextColor
- (void)setPRIMARYTEXTCOLOR:(NSNumber *)primaryTextColor
{
    self.primaryTextColorFlagChanged = YES;
    self.primaryTextColor = primaryTextColor;
}

//linkTextColor
- (void)setLINKTEXTCOLOR:(NSNumber *)linkTextColor
{
    self.linkTextColorFlagChanged = YES;
    self.linkTextColor = linkTextColor;
}

//tweetStyle
- (void)setTWEETSTYLE:(NSString *)tweetStyle
{
    self.tweetStyleFlagChanged = YES;
    self.tweetStyle = tweetStyle;
}

//tweetTheme
- (void)setTWEETTHEME:(NSString *)tweetTheme
{
    self.tweetThemeFlagChanged = YES;
    self.tweetTheme = tweetTheme;
}

//background color
- (void)setBACKGROUNDCOLOR:(NSNumber *)backgroundColor
{
    self.backgroundColorFlagChanged = YES;
    self.tweetBackgroundColor = backgroundColor;
}


#pragma mark - views layout
/////////////////////// VIEW LAYOUT /////////////
- (void)setupViews
{
    [self createTwitterView];
    [self createActivityIndicator];
}


- (void)adjustViewsLayout
{
    if (self.twitterDidLoadWithSuccess) {
        [self adjustViewsLayoutTweetLoadSuccess];
    } else if(self.twitterDidFailWithError) {
        [self adjustViewsLayoutTweetLoadFail];
    }
}

- (void)reactSetFrame:(CGRect)frame
{
    NSLog(@"React sets frame: %f, %f", frame.size.width, frame.size.height);
    [super reactSetFrame:frame];
    
    [self layoutIfNeeded];
    
    if (self.posponedResize) {
        self.posponedResize = NO;
        
        CGSize desiredSize = [self.tweetView sizeThatFits:CGSizeMake(self.bounds.size.width, CGFLOAT_MAX)];
        NSLog(@"MEASURED SIZE = %f   %f", desiredSize.width, desiredSize.height);
        [self sendSizeChange:desiredSize];

    }
}

//LOAD WITH SUCCESS----
- (void)adjustViewsLayoutTweetLoadSuccess
{
    //adjust twitter view
    [self applyViewProperties];
    
    self.tweetView.hidden = NO;
    
    CGFloat width = self.frame.size.width;
    if (width == 0) {
        self.posponedResize = YES;
    } else {
        CGSize desiredSize = [self.tweetView sizeThatFits:CGSizeMake(width, CGFLOAT_MAX)];
        NSLog(@"MEASURED SIZE = %f   %f", desiredSize.width, desiredSize.height);
        [self sendSizeChange:desiredSize];
    }

    
//    if ([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"twitter://"]]) {
//        // Twitter app is installed
//        desiredSize.height += 30;
//    }
    
    //adjust self frame
//    CGRect selfViewRect = CGRectMake(self.frame.origin.x, self.frame.origin.y, desiredSize.width, desiredSize.height);
//    self.frame = selfViewRect;
    
    //apply properties from the JS
    
    
}

//LOAD FAILS----
- (void)adjustViewsLayoutTweetLoadFail
{
    
    self.backgroundColor = [UIColor whiteColor];
    
    //message label
    NSUInteger const messageHeight = 20;
    NSUInteger const messageYOffset = 26;
    UILabel *twitLoadErrorMessage = [[UILabel alloc] init];
    CGRect messageFrame = CGRectMake(0, self.frame.size.height - messageHeight - messageYOffset, self.frame.size.width, messageHeight);
    twitLoadErrorMessage.frame = messageFrame;
    twitLoadErrorMessage.textAlignment = NSTextAlignmentCenter;
    twitLoadErrorMessage.font = [UIFont fontWithName:@"Helvetica" size:15];
    twitLoadErrorMessage.text = @"Das Element konnte nicht geladen werden.";
    twitLoadErrorMessage.textColor = UIColorFromRGB(0x9a9a9a);
    self.twitLoadErrorMessageLabel = twitLoadErrorMessage;
    
    //reload button
    NSUInteger const buttonWidth = 32;
    NSUInteger const buttonHeight = 32;
    NSUInteger const halfParentViewWidth = self.frame.size.width/2;
    NSUInteger const halfParentViewHeight = self.frame.size.height/2;
    
    //reload image
    UIImage *reloadImg = [UIImage imageNamed:@"tweet_refresh_icon"];
    
    UIButton *button = [[UIButton alloc] init];
    CGRect buttonFrame = CGRectMake((halfParentViewWidth-(buttonWidth/2)), (halfParentViewHeight-(buttonHeight/2)), buttonWidth, buttonHeight);
    button.frame = buttonFrame;
    
    [button setImage:reloadImg forState:UIControlStateNormal];
    [button setImage:reloadImg forState:UIControlStateSelected];
    
    [button addTarget:self action:@selector(reloadTwitterButtonClicked:) forControlEvents:UIControlEventTouchUpInside];
    self.twitReloadButton = button;
    
    [self addSubview:self.twitLoadErrorMessageLabel];
    [self addSubview:self.twitReloadButton];
    
}


#pragma mark - Tweeter properties apply
- (void)applyViewProperties
{
    //default values
    self.tweetView.showActionButtons = YES;
    
    if (self.actionButtonsFlagChanged) {
        self.tweetView.showActionButtons = self.showActionButtons;
    }
    
    if (self.showBorderFlagChanged) {
        self.tweetView.showBorder = self.showBorder;
    }
    
    if (self.primaryTextColorFlagChanged) {
        self.tweetView.primaryTextColor = UIColorFromRGB([self.primaryTextColor unsignedIntegerValue]);
    }
    
    if (self.linkTextColorFlagChanged) {
        self.tweetView.linkTextColor = UIColorFromRGB([self.linkTextColor unsignedIntegerValue]);
    }
    
    // TODO remove support for now as it needs to recreate the view
//    if(self.tweetStyleFlagChanged) {
//        if([[self.tweetStyle lowercaseString] isEqualToString:@"twtrtweetviewstyleregular"]){
//            self.tweetView.style = TWTRTweetViewStyleRegular;
//        } else {
//            self.tweetView.style = TWTRTweetViewStyleCompact;
//        }
//    }
    
    if (self.tweetThemeFlagChanged) {
        if([[self.tweetTheme lowercaseString] isEqualToString:@"twtrtweetviewthemelight"])
        {
            self.tweetView.theme = TWTRTweetViewThemeLight;
        } else {
            self.tweetView.theme = TWTRTweetViewThemeDark;
        }
    }
    
    if (self.backgroundColorFlagChanged) {
        self.tweetView.backgroundColor = UIColorFromRGB([self.tweetBackgroundColor unsignedIntegerValue]);
    }
    
//    if ([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"twitter://"]]) {
//        // Twitter app is installed
//    } else {
//        // Twitter app is NOT installed
//        self.tweetView.showActionButtons = NO;
//    }
    
    
}



- (void)sendSizeChange:(CGSize)size
{
    id<RNTwitterKitViewDelegate> delegate = self.delegate; // weak -> strong
    if ([delegate respondsToSelector:@selector(tweetView:requestsResize:)]) {
        [delegate tweetView:self requestsResize:size];
    }
}

#pragma mark - IBActions
- (IBAction)reloadTwitterButtonClicked:(id)sender
{
    
    //hide tweet view when loading
    self.tweetView.hidden = YES;
    
    //remove error message and reload button
    [self.twitLoadErrorMessageLabel removeFromSuperview];
    [self.twitReloadButton removeFromSuperview];
    self.backgroundColor = [UIColor whiteColor];
    
    //recreate load success error flags
    self.twitterDidLoadWithSuccess = NO;
    self.twitterDidFailWithError = NO;
    
    //recreate info timer for readjusting layout
    
    NSLog(@"\nreload tweet");
    
    [self loadTweetWithID:self.tweetIDString];
}


#pragma mark - Tweetter object
///////////////// TWEETER OBJECT //////////////////
- (void)createTwitterView
{
    TWTRTweetView *tweetView = [[TWTRTweetView alloc] init];
    self.tweetView = tweetView;
    self.tweetView.translatesAutoresizingMaskIntoConstraints = NO;
    self.twitterDidLoadWithSuccess = NO;
    self.tweetView.hidden = YES;
 
    [self addSubview:self.tweetView];

    NSArray *hConstraints =
    [NSLayoutConstraint constraintsWithVisualFormat:@"|[tweetView]|"
                                            options:0
                                            metrics:nil
                                              views:NSDictionaryOfVariableBindings(tweetView)];
    [self addConstraints:hConstraints];
    
    NSArray *vConstraints =
    [NSLayoutConstraint constraintsWithVisualFormat:@"V:|[tweetView]|"
                                            options:0
                                            metrics:nil
                                              views:NSDictionaryOfVariableBindings(tweetView)];
    [self addConstraints:vConstraints];
    
    [NSLayoutConstraint activateConstraints:hConstraints];
    [NSLayoutConstraint activateConstraints:vConstraints];

}


- (void)loadTweetWithID:(NSString*)tweetID
{
    
    activityIndicator.hidden = NO;
    [activityIndicator startAnimating];
    
    [self.twitterAPIClient loadTweetWithID:tweetID completion:^(TWTRTweet *tweet, NSError *error) {
        if (tweet) {
            [self.tweetView configureWithTweet:tweet];
            self.twitterDidLoadWithSuccess = YES;
            [activityIndicator stopAnimating];
            [self applyViewProperties];
            [self adjustViewsLayout];
            activityIndicator.hidden = YES;
        } else {
            NSLog(@"Failed to load tweet: %@", [error localizedDescription]);
            [activityIndicator stopAnimating];
            activityIndicator.hidden = YES;
            self.twitterDidFailWithError = YES;
        }
    }];
}


#pragma mark - other
//////////// OTHER ///////////////
- (void)createActivityIndicator
{
    
    activityIndicator = [[UIActivityIndicatorView alloc] initWithFrame:CGRectZero];
    activityIndicator.translatesAutoresizingMaskIntoConstraints = NO;
    activityIndicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyleGray;
    activityIndicator.hidden = YES;
    
    [self addSubview:activityIndicator];

    UIView *parent = self;
    
    [self addConstraint:
     [NSLayoutConstraint constraintWithItem:activityIndicator
                                  attribute:NSLayoutAttributeCenterX
                                  relatedBy:NSLayoutRelationEqual
                                     toItem:parent
                                  attribute:NSLayoutAttributeCenterX
                                 multiplier:1
                                   constant:0]];
    [self addConstraint:
     [NSLayoutConstraint constraintWithItem:activityIndicator
                                  attribute:NSLayoutAttributeCenterY
                                  relatedBy:NSLayoutRelationEqual
                                     toItem:parent
                                  attribute:NSLayoutAttributeCenterY
                                 multiplier:1
                                   constant:0]];
}

@end
