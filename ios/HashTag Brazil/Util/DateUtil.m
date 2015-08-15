//
//  DateUtil.m
//  HashTag Party
//
//  Created by Pedro Salomão on 23/11/14.
//  Copyright (c) 2014 Pedro Salomão. All rights reserved.
//

#import "DateUtil.h"

@implementation DateUtil

+ (NSInteger)getDayFromDate:(NSDate *)date
{
    return [[[NSCalendar currentCalendar] components:NSCalendarUnitDay fromDate:date] day];
}

+ (NSString *)getMonthStringFromDate:(NSDate *)date
{
    NSInteger month = [[[NSCalendar currentCalendar] components:NSCalendarUnitMonth fromDate:date] month];
    
    switch (month) {
        case 1:  return @"JAN";
        case 2:  return @"FEV";
        case 3:  return @"MAR";
        case 4:  return @"ABR";
        case 5:  return @"MAI";
        case 6:  return @"JUN";
        case 7:  return @"JUL";
        case 8:  return @"AGO";
        case 9:  return @"SET";
        case 10: return @"OUT";
        case 11: return @"NOV";
        case 12: return @"DEZ";
        default: return @"";
    }
}

+ (NSInteger)getHourFromDate:(NSDate *)date {
    return [[[NSCalendar currentCalendar] components:NSCalendarUnitHour fromDate:date] hour];
}

@end
