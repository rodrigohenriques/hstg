//
//  HSTGBarButtonItem.m
//  HashTag Party
//
//  Created by Pedro Salomão on 11/10/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "HSTGBarButtonItem.h"
#import "FontConstants.h"

@interface HSTGBarButtonItem()

@property (nonatomic, strong) NSString *tipo;

@end

@implementation HSTGBarButtonItem

- (void)awakeFromNib
{
    [super awakeFromNib];
    if([self.tipo isEqualToString:@"semibold"]) {
        [self setTitleTextAttributes:@{ NSFontAttributeName: [UIFont fontWithName:HURME_GEOMETRIC_SEMIBOLD
                                                                             size:15.0] }
                            forState:UIControlStateNormal];
    } else if([self.tipo isEqualToString:@"bold"]) {
        [self setTitleTextAttributes:@{ NSFontAttributeName: [UIFont fontWithName:HURME_GEOMETRIC_BOLD
                                                                             size:15.0] }
                            forState:UIControlStateNormal];
    } else if([self.tipo isEqualToString:@"light"]) {
        [self setTitleTextAttributes:@{ NSFontAttributeName: [UIFont fontWithName:HURME_GEOMETRIC_LIGHT
                                                                             size:15.0] }
                            forState:UIControlStateNormal];
    }
}

@end
