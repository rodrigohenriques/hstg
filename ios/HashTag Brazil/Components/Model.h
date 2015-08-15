#import <CoreData/CoreData.h>

@interface Model : NSManagedObject {
	
}

#pragma mark -
#pragma mark Private

+ (NSArray *)propertiesToFetchForString:(NSString *)stringPropertiesToFetch;
+ (NSPredicate *)predicateForStringWithFormat:(NSString *)stringWithFormatPredicate;
+ (NSArray *)sortDescriptorForString:(NSString *)stringSortDescriptors;

#pragma mark -
#pragma mark Main Managed Object Context

- (NSManagedObjectContext *)mainManagedObjectContext;
+ (NSManagedObjectContext *)mainManagedObjectContext;


#pragma mark -
#pragma mark Fecth Request

+ (NSFetchRequest *)fetchRequestWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andPredicate:(NSString *)stringPredicates andSortDescriptor:(NSString *)stringSortDescriptors andFetchLimit:(NSUInteger)limit;
+ (NSFetchRequest *)fetchRequestWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andPredicate:(NSString *)stringPredicates andSortDescriptor:(NSString *)stringSortDescriptors;
+ (NSFetchRequest *)fetchRequestWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andSortDescriptor:(NSString *)stringSortDescriptors;
+ (NSFetchRequest *)fetchRequestWithSortDescriptor:(NSString *)stringSortDescriptors;


#pragma mark -
#pragma mark Arrays

+ (NSArray *)getWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andPredicate:(NSString *)stringPredicates andSortDescriptor:(NSString *)stringSortDescriptors;
+ (NSArray *)getAll;
+ (NSArray *)getAllWithStringSortDescriptor:(NSString *)stringSortDescriptor;
+ (NSArray *)getAllWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andSortDescriptor:(NSString *)stringSortDescriptors;
+ (NSArray *)getWithPredicate:(NSString *)stringPredicates;
+ (NSArray *)getWithPredicate:(NSString *)stringPredicates andSortDescriptor:(NSString *)stringSortDescriptors;

#pragma mark -
#pragma mark  Save

- (BOOL)save:(NSError **)error;
+ (BOOL) saveAll:(NSError **)error;

#pragma mark  Delete

- (void)delete;
+ (void)deleteWithPredicate:(NSString *)predicate;


#pragma mark -
#pragma mark Truncate
+ (void)truncate;
+ (void)truncateNoSave;
@end
