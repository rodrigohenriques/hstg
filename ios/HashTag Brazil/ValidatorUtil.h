//
//  ValidatorUtil.h
//  HashTag Brazil
//
//  Created by Pedro Salomão on 3/22/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ValidatorUtil : NSObject

+ (BOOL)valida:(NSString *)valor withPattern:(NSString *)regex;
+ (BOOL)validaEmail:(NSString *)email;

@end
