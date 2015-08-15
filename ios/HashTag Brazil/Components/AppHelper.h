//
//  AppHelper.h
//  ModelExample
//
//  Created by Franco Carbonaro on 23/09/10.
//  Copyright 2010 Franco Carbonaro. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@interface AppHelper : NSObject

+ (NSString*) getInstagramToken;
+ (NSManagedObjectContext *)mainManagedObjectContext;

@end