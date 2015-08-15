//
//  HSTGRoundedButton.m
//  HashTag Brazil
//
//  Created by Pedro Salomão on 1/15/15.
//  Copyright (c) 2015 Pedro Salomão. All rights reserved.
//

#import "HSTGRoundedButton.h"

@implementation HSTGRoundedButton

- (void)awakeFromNib
{
    [super awakeFromNib];

    self.layer.cornerRadius = (self.bounds.size.height / 2);
    self.layer.masksToBounds = YES;
}

@end
