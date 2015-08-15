//
//  ValidatorUtil.m
//  HashTag Brazil
//
//  Created by Pedro Salomão on 3/22/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import "ValidatorUtil.h"

#define VALIDADOR_EMAIL_PATTERN @"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"

@implementation ValidatorUtil

+ (BOOL)valida:(NSString *)valor withPattern:(NSString *)regex {
    NSError *error = nil;
    NSRegularExpression *regularExpression = [NSRegularExpression regularExpressionWithPattern:regex options:0 error:&error];
    if(error) {
        return NO;
    }

    return [regularExpression numberOfMatchesInString:valor options:0 range:NSMakeRange(0, [valor length])];
}

+ (BOOL)validaEmail:(NSString *)email {
    return (email.length > 0) && ([ValidatorUtil valida:email withPattern:VALIDADOR_EMAIL_PATTERN]);
}

@end
