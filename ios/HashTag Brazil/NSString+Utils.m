//
//  NSString.m
//  HashTag Brazil
//
//  Created by Pedro Salomão on 3/22/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import "NSString+Utils.h"

@implementation NSString(NSStringUtils)

- (NSString *)trim
{
    return [self stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
}

@end
