//
//  NumberUtil.m
//  HashTag Party
//
//  Created by Pedro Salomão on 28/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "NumberUtil.h"

@implementation NumberUtil

+ (NSString *)currencyFormat:(NSNumber *)number
{
    NSNumberFormatter *_currencyFormatter = [[NSNumberFormatter alloc] init];
    [_currencyFormatter setNumberStyle:NSNumberFormatterCurrencyStyle];
    [_currencyFormatter setCurrencyCode:@"R$ "];
    [_currencyFormatter setGroupingSeparator:@"."];
    [_currencyFormatter setDecimalSeparator:@","];
    return [_currencyFormatter stringFromNumber:number];
}

@end
