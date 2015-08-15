//
//  ProdutosViewController.m
//  HashTag Party
//
//  Created by Pedro Salomão on 25/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "ProdutosViewController.h"
#import "Products.h"
#import "HSTGProductView.h"
#import "NumberUtil.h"
#import "AppDelegate.h"
#import "Constants.h"
#import <AFNetworking/UIImageView+AFNetworking.h>

#define PAGE_WIDTH_PERCENT 0.5
#define PAGE_HORIZONTAL_SPACING 10
#define PAGE_MARGIN_TOP 10
#define PAGE_MARGIN_HORIZONTAL 40

@interface ProdutosViewController ()

@property (weak,   nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak,   nonatomic) IBOutlet UIView *activityIndicatorContainerView;
@property (weak,   nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;
@property (strong, nonatomic) NSArray *productsList;

@end

@implementation ProdutosViewController

#pragma mark View LifeCycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self updateProducts];
}

- (void)viewWillAppear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateProducts) name:NOTIFICATION_PRODUCTS_UPDATED object:nil];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:NOTIFICATION_PRODUCTS_UPDATED object:nil];
}

#pragma mark Private Methods

- (void)updateProducts
{
    AppDelegate *appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    if(appDelegate.isDownloadingProducts)
    {
        // Update Model info
        self.productsList = [NSArray new];

        // Clear and Hide ScrollView
        [self clearScrollViewChilds];
        self.scrollView.hidden = YES;
        
        // Show ActivityIndicator
        self.activityIndicatorContainerView.hidden = NO;
        [self.activityIndicator startAnimating];
    }
    else
    {
        // Update Model info
        self.productsList = [Products getAll];

        // Clear ScrollView
        [self clearScrollViewChilds];
        
        int i = 0;
        NSMutableArray *productViews = [NSMutableArray new];
        for (Products *product in self.productsList) {
            NSArray *array = [[NSBundle mainBundle] loadNibNamed:@"HSTGProductView" owner:[UIViewController new] options:nil];
            if(array && array.count > 0) {
                id object = [array objectAtIndex:0];
                if([object isKindOfClass:[HSTGProductView class]]) {
                    HSTGProductView *productView = (HSTGProductView *) object;
                    productView.translatesAutoresizingMaskIntoConstraints = NO;
                    
                    productView.nameLabel.text = product.titulo;
                    productView.descriptionLabel.text = product.descricao;
                    [productView.imageView setImageWithURL:[NSURL URLWithString:product.imagem]
                                          placeholderImage:[UIImage imageNamed:@"productsNoPicture"]];
                    productView.priceLabel.text = [NumberUtil currencyFormat:[NSNumber numberWithDouble:[product.preco doubleValue]]];
                    
                    [productViews addObject:productView];
                    [self.scrollView addSubview:productView];
                    [self addConstraintsToProductView:productView
                                       withScrollView:self.scrollView
                                   andPerviousSibling:(i > 0)?([productViews objectAtIndex:(i - 1)]):(nil)];
                    i++;
                }
            }
        }
        
        // Stop and Hide ActivityIndicator
        [self.activityIndicator stopAnimating];
        self.activityIndicatorContainerView.hidden = YES;
        
        // Show the ScrollView
        self.scrollView.hidden = NO;
        
        dispatch_async(dispatch_get_main_queue(), ^{
            [self updateScrollViewContentSize];
        });
    }
}

- (void)addConstraintsToProductView:(HSTGProductView *)productView withScrollView:(UIScrollView *)scrollView andPerviousSibling:(HSTGProductView *)siblingProductView
{
    [scrollView addConstraint:[NSLayoutConstraint
                              constraintWithItem:productView
                              attribute:NSLayoutAttributeWidth
                              relatedBy:NSLayoutRelationEqual
                              toItem:scrollView
                              attribute:NSLayoutAttributeWidth
                              multiplier:PAGE_WIDTH_PERCENT
                              constant:0.0]];
    

    if(!siblingProductView) {
        [scrollView addConstraint:[NSLayoutConstraint
                                  constraintWithItem:productView
                                  attribute:NSLayoutAttributeLeft
                                  relatedBy:NSLayoutRelationEqual
                                  toItem:scrollView
                                  attribute:NSLayoutAttributeLeft
                                  multiplier:1.0
                                  constant:PAGE_MARGIN_HORIZONTAL]];
    } else {
        [scrollView addConstraint:[NSLayoutConstraint
                                  constraintWithItem:productView
                                  attribute:NSLayoutAttributeLeft
                                  relatedBy:NSLayoutRelationEqual
                                  toItem:siblingProductView
                                  attribute:NSLayoutAttributeRight
                                  multiplier:1.0
                                  constant:PAGE_MARGIN_HORIZONTAL]];
    }

    [self.view addConstraint:[NSLayoutConstraint
                              constraintWithItem:productView
                              attribute:NSLayoutAttributeBottom
                              relatedBy:NSLayoutRelationEqual
                              toItem:scrollView
                              attribute:NSLayoutAttributeBottom
                              multiplier:1.0
                              constant:0.0]];

    [self.view addConstraint:[NSLayoutConstraint
                              constraintWithItem:productView
                              attribute:NSLayoutAttributeTop
                              relatedBy:NSLayoutRelationEqual
                              toItem:scrollView
                              attribute:NSLayoutAttributeTop
                              multiplier:1.0
                              constant:PAGE_MARGIN_TOP]];
}

- (void)clearScrollViewChilds
{
    for (UIView *v in self.scrollView.subviews) {
        if ([v isKindOfClass:[HSTGProductView class]]) {
            [v removeFromSuperview];
        }
    }
}

- (void)updateScrollViewContentSize
{
    CGFloat scrollViewWidth = 0.0f;
    for (UIView* view in self.scrollView.subviews)
    {
        if ([view isKindOfClass:[HSTGProductView class]]) {
            scrollViewWidth += view.frame.size.width + PAGE_MARGIN_HORIZONTAL;
        }
    }
    scrollViewWidth += PAGE_MARGIN_HORIZONTAL;
    
    dispatch_async(dispatch_get_main_queue(), ^{
        self.scrollView.contentSize = CGSizeMake(scrollViewWidth, self.scrollView.frame.size.height);
    });
}

@end
