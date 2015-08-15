//
//  NovidadesViewController.m
//  HashTag Party
//
//  Created by Pedro Salomão on 11/10/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "NovidadesViewController.h"
#import "HSTGLabel.h"
#import "AppDelegate.h"
#import "News.h"
#import "HSTGNovidadesTableViewCell.h"
#import "FetchUtil.h"
#import "Constants.h"
#import <AFNetworking/UIImageView+AFNetworking.h>

@interface NovidadesViewController ()

@property (strong, nonatomic) NSArray *news;

@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) UITableViewController *tableViewController;

@end

@implementation NovidadesViewController

- (IBAction)topoClicked:(UIButton *)sender
{
    [self.tableView scrollToRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]
                          atScrollPosition:UITableViewScrollPositionTop
                                  animated:YES];
}

#pragma mark - View LifeCycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    
    self.tableViewController = [[UITableViewController alloc] initWithStyle:self.tableView.style];
    [self addChildViewController:self.tableViewController];
    self.tableViewController.tableView = self.tableView;
    self.tableViewController.refreshControl = [UIRefreshControl new];
    [self.tableViewController.refreshControl addTarget:self action:@selector(refetchData) forControlEvents:UIControlEventValueChanged];

    [self updateTableViewData];
}

- (void)viewWillAppear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateTableViewData) name:NOTIFICATION_NEWS_UPDATED object:nil];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:NOTIFICATION_NEWS_UPDATED object:nil];
}

#pragma mark - Private Methods


- (void)updateTableViewData
{
    AppDelegate *appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    if(appDelegate.isDownloadingNews) {
        [self.tableViewController.refreshControl beginRefreshing];
    } else {
        [self.tableViewController.refreshControl endRefreshing];
        self.news = [News getAll];
        [self.tableView reloadData];
    }
}

- (void)refetchData
{
    [FetchUtil fetchNews];
}

#pragma mark - UITableViewDataSource

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.news count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    // we must be sure to use the same identifier here as in the storyboard!
    static NSString *CellIdentifier = @"NovidadesCell";
    HSTGNovidadesTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    News *news = [self.news objectAtIndex:indexPath.row];
    
    cell.titulo.text = news.titulo;
    cell.descricao.text = news.texto;
    [cell.imagem setImageWithURL:[NSURL URLWithString:news.imagem]
                   placeholderImage:[UIImage imageNamed:@"newsNoPicture"]];
    
    return cell;
}

#pragma mark - UITableViewDelegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Do nothing
}

@end
