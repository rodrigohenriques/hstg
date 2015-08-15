//
//  Events.h
//  HashTag Party
//
//  Created by Pedro Salomão on 23/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Model+JSONKit.h"

@interface Events : Model

@property (nonatomic, retain) NSString * event_id;
@property (nonatomic, retain) NSString * data_inicial;
@property (nonatomic, retain) NSString * data_final;
@property (nonatomic, retain) NSString * titulo;
@property (nonatomic, retain) NSString * texto;
@property (nonatomic, retain) NSString * imagem;
@property (nonatomic, retain) NSData * imagemData;

@end
