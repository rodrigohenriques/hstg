//
//  HSTGNovidadesTableViewCell.h
//  HashTag Party
//
//  Created by Pedro Salomão on 23/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HSTGLabel.h"

@interface HSTGNovidadesTableViewCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIImageView *imagem;
@property (weak, nonatomic) IBOutlet HSTGLabel *titulo;
@property (weak, nonatomic) IBOutlet HSTGLabel *descricao;

@end


