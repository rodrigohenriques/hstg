//
//  GaleriaFotosViewController.m
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 12/08/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import "GaleriaFotosViewController.h"
#import "GaleriaFotoCell.h"
#import <InstagramKit/InstagramKit.h>
#import <AFNetworking/UIImageView+AFNetworking.h>

@interface GaleriaFotosViewController ()
@property (strong, nonatomic) IBOutlet UICollectionView *galeria;

@end

@implementation GaleriaFotosViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.galeria.dataSource = self;
    self.galeria.delegate = self;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Collection View

-(NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 1;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *CellIdentifier = @"GaleriaFotoCell";
    
    GaleriaFotoCell *cell = [self.galeria dequeueReusableCellWithReuseIdentifier:CellIdentifier forIndexPath:indexPath];
    
    InstagramMedia *media = [self.instagramMedias objectAtIndex:indexPath.row];
    
    [cell.imgFoto setImageWithURL:media.standardResolutionImageURL placeholderImage:[UIImage imageNamed:@"ic_hstg"]];
    cell.imagemUrl = media.standardResolutionImageURL;
    
    NSMutableString *strTitulo = [NSMutableString new];
    
    
    for (NSString *strTag in media.tags) {
        [strTitulo appendString:[NSString stringWithFormat:@" #%@", strTag]];
    }
    
    cell.imagemTitulo = strTitulo;
    cell.delegate = self;
    
    return cell;
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    return [self.instagramMedias count];
}

@end
