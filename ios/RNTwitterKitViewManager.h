//
//  RNTwitterKitViewManager.h
//  RNTwitterkit
//
//  Created by Andi Anton on 28/03/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTViewManager.h>
#import "RNTwitterKitView.h"
#import <React/RCTShadowView.h>

// TODO:
// - move tweet view delegate methods here
// - remove timer-based check on layout change
// - specify load error message via props
@interface RNTwitterKitViewManager : RCTViewManager

@property (nonatomic, strong) RCTShadowView *sv;

@end
