//
//  GaleriaFotosViewController.h
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 12/08/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GaleriaFotosViewController : UIViewController<UICollectionViewDataSource, UICollectionViewDelegate>


@property (nonatomic, strong) NSMutableArray *instagramMedias;

@end
