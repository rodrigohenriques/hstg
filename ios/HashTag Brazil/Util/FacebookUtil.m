//
//  FacebookUtil.m
//  HashTag Party
//
//  Created by Pedro Salomão on 11/20/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "FacebookUtil.h"
#import <FBSDKCoreKit/FBSDKCoreKit.h>
#import "AppDelegate.h"

@implementation FacebookUtil

+ (void)checkForCachedSession
{
    AppDelegate *appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    

    
    // Whenever a person opens the app, check for a cached session
//    if (FBSession.activeSession.state == FBSessionStateCreatedTokenLoaded) {
//        NSLog(@"Found a cached session");
//
//        // If there's one, just open the session silently, without showing the user the login UI
//        [FBSession openActiveSessionWithReadPermissions:@[@"public_profile"]
//                                           allowLoginUI:NO
//                                      completionHandler:^(FBSession *session, FBSessionState state, NSError *error) {
//                                          // Handler for session state changes
//                                          // This method will be called EACH time the session state changes,
//                                          // also for intermediate states and NOT just when the session open
//                                          
//                                      }];
//        appDelegate.isFacebookLoggedIn = YES;
//    } else {
//        appDelegate.isFacebookLoggedIn = NO;
//    }
}

@end
