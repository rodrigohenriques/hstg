//
//  Products.h
//  HashTag Party
//
//  Created by Pedro Salomão on 23/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Model+JSONKit.h"

@interface Products : Model

@property (nonatomic, retain) NSString * produto_id;
@property (nonatomic, retain) NSString * titulo;
@property (nonatomic, retain) NSString * descricao;
@property (nonatomic, retain) NSString * preco;
@property (nonatomic, retain) NSString * imagem;
@property (nonatomic, retain) NSString * link;
@property (nonatomic, retain) NSData * imagemData;

@end
