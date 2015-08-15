//
//  HSTGLabel.m
//  HashTag Party
//
//  Created by Pedro Salomão on 11/8/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "HSTGLabel.h"
#import "FontConstants.h"

@interface HSTGLabel()

@property (nonatomic, strong) NSString *tipo;

@end

@implementation HSTGLabel

- (void)awakeFromNib
{
    [super awakeFromNib];
    if([self.tipo isEqualToString:@"semibold"]) {
        self.font = [UIFont fontWithName:HURME_GEOMETRIC_SEMIBOLD
                                               size:self.font.pointSize];
    } else if([self.tipo isEqualToString:@"bold"]) {
        self.font = [UIFont fontWithName:HURME_GEOMETRIC_BOLD
                                               size:self.font.pointSize];
    } else if([self.tipo isEqualToString:@"light"]) {
        self.font = [UIFont fontWithName:HURME_GEOMETRIC_LIGHT
                                               size:self.font.pointSize];
    } else if([self.tipo isEqualToString:@"thin"]) {
        self.font = [UIFont fontWithName:HURME_GEOMETRIC_THIN
                                    size:self.font.pointSize];
    }
}

@end
