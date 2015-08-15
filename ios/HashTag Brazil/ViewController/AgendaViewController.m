//
//  AgendaViewController.m
//  HashTag Party
//
//  Created by Pedro Salomão on 23/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "AgendaViewController.h"
#import "HSTGLabel.h"
#import "AppDelegate.h"
#import "HSTGAgendaTableViewCell.h"
#import "FetchUtil.h"
#import "Events.h"
#import "DateUtil.h"
#import "Constants.h"
#import <AFNetworking/UIImageView+AFNetworking.h>

@interface AgendaViewController ()

@property (strong, nonatomic) NSArray *events;

@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) UITableViewController *tableViewController;


@end

@implementation AgendaViewController

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
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateTableViewData) name:NOTIFICATION_EVENTS_UPDATED object:nil];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:NOTIFICATION_EVENTS_UPDATED object:nil];
}

#pragma mark - Private Methods

- (void)updateTableViewData
{
    AppDelegate *appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    if(appDelegate.isDownloadingNews) {
        [self.tableViewController.refreshControl beginRefreshing];
    } else {
        [self.tableViewController.refreshControl endRefreshing];
        self.events = [Events getAll];
        [self.tableView reloadData];
    }
}

- (void)refetchData
{
    [FetchUtil fetchAgenda];
}

#pragma mark - UITableViewDataSource

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.events count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    // we must be sure to use the same identifier here as in the storyboard!
    static NSString *CellIdentifier = @"AgendaCell";
    HSTGAgendaTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];

    Events *event = [self.events objectAtIndex:indexPath.row];

    if(event.imagem.length > 0) {
        [cell.imagem setImageWithURL:[NSURL URLWithString:event.imagem] placeholderImage:[UIImage imageNamed:@"eventsNoPicture"]];
        cell.labelImagem.hidden = YES;
    } else {
        cell.imagem.image = [UIImage imageNamed:@"eventsNoPicture"];
        cell.labelImagem.hidden = NO;
    }
    
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
    NSDate *data = [formatter dateFromString:event.data_inicial];
    
    cell.labelImagem.text = [NSString stringWithFormat:@"%02ld\n%@", ((long) [DateUtil getDayFromDate:data]), [DateUtil getMonthStringFromDate:data]];
    cell.titulo.text = [NSString stringWithFormat:@"%02ld %@ - %02ldH\n%@", ((long) [DateUtil getDayFromDate:data]), [DateUtil getMonthStringFromDate:data], ((long) [DateUtil getHourFromDate:data]), event.titulo];
    cell.descricao.text = event.texto;
    
    return cell;
}

#pragma mark - UITableViewDelegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Do nothing
}

@end
