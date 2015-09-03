//
//  ParametroTelaPublicacao.h
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 02/09/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Parse/Parse.h>

@interface ParametroTelaPublicacao : NSObject

@property (nonatomic, strong) PFObject *galeria;
@property (nonatomic, strong) UIImage *foto;

@end
