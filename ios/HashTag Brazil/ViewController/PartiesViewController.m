//
//  PartiesViewController.m
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 12/08/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import "PartiesViewController.h"
#import "HSTGGaleriasCell.h"
#import <Parse/Parse.h>

@interface PartiesViewController ()

@property (nonatomic, strong) NSMutableArray *parties;
@property (strong, nonatomic) IBOutlet UITableView *tabela;

@end

@implementation PartiesViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    PFQuery *query = [PFQuery queryWithClassName:@"Galeria"];
    [query whereKey:@"isParty" equalTo:[NSNumber numberWithBool:YES]];
    
    self.parties = [NSMutableArray arrayWithArray:[query findObjects]];
    
    self.tabela.delegate = self;
    self.tabela.dataSource = self;
    
}

-(UIColor *)colorFromHexString:(NSString *)hexString {
    unsigned rgbValue = 0;
    NSScanner *scanner = [NSScanner scannerWithString:hexString];
    [scanner setScanLocation:1]; // bypass '#' character
    [scanner scanHexInt:&rgbValue];
    return [UIColor colorWithRed:((rgbValue & 0xFF0000) >> 16)/255.0 green:((rgbValue & 0xFF00) >> 8)/255.0 blue:(rgbValue & 0xFF)/255.0 alpha:1.0];
}

#pragma mark - Table View

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *CellIdentifier = @"HSTGGaleriasCell";
    
    HSTGGaleriasCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    PFObject *galeria = [self.parties objectAtIndex:indexPath.row];
    
    cell.lblGaleriaName.text = [NSString stringWithFormat:@"#%@", galeria[@"hashtag"]];
    [cell setBackgroundColor:[self colorFromHexString:galeria[@"hexColor"]]];
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [self.parties count];
}

@end
