//
//  LoginViewController.m
//  HashTag Party
//
//  Created by Pedro Salomão on 11/15/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "LoginViewController.h"
#import "AppDelegate.h"

@interface LoginViewController ()

@end

@implementation LoginViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationController.navigationBarHidden = YES;

    AppDelegate *appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    if(appDelegate.isFacebookLoggedIn) {
        [self performSegueWithIdentifier:@"goToMain" sender:self];
    }
}

- (IBAction)facebookButtonPressed:(UITapGestureRecognizer *)sender
{
    // Open a session showing the user the login UI
    // You must ALWAYS ask for public_profile permissions when opening a session
//    [FBSession openActiveSessionWithReadPermissions:@[@"public_profile"]
//                                       allowLoginUI:YES
//                                  completionHandler:
//     ^(FBSession *session, FBSessionState state, NSError *error) {
//         
//         [[FBRequest requestForMe] startWithCompletionHandler:^(FBRequestConnection *connection, NSDictionary<FBGraphUser> *FBuser, NSError *error)
//         {
//             if (error) {
//                 // Handle error
//             } else {
//                 NSString *userName = [FBuser name];
//                 NSString *profileId = [FBuser objectID];
//                 
//                 NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
//                 [prefs setValue:userName forKey:@"FBUSER"];
//                 [prefs setValue:profileId forKey:@"FBID"];
//                 
//                 dispatch_async(dispatch_get_main_queue(), ^{
//                     [self performSegueWithIdentifier:@"goToMain" sender:self];
//                 });
//             }
//         }];
//     }];
}

@end
