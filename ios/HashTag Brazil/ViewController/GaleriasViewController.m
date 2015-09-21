//
//  GaleriasViewController.m
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 12/08/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import "GaleriasViewController.h"
#import "HSTGGaleriasCell.h"
#import <Parse/Parse.h>
#import "AppHelper.h"
#import <InstagramKit/InstagramKit.h>
#import "GaleriaFotosViewController.h"
#import "ParametroTelaGaleria.h"
#import "MBProgressHUD.h"

@interface GaleriasViewController ()

@property (strong, nonatomic) IBOutlet UITableView *tabela;
@property (strong, nonatomic) NSMutableArray *galerias;
@property (strong, nonatomic) InstagramEngine *instagramEngine;
@property (strong, nonatomic) MBProgressHUD *hud;

@end

@implementation GaleriasViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    PFQuery *query = [PFQuery queryWithClassName:@"Galeria"];
    self.galerias = [NSMutableArray arrayWithArray:[query findObjects]];
    
    if (![AppHelper getInstagramToken].length > 0) {
        NSString *accesToken = [[NSUserDefaults standardUserDefaults] valueForKey:@"instagramAccessToken"];
        self.instagramEngine = [InstagramEngine sharedEngine];
        if (accesToken.length > 0) {
            self.instagramEngine.accessToken = accesToken;
        } else {
            [self.instagramEngine loginWithBlock:^(NSError *error) {
                NSLog(@"Logou");
            }];
        }
    }
    
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

#pragma mark - TableView

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *CellIdentifier = @"HSTGGaleriasCell";
    
    HSTGGaleriasCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    PFObject *galeria = [self.galerias objectAtIndex:indexPath.row];
    
    cell.lblGaleriaName.text = [NSString stringWithFormat:@"#%@", galeria[@"hashtag"]];
    [cell setBackgroundColor:[self colorFromHexString:galeria[@"hexColor"]]];
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    self.hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    self.hud.mode = MBProgressHUDModeIndeterminate;
    self.hud.labelText = @"Carregando...";
    [self.hud show:YES];
    
    PFObject *galeria = [self.galerias objectAtIndex:indexPath.row];
    self.instagramEngine.accessToken = [[NSUserDefaults standardUserDefaults] valueForKey:@"instagramAccessToken"];
    [self.instagramEngine getMediaWithTagName:galeria[@"hashtag"] count:100 maxId:nil withSuccess:^(NSArray *media, InstagramPaginationInfo *paginationInfo) {
        NSString *hashtag = [NSString stringWithFormat:@"#%@", galeria[@"hashtag"]];
        ParametroTelaGaleria *parametroTelaGaleria = [ParametroTelaGaleria new];
        parametroTelaGaleria.medias = media;
        parametroTelaGaleria.nomeHashtag = hashtag;
        
        [self performSegueWithIdentifier:@"sgGaleriaFotos" sender:parametroTelaGaleria];

    } failure:^(NSError *error) {
        NSLog(@"%@", [error localizedDescription]);
    }];
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [self.galerias count];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    [self.hud hide:YES];
    if ([segue.identifier isEqualToString:@"sgGaleriaFotos"]) {
        GaleriaFotosViewController *galeria = (GaleriaFotosViewController*) segue.destinationViewController;
        galeria.parametroTelaGaleria = (ParametroTelaGaleria*) sender;
    }
}

@end
