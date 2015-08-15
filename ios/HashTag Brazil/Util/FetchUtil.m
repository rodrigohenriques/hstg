//
//  FetchUtil.m
//  HashTag Party
//
//  Created by Pedro Salomão on 23/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "FetchUtil.h"
#import "News.h"
#import "Events.h"
#import "AppDelegate.h"
#import "Products.h"
#import "Constants.h"

#define URL_NEWS @"http://usehstg.com.br/api/news/list?params=eyAgInRva2VuIiA6ICJkM2I0MWQ5MTllODUxMjI0YzI5ZGU5ZjUzZGM5MTk2OSIgIH0%3D"
#define URL_AGENDA @"http://usehstg.com.br/api/agenda/list?params=eyAgInRva2VuIiA6ICJkM2I0MWQ5MTllODUxMjI0YzI5ZGU5ZjUzZGM5MTk2OSIgIH0%3D"
#define URL_PRODUTOS @"http://usehstg.com.br/api/produto/list?params=eyAgInRva2VuIiA6ICJkM2I0MWQ5MTllODUxMjI0YzI5ZGU5ZjUzZGM5MTk2OSIgIH0%3D"

@implementation FetchUtil

+ (void)fetchNews
{
    // SetUp downloading configuration.
    AppDelegate *appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    appDelegate.isDownloadingNews = YES;
    
    // Clean up old News.
    [News truncate];

    [self getDataWithURL:[NSURL URLWithString:URL_NEWS] withCompletionBlock:^(NSURL *localfile, NSURLResponse *response, NSError *error) {
        if(error)
            NSLog(@"News Error: %@", error);
        
        NSData *jsonResults = [NSData dataWithContentsOfURL:localfile];
        if(jsonResults.length > 0)
        {
            NSDictionary *propertyListResults = [NSJSONSerialization JSONObjectWithData:jsonResults
                                                                                options:0
                                                                                  error:NULL];
            
            dispatch_async(dispatch_get_main_queue(), ^{
                NSEnumerator *enumerator = [propertyListResults objectEnumerator];
                id object;
                
                while ((object = [enumerator nextObject])) {
                    if([object isKindOfClass:[NSDictionary class]]) {
                        News *news = [News objectFromDictionary:(NSDictionary *) object];
                        
                        NSError *error = nil;
                        [news save:&error];
                        if(error) {
                            break;
                        }
                    }
                }
                
                appDelegate.isDownloadingNews = NO;
                [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFICATION_NEWS_UPDATED object:nil];
            });
        } else {
            dispatch_async(dispatch_get_main_queue(), ^{
                appDelegate.isDownloadingNews = NO;
                [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFICATION_NEWS_UPDATED object:nil];
            });
        }
    }];
}

+ (void)fetchAgenda
{
    // SetUp downloading configuration.
    AppDelegate *appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    appDelegate.isDownloadingAgenda = YES;
    
    // Clean up old Events.
    [Events truncate];
    
    [self getDataWithURL:[NSURL URLWithString:URL_AGENDA] withCompletionBlock:^(NSURL *localfile, NSURLResponse *response, NSError *error) {
        if(error)
            NSLog(@"Agenda Error: %@", error);

        NSData *jsonResults = [NSData dataWithContentsOfURL:localfile];
        
        if(jsonResults.length > 0)
        {
            NSDictionary *propertyListResults = [NSJSONSerialization JSONObjectWithData:jsonResults
                                                                                options:0
                                                                                  error:NULL];
            
            dispatch_async(dispatch_get_main_queue(), ^{
                NSEnumerator *enumerator = [propertyListResults objectEnumerator];
                id object;
                
                while ((object = [enumerator nextObject])) {
                    if([object isKindOfClass:[NSDictionary class]]) {
                        Events *event = [Events objectFromDictionary:(NSDictionary *) object];
                        
                        NSError *error = nil;
                        [event save:&error];
                        if(error) {
                            break;
                        }
                    }
                }
                
                appDelegate.isDownloadingAgenda = NO;
                [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFICATION_EVENTS_UPDATED object:nil];
            });
        } else {
            dispatch_async(dispatch_get_main_queue(), ^{
                appDelegate.isDownloadingAgenda = NO;
                [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFICATION_EVENTS_UPDATED object:nil];
            });
        }
    }];
}

+ (void)fetchProducts
{
    // SetUp downloading configuration.
    AppDelegate *appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    appDelegate.isDownloadingProducts = YES;
    
    // Clean up old Products.
    [Products truncate];
    
    [self getDataWithURL:[NSURL URLWithString:URL_PRODUTOS] withCompletionBlock:^(NSURL *localfile, NSURLResponse *response, NSError *error) {

        if(error)
            NSLog(@"Products Error: %@", error);
        
        NSData *jsonResults = [NSData dataWithContentsOfURL:localfile];
        if(jsonResults.length > 0)
        {
            NSDictionary *propertyListResults = [NSJSONSerialization JSONObjectWithData:jsonResults
                                                                                options:0
                                                                                  error:NULL];
            
            NSEnumerator *enumerator = [propertyListResults objectEnumerator];
            id object;
            
            NSMutableArray *products = [[NSMutableArray alloc] init];
            while ((object = [enumerator nextObject])) {
                if([object isKindOfClass:[NSDictionary class]]) {
                    Products *product = [Products objectFromDictionary:(NSDictionary *) object];
                    [products addObject:product];
                }
            }
            
            dispatch_async(dispatch_get_main_queue(), ^{
                for (Products *product in products) {
                    [product save:NULL];
                }
                
                appDelegate.isDownloadingProducts = NO;
                [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFICATION_PRODUCTS_UPDATED object:nil];
            });
        } else {
            dispatch_async(dispatch_get_main_queue(), ^{
                appDelegate.isDownloadingProducts = NO;
                [[NSNotificationCenter defaultCenter] postNotificationName:NOTIFICATION_PRODUCTS_UPDATED object:nil];
            });
        }
    }];
}

+ (void) getDataWithURL:(NSURL *)url withCompletionBlock:(void (^)(NSURL *localfile, NSURLResponse *response, NSError *error))completionHandler
{
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration ephemeralSessionConfiguration];
    NSURLSession *session = [NSURLSession sessionWithConfiguration:configuration];
    
    NSURLSessionDownloadTask *task = [session downloadTaskWithRequest:request
                                                    completionHandler:completionHandler];
    [task resume];
}

@end
