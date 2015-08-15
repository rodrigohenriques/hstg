//
//  MeusDadosViewController.m
//  HashTag Brazil
//
//  Created by Pedro Salomão on 1/15/15.
//  Copyright (c) 2015 Pedro Salomão. All rights reserved.
//

#import "MeusDadosViewController.h"
#import "AppDelegate.h"
#import "NSString+Utils.h"
#import "ValidatorUtil.h"
#import <AFNetworking/AFHTTPRequestOperationManager.h>

#define PREFS_ALREADY_SENT @"ja_enviou"
#define PREFS_NAME @"nome"
#define PREFS_EMAIL @"email"

@interface MeusDadosViewController ()

@property (weak, nonatomic) IBOutlet UITextField *nameTextField;
@property (weak, nonatomic) IBOutlet UITextField *emailTextField;
@property (weak, nonatomic) IBOutlet UIView *dataFillView;
@property (weak, nonatomic) IBOutlet UIView *sendingView;
@property (weak, nonatomic) IBOutlet UILabel *resultLabel;
@property (weak, nonatomic) IBOutlet UIView *resultView;

@end

@implementation MeusDadosViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)viewWillAppear:(BOOL)animated
{
    [self updateScreen];
}

- (void)updateScreen
{
    NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    NSNumber *alreadySentObject = [prefs objectForKey:PREFS_ALREADY_SENT];
    if(!alreadySentObject || ![alreadySentObject boolValue]) {
        
        // Not sent
        self.dataFillView.hidden = NO;
        self.sendingView.hidden = YES;
        self.resultView.hidden = YES;

    } else {

        // Already sent
        self.dataFillView.hidden = YES;
        self.sendingView.hidden = YES;
        self.resultView.hidden = NO;
        
        NSString *name = [prefs objectForKey:PREFS_NAME];
        NSString *email = [prefs objectForKey:PREFS_EMAIL];

        self.resultLabel.text = [NSString stringWithFormat:@"Obrigado por se cadastrar!\n\nNome: %@\nE-mail: %@", name, email];
        
    }
}

- (IBAction)facebookConnectClicked:(UIButton *)sender {
    // Open a session showing the user the login UI
    // You must ALWAYS ask for public_profile permissions when opening a session
//    [FBSession openActiveSessionWithReadPermissions:@[@"public_profile", @"email"]
//                                       allowLoginUI:YES
//                                  completionHandler:
//     ^(FBSession *session, FBSessionState state, NSError *error) {
//         
//         [[FBRequest requestForMe] startWithCompletionHandler:^(FBRequestConnection *connection, NSDictionary<FBGraphUser> *FBuser, NSError *error)
//          {
//              if (error) {
//                  // Handle error
//              } else {
//                  NSString *userName = [FBuser name];
//                  //NSString *profileId = [FBuser objectID];
//                  NSString *email = [FBuser objectForKey:@"email"];
//                  
//                  dispatch_async(dispatch_get_main_queue(), ^{
//                      [self sendToServerName:userName email:email];
//                  });
//              }
//          }];
//     }];
}

- (IBAction)sendClicked:(id)sender {

    NSString *name = [self.nameTextField.text trim];
    NSString *email = [self.emailTextField.text trim];
    
    if(name.length > 0 && (email.length > 0 && [self isValidEmail:email])) {
        
        self.dataFillView.hidden = YES;
        self.sendingView.hidden = NO;
        
        [self.view endEditing:YES];
        
        [self sendToServerName:name email:email];
    }

}

- (BOOL)isValidEmail:(NSString *)email
{
    return [ValidatorUtil validaEmail:email];
}

- (void)sendToServerName:(NSString *)name email:(NSString *)email
{
    NSError *error = nil;
    NSString *url = @"http://www.usehstg.com.br/homologacao/webapi/?model=usuario&action=post";
    NSDictionary *parameters = @{@"nome" : name, @"email" : email};
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    NSMutableURLRequest *request = [[AFJSONRequestSerializer serializer] requestWithMethod:@"POST" URLString:url parameters:parameters error:&error];
    
    if(error) {
        
        self.dataFillView.hidden = NO;
        self.sendingView.hidden = YES;
        self.resultView.hidden = YES;
        
    } else {
        AFHTTPRequestOperation *operation = [manager HTTPRequestOperationWithRequest:request success:^(AFHTTPRequestOperation *operation, id responseObject) {
            NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
            [prefs setValue:[NSNumber numberWithBool:YES] forKey:PREFS_ALREADY_SENT];
            [prefs setValue:name forKey:PREFS_NAME];
            [prefs setValue:email forKey:PREFS_EMAIL];
            
            [self updateScreen];
            
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {

            self.dataFillView.hidden = NO;
            self.sendingView.hidden = YES;
            self.resultView.hidden = YES;
            
        }];

        NSMutableSet *acceptableContentTypes = [NSMutableSet setWithSet:operation.responseSerializer.acceptableContentTypes];
        [acceptableContentTypes addObject:@"text/html"];
        operation.responseSerializer.acceptableContentTypes = acceptableContentTypes;
        [[NSOperationQueue mainQueue] addOperation:operation];
    }
}

@end
