//
//  PartiesViewController.m
//  HashTag Brazil
//
//  Created by Guilherme Augusto on 12/08/15.
//  Copyright (c) 2015 Pedro Salom&#227;o. All rights reserved.
//

#import "PartiesViewController.h"
#import "HSTGGaleriasCell.h"
#import <Parse/Parse.h>
#import "PublicacaoViewController.h"
#import "ParametroTelaPublicacao.h"
#import "MBProgressHUD.h"

@interface PartiesViewController ()

@property (nonatomic, strong) NSMutableArray *parties;
@property (strong, nonatomic) IBOutlet UITableView *tabela;
@property (strong, nonatomic) UIImage *foto;
@property (nonatomic, strong) PFObject *galeriaSelecionada;
@property (nonatomic, strong) MBProgressHUD *hud;

@end

@implementation PartiesViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.foto = [UIImage new];
    
    self.hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    self.hud.mode = MBProgressHUDModeIndeterminate;
    self.hud.labelText = @"Carregando";
    [self.hud show:YES];
    
    PFQuery *query = [PFQuery queryWithClassName:@"Galeria"];
    [query whereKey:@"isParty" equalTo:[NSNumber numberWithBool:YES]];
    
    self.parties = [NSMutableArray arrayWithArray:[query findObjects]];
    
    self.tabela.delegate = self;
    self.tabela.dataSource = self;
    
    [self.hud hide:YES];
}

-(UIColor *)colorFromHexString:(NSString *)hexString {
    unsigned rgbValue = 0;
    NSScanner *scanner = [NSScanner scannerWithString:hexString];
    [scanner setScanLocation:1]; // bypass '#' character
    [scanner scanHexInt:&rgbValue];
    return [UIColor colorWithRed:((rgbValue & 0xFF0000) >> 16)/255.0 green:((rgbValue & 0xFF00) >> 8)/255.0 blue:(rgbValue & 0xFF)/255.0 alpha:1.0];
}

#pragma mark - Table View

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *CellIdentifier = @"HSTGGaleriasCell";
    
    HSTGGaleriasCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    PFObject *galeria = [self.parties objectAtIndex:indexPath.row];
    
    cell.lblGaleriaName.text = [NSString stringWithFormat:@"#%@", galeria[@"hashtag"]];
    [cell setBackgroundColor:[self colorFromHexString:galeria[@"hexColor"]]];
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    PFObject *galeria = [self.parties objectAtIndex:indexPath.row];
    self.galeriaSelecionada = galeria;
    [self abrirCamera];
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [self.parties count];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    PublicacaoViewController *publicacaoVC = (PublicacaoViewController*) segue.destinationViewController;
    publicacaoVC.parametros = (ParametroTelaPublicacao*) sender;
}

#pragma mark - UIImagePicker

-(void) abrirCamera{
    @try
    {
        UIImagePickerController *picker = [[UIImagePickerController alloc] init];
        picker.delegate = self;
        picker.sourceType = UIImagePickerControllerSourceTypeCamera;
        picker.allowsEditing = NO;
        picker.showsCameraControls = YES;
        [self presentViewController:picker animated:YES completion:nil];
    }
    @catch (NSException *exception)
    {
        [[[UIAlertView alloc] initWithTitle:@"Sem Câmera" message:@"Câmera não disponível" delegate:self cancelButtonTitle:@"Ok" otherButtonTitles:nil] show];
    }
    [self.hud hide:YES];
}

-(void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info{
    [picker dismissViewControllerAnimated:YES completion:nil];
    
    UIImage *cropedImage = squareCropImageToSideLength(info[UIImagePickerControllerOriginalImage], 300);
    
    ParametroTelaPublicacao *parametroTela = [ParametroTelaPublicacao new];
    parametroTela.foto = cropedImage;
    parametroTela.galeria = self.galeriaSelecionada;
    
    [self performSegueWithIdentifier:@"segueAddFoto" sender:parametroTela];
}

UIImage *squareCropImageToSideLength(UIImage *sourceImage,
                                     CGFloat sideLength)
{
    // input size comes from image
    CGSize inputSize = sourceImage.size;
    
    // round up side length to avoid fractional output size
    sideLength = ceilf(sideLength);
    
    // output size has sideLength for both dimensions
    CGSize outputSize = CGSizeMake(sideLength, sideLength);
    
    // calculate scale so that smaller dimension fits sideLength
    CGFloat scale = MAX(sideLength / inputSize.width,
                        sideLength / inputSize.height);
    
    // scaling the image with this scale results in this output size
    CGSize scaledInputSize = CGSizeMake(inputSize.width * scale,
                                        inputSize.height * scale);
    
    // determine point in center of "canvas"
    CGPoint center = CGPointMake(outputSize.width/2.0,
                                 outputSize.height/2.0);
    
    // calculate drawing rect relative to output Size
    CGRect outputRect = CGRectMake(center.x - scaledInputSize.width/2.0,
                                   center.y - scaledInputSize.height/2.0,
                                   scaledInputSize.width,
                                   scaledInputSize.height);
    
    // begin a new bitmap context, scale 0 takes display scale
    UIGraphicsBeginImageContextWithOptions(outputSize, YES, 0);
    
    // optional: set the interpolation quality.
    // For this you need to grab the underlying CGContext
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    CGContextSetInterpolationQuality(ctx, kCGInterpolationHigh);
    
    // draw the source image into the calculated rect
    [sourceImage drawInRect:outputRect];
    
    // create new image from bitmap context
    UIImage *outImage = UIGraphicsGetImageFromCurrentImageContext();
    
    // clean up
    UIGraphicsEndImageContext();
    
    // pass back new image
    return outImage;
}

@end
