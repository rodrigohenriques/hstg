//
//  HSTGAgendaTableViewCell.h
//  HashTag Party
//
//  Created by Pedro Salomão on 23/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HSTGLabel.h"

@interface HSTGAgendaTableViewCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIImageView *imagem;
@property (weak, nonatomic) IBOutlet HSTGLabel *titulo;
@property (weak, nonatomic) IBOutlet HSTGLabel *descricao;
@property (weak, nonatomic) IBOutlet HSTGLabel *labelImagem;


@end
