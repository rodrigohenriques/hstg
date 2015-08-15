//
//  GaleriaFotoCell.m
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 12/08/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import "GaleriaFotoCell.h"


@implementation GaleriaFotoCell

-(void)awakeFromNib{
    
    self.mediaFocusManager = [[ASMediaFocusManager alloc] init];
    self.mediaFocusManager.delegate = self;
    // Tells which views need to be focusable. You can put your image views in an array and give it to the focus manager.
    [self.mediaFocusManager installOnViews:@[self.imgFoto]];
}

#pragma mark - ASMediaFocusDelegate

- (UIViewController *)parentViewControllerForMediaFocusManager:(ASMediaFocusManager *)mediaFocusManager
{
    return (UIViewController*) self.delegate;
}

// Returns the URL where the media (image or video) is stored. The URL may be local (file://) or distant (http://).
- (NSURL *)mediaFocusManager:(ASMediaFocusManager *)mediaFocusManager mediaURLForView:(UIView *)view
{
    return self.imagemUrl;
}

// Returns the title for this media view. Return nil if you don't want any title to appear.
- (NSString *)mediaFocusManager:(ASMediaFocusManager *)mediaFocusManager titleForView:(UIView *)view
{
    return self.imagemTitulo;
}

@end
