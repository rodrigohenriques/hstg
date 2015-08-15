#import "AppHelper.h"
#import "AppDelegate.h"

@implementation AppHelper

+(NSString *)getInstagramToken{
    return [((AppDelegate *) [[UIApplication sharedApplication] delegate]) instagramToken];
}

+ (NSManagedObjectContext *)mainManagedObjectContext {
	return [((AppDelegate *) [[UIApplication sharedApplication] delegate]) managedObjectContext];
}

@end