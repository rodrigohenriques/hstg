//
//  DateUtil.h
//  HashTag Party
//
//  Created by Pedro Salomão on 23/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DateUtil : NSObject

+ (NSInteger)getDayFromDate:(NSDate *)date;
+ (NSString *)getMonthStringFromDate:(NSDate *)date;
+ (NSInteger)getHourFromDate:(NSDate *)date;

@end
