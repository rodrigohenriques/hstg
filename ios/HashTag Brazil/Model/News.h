//
//  News.h
//  HashTag Party
//
//  Created by Pedro Salomão on 23/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Model+JSONKit.h"

@interface News : Model

@property (nonatomic, retain) NSString * post_id;
@property (nonatomic, retain) NSString * titulo;
@property (nonatomic, retain) NSString * texto;
@property (nonatomic, retain) NSString * data;
@property (nonatomic, retain) NSString * imagem;
@property (nonatomic, retain) NSData * imagemData;

@end
