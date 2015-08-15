//
//  HSTGProductView.h
//  HashTag Party
//
//  Created by Pedro Salomão on 25/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HSTGLabel.h"

@interface HSTGProductView : UIView

@property (weak, nonatomic) IBOutlet HSTGLabel *nameLabel;
@property (weak, nonatomic) IBOutlet HSTGLabel *descriptionLabel;
@property (weak, nonatomic) IBOutlet UIImageView *imageView;
@property (weak, nonatomic) IBOutlet HSTGLabel *priceLabel;

@end
