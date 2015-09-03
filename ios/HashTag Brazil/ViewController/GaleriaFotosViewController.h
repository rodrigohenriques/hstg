//
//  GaleriaFotosViewController.h
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 12/08/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HSTGLabel.h"
#import "ParametroTelaGaleria.h"

@interface GaleriaFotosViewController : UIViewController<UICollectionViewDataSource, UICollectionViewDelegate>

@property (nonatomic, strong) ParametroTelaGaleria *parametroTelaGaleria;
@property (nonatomic, strong) NSMutableArray *instagramMedias;
@property (strong, nonatomic) IBOutlet UILabel *labelHashTag;

@end
