//
//  HomeViewController.m
//  HashTag Party
//
//  Created by Pedro Salomão on 11/8/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "HomeViewController.h"
#import "HSTGButton.h"
#import "NovidadesViewController.h"
#import "HSTGLabel.h"

@interface HomeViewController ()

@property (weak, nonatomic) IBOutlet UIButton *meusDadosButton;
@property (weak, nonatomic) IBOutlet UIButton *wifiPartyButton;
@property (weak, nonatomic) IBOutlet UIView *navigationHeaderView;
@property (weak, nonatomic) IBOutlet HSTGLabel *nameLabel;

@property (strong, nonatomic) IBOutletCollection(HSTGButton) NSArray *menuButtons;
//@property (weak, nonatomic) IBOutlet FBProfilePictureView *fbProfileView;

@end

@implementation HomeViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.meusDadosButton.titleLabel.textAlignment = NSTextAlignmentCenter;
    self.wifiPartyButton.titleLabel.textAlignment = NSTextAlignmentCenter;

    self.navigationController.navigationBarHidden = NO;
    self.navigationItem.hidesBackButton = YES;
    
    self.navigationHeaderView.frame = CGRectMake(8, 6, 304, 33);
    
    NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    self.nameLabel.text = [NSString stringWithFormat:@"Olá, %@", [prefs objectForKey:@"FBUSER"]];
}

#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    self.title = @" ";
}

@end
