//
//  PublicacaoViewController.m
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 02/09/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import "PublicacaoViewController.h"
#import <Parse/Parse.h>
#import "MolduraCell.h"
#import "Imagem.h"
#import <InstagramKit/InstagramKit.h>

@interface PublicacaoViewController ()
@property (strong, nonatomic) IBOutlet UIImageView *imgFoto;
@property (strong, nonatomic) IBOutlet UICollectionView *galeria;
@property (nonatomic, strong) NSMutableArray *molduras;
@property (nonatomic, strong) UIDocumentInteractionController *documentController;
@end

@implementation PublicacaoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.documentController = [UIDocumentInteractionController new];
    self.documentController.delegate = self;
    
    self.molduras = [NSMutableArray new];
    self.galeria.dataSource = self;
    self.galeria.delegate = self;
    
    PFQuery *query = [PFQuery queryWithClassName:@"Mosaicos"];
    [query whereKey:@"galeria" equalTo:self.parametros.galeria];
    self.imgFoto.image = self.parametros.foto;
    
    NSArray *galerias = [query findObjects];
    
    for (PFObject *object in galerias) {
        PFFile *molduraFile = object[@"image"];
        PFFile *thumbFile   = object[@"thumb"];
        NSData *molduraData = [molduraFile getData];
        NSData *thumbData = [thumbFile getData];
        UIImage *imgMoldura = [UIImage imageWithData:molduraData];
        UIImage *imgThumb   = [UIImage imageWithData:thumbData];
        Imagem *imagem = [Imagem new];
        imagem.thumb = imgThumb;
        imagem.imagem = imgMoldura;
        
        [self.molduras addObject:imagem];
    }
    
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) carregarMoldura:(UIImage*) moldura{
    UIImage *bottomImage = self.imgFoto.image; //background image
    UIImage *image       = moldura; //foreground image
    
    CGSize newSize = CGSizeMake(bottomImage.size.width, bottomImage.size.height);
    UIGraphicsBeginImageContext(newSize);
    
    // Use existing opacity as is
    [bottomImage drawInRect:CGRectMake(0,0, newSize.width, newSize.height)];
    
    // Apply supplied opacity if applicable
    [image drawInRect:CGRectMake(0, 0, newSize.width, newSize.height) blendMode:kCGBlendModeNormal alpha:1.0];
    
    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext();
    
    self.imgFoto.image = newImage;
}

#pragma mark - CollectionView

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    MolduraCell *cell = [self.galeria dequeueReusableCellWithReuseIdentifier:@"MolduraCell" forIndexPath:indexPath];
    Imagem *imagem = [self.molduras objectAtIndex:indexPath.row];
    cell.imgMoldura.image = imagem.thumb;
    return cell;
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    Imagem *imagem = [self.molduras objectAtIndex:indexPath.row];
    [self carregarMoldura:imagem.imagem];
}

-(NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 1;
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    return self.molduras.count;
}

- (IBAction)btnPublicarClick:(id)sender{
    NSURL *instagramURL = [NSURL URLWithString:@"instagram://app"];
    if([[UIApplication sharedApplication] canOpenURL:instagramURL])
    {
        NSData *imageData = UIImageJPEGRepresentation(self.imgFoto.image, 1.0);
        
        NSString *writePath = [NSTemporaryDirectory() stringByAppendingPathComponent:@"instagram.igo"];
        if (![imageData writeToFile:writePath atomically:YES]) {
            NSLog(@"image save failed to path %@", writePath);
            return;
        }
        
        NSURL *fileURL = [NSURL fileURLWithPath:writePath];
        self.documentController = [UIDocumentInteractionController interactionControllerWithURL:fileURL];
        [self.documentController setUTI:@"com.instagram.exclusivegram"];
        [self.documentController setAnnotation:@{@"InstagramCaption" : [NSString stringWithFormat:@"#%@", self.parametros.galeria[@"hashtag"]]}];
        [self.documentController presentOpenInMenuFromRect:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 480) inView:self.view animated:YES];
    } else {
        [[[UIAlertView alloc] initWithTitle:@"Falha" message:@"Aplicativo Instagram não instalado neste dispositivo" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles: nil] show];
    }
    
    
}

-(void)documentInteractionControllerDidDismissOpenInMenu:(UIDocumentInteractionController *)controller{
    [self.navigationController popToRootViewControllerAnimated:YES];
}

-(void)documentInteractionControllerDidDismissOptionsMenu:(UIDocumentInteractionController *)controller{
    [self.navigationController popToRootViewControllerAnimated:YES];
}

@end
