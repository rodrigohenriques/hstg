//
//  GaleriaFotoCell.h
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 12/08/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <ASMediaFocusManager/ASMediaFocusManager.h>

@interface GaleriaFotoCell : UICollectionViewCell<ASMediasFocusDelegate>

@property (nonatomic) id delegate;
@property (nonatomic, strong) ASMediaFocusManager *mediaFocusManager;
@property (strong, nonatomic) IBOutlet UIImageView *imgFoto;
@property (nonatomic, strong) NSURL *imagemUrl;
@property (nonatomic, strong) NSString *imagemTitulo;

@end
