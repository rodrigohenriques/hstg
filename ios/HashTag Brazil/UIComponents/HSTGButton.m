//
//  HSTGButton.m
//  HashTag Party
//
//  Created by Pedro Salomão on 11/8/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "HSTGButton.h"
#import "FontConstants.h"

@interface HSTGButton()

@property (nonatomic, strong) NSString *tipo;

@end

@implementation HSTGButton

- (void)awakeFromNib
{
    [super awakeFromNib];
    if([self.tipo isEqualToString:@"semibold"]) {
        self.titleLabel.font = [UIFont fontWithName:HURME_GEOMETRIC_SEMIBOLD
                                               size:self.titleLabel.font.pointSize];
    } else if([self.tipo isEqualToString:@"bold"]) {
        self.titleLabel.font = [UIFont fontWithName:HURME_GEOMETRIC_BOLD
                                               size:self.titleLabel.font.pointSize];
    } else if([self.tipo isEqualToString:@"light"]) {
        self.titleLabel.font = [UIFont fontWithName:HURME_GEOMETRIC_LIGHT
                                               size:self.titleLabel.font.pointSize];
    }
}

@end
