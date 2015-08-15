//
//  AppDelegate.h
//  HashTag Brazil
//
//  Created by Pedro Salomão on 29/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import <InstagramKit/InstagramKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) NSString *instagramToken;
@property (strong, nonatomic) UIWindow *window;
@property (assign, nonatomic) BOOL isFacebookLoggedIn;

@property (assign, nonatomic) BOOL isDownloadingNews;
@property (assign, nonatomic) BOOL isDownloadingAgenda;
@property (assign, nonatomic) BOOL isDownloadingProducts;

@property (readonly, strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@property (readonly, strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (readonly, strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;

- (void)saveContext;
- (NSURL *)applicationDocumentsDirectory;


@end

