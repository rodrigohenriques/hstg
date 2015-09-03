//
//  PublicacaoViewController.h
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 02/09/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>
#import "ParametroTelaPublicacao.h"

@interface PublicacaoViewController : UIViewController<UICollectionViewDataSource, UICollectionViewDelegate, UIDocumentInteractionControllerDelegate>

@property (nonatomic, strong) ParametroTelaPublicacao *parametros;

@end
