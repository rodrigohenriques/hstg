#import "Model.h"
#import "AppHelper.h"
#import <objc/runtime.h>

@implementation Model

#pragma mark -
#pragma mark Init

- (id) init
{
    if (![NSStringFromClass(self.class) isEqualToString: @"Model"]) {
        NSEntityDescription *entityDescription = [NSEntityDescription entityForName:NSStringFromClass(self.class)  inManagedObjectContext:[self mainManagedObjectContext]];
        self = [super initWithEntity:entityDescription insertIntoManagedObjectContext:[self mainManagedObjectContext]];
    }
    else {
        NSLog(@"Não pode instanciar a classe Model diretamente");
    }
	return self;
}

#pragma mark -
#pragma mark Main Managed Object Context

- (NSManagedObjectContext *)mainManagedObjectContext {
	return [[self class] mainManagedObjectContext];
}


+ (NSManagedObjectContext *)mainManagedObjectContext {
    
	return [AppHelper mainManagedObjectContext];
}

#pragma mark -
#pragma mark Fetch Request

+ (NSFetchRequest *)fetchRequestWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andPredicate:(NSString *)stringPredicates andSortDescriptor:(NSString *)stringSortDescriptors andFetchLimit:(NSUInteger)limit {
	
	NSFetchRequest *request = [NSFetchRequest new];
	NSEntityDescription *entity = [NSEntityDescription entityForName:NSStringFromClass(self.class) 
											  inManagedObjectContext:[self mainManagedObjectContext]];
	
	[request setEntity:entity];
	[request setPropertiesToFetch:[self propertiesToFetchForString:stringPropertiesToFetch]];
	[request setPredicate:[self predicateForStringWithFormat:stringPredicates]];
	[request setSortDescriptors:[self sortDescriptorForString:stringSortDescriptors]];
	//[request setFetchLimit:limit];
	
	return request;
}

+ (NSFetchRequest *)fetchRequestWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andPredicate:(NSString *)stringPredicates andSortDescriptor:(NSString *)stringSortDescriptors {
	
	return [self fetchRequestWithPropertiesToFetch:stringPropertiesToFetch andPredicate:stringPredicates andSortDescriptor:stringSortDescriptors andFetchLimit:20];
	
}


+ (NSFetchRequest *)fetchRequestWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andSortDescriptor:(NSString *)stringSortDescriptors {
	return [self fetchRequestWithPropertiesToFetch:stringPropertiesToFetch andPredicate:@"" andSortDescriptor:stringSortDescriptors];
}

+ (NSFetchRequest *)fetchRequestWithSortDescriptor:(NSString *)stringSortDescriptors {
	return [self fetchRequestWithPropertiesToFetch:@"" andPredicate:@"" andSortDescriptor:stringSortDescriptors];
}

+ (NSArray *)getWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andPredicate:(NSString *)stringPredicates andSortDescriptor:(NSString *)stringSortDescriptors {
	NSError *error;
	NSFetchRequest *request = [self fetchRequestWithPropertiesToFetch:stringPropertiesToFetch andPredicate:stringPredicates andSortDescriptor:stringSortDescriptors];
	NSArray *result = [[NSArray alloc] initWithArray:[[self mainManagedObjectContext] executeFetchRequest:request error:&error]];
    
	return result;
}

#pragma mark -
#pragma mark Array - Get All

+ (NSArray *)getAll {
	return [self getWithPropertiesToFetch:@"" andPredicate:@"" andSortDescriptor:@""];
}

+ (NSArray *)getAllWithStringSortDescriptor:(NSString *)stringSortDescriptor {
	return [self getWithPropertiesToFetch:@"" andPredicate:@"" andSortDescriptor:stringSortDescriptor];
}

+ (NSArray *)getAllWithPropertiesToFetch:(NSString *)stringPropertiesToFetch andSortDescriptor:(NSString *)stringSortDescriptors {
	return [self getWithPropertiesToFetch:stringPropertiesToFetch andPredicate:@"" andSortDescriptor:stringSortDescriptors];
}


#pragma mark Array - Get with predicate

+ (NSArray *)getWithPredicate:(NSString *)stringPredicates {
	return [self getWithPropertiesToFetch:@"" andPredicate:stringPredicates andSortDescriptor:@""];
}

+ (NSArray *)getWithPredicate:(NSString *)stringPredicates andSortDescriptor:(NSString *)stringSortDescriptors {
	return [self getWithPropertiesToFetch:@"" andPredicate:stringPredicates andSortDescriptor:stringSortDescriptors];
}

#pragma mark -
#pragma mark Private Methods

+ (NSArray *)propertiesToFetchForString:(NSString *)stringPropertiesToFetch {
	
	if (![stringPropertiesToFetch isEqualToString:@""]) {
		NSMutableArray *propertiesToFetch = [[NSMutableArray alloc] initWithArray:[[stringPropertiesToFetch stringByReplacingOccurrencesOfString:@" " withString:@""] 
																				   componentsSeparatedByString:@","]];
		
		return propertiesToFetch;
	}
	
	return nil;
}

+ (NSPredicate *)predicateForStringWithFormat:(NSString *)stringWithFormatPredicate {
	
	if (stringWithFormatPredicate && ![stringWithFormatPredicate isEqualToString:@""]) {
		NSPredicate *predicate = [NSPredicate predicateWithFormat:stringWithFormatPredicate];
		return predicate;
	} 
	
	return nil;
}

static const char *getPropertyType(objc_property_t property) {
    const char *attributes = property_getAttributes(property);
    char buffer[1 + strlen(attributes)];
    strcpy(buffer, attributes);
    char *state = buffer, *attribute;
    while ((attribute = strsep(&state, ",")) != NULL) {
        if (attribute[0] == 'T') {
            return (const char *)[[NSData dataWithBytes:(attribute + 3) length:strlen(attribute) - 4] bytes];
        }
    }
    return "@";
}

+ (NSArray *)sortDescriptorForString:(NSString *)stringSortDescriptors {
	
	if (stringSortDescriptors && ![stringSortDescriptors isEqualToString:@""]) {
		
		NSMutableArray *sortDescriptors = [NSMutableArray new];
		NSArray *sortDescriptorsItens = [[NSArray alloc] initWithArray:[stringSortDescriptors 
																		componentsSeparatedByString:@","]];
		
		for (NSInteger i = 0; i < [sortDescriptorsItens count]; i++) {
			NSSortDescriptor *sortDescriptor = nil;
			NSArray *itemSortDescriptor = [[NSArray alloc] initWithArray:[[sortDescriptorsItens objectAtIndex:i] componentsSeparatedByString:@" "]];
			
			u_int count;
			objc_property_t * vars = class_copyPropertyList([self class], &count);
			
			if (count > 0) {
				for (NSUInteger i = 0;  i < count; i++) {
					objc_property_t var = vars[i];
					
					NSString *propertyName = [NSString stringWithUTF8String:property_getName(var)];
					//NSString  *propertyType = [NSString stringWithUTF8String:ivar_getName(var)];
                    NSString  *propertyType = [NSString stringWithUTF8String:getPropertyType(var)];
					
					if ([propertyName rangeOfString:[itemSortDescriptor objectAtIndex:0]].location != NSNotFound) {
						if ([propertyType rangeOfString:@"NSString"].location != NSNotFound) {
							sortDescriptor = [[NSSortDescriptor alloc] 
											  initWithKey:[itemSortDescriptor objectAtIndex:0]
											  ascending:[[itemSortDescriptor objectAtIndex:1] boolValue]
											  selector:@selector(localizedCaseInsensitiveCompare:)];
						}
						else {
							sortDescriptor = [[NSSortDescriptor alloc] 
											  initWithKey:[itemSortDescriptor objectAtIndex:0]
											  ascending:[[itemSortDescriptor objectAtIndex:1] boolValue]];
						}
						break;
					}
				}				
			}
			else {
				sortDescriptor = [[NSSortDescriptor alloc] initWithKey:[itemSortDescriptor objectAtIndex:0] ascending:[[itemSortDescriptor objectAtIndex:1] boolValue]];
			}
			free(vars);
			if (sortDescriptor != nil) {
                [sortDescriptors addObject:sortDescriptor];
            }
		}
		return sortDescriptors;
	}
	
	return nil;
}

#pragma mark -
#pragma mark  Save

- (BOOL)save:(NSError **)error {
	return [[AppHelper mainManagedObjectContext] save:error];
}

+ (BOOL) saveAll:(NSError **)error {
	return [[AppHelper mainManagedObjectContext] save:error];
}

#pragma mark -
#pragma mark Delete

- (void)delete {
	[[self mainManagedObjectContext] deleteObject:self];
	[[AppHelper mainManagedObjectContext] save:NULL];
}

+ (void)deleteWithPredicate:(NSString *)predicate {
	NSArray *allObjs = [self getWithPredicate:predicate];
	for (Model *obj in allObjs) {
		[[AppHelper mainManagedObjectContext] deleteObject:obj];
	}
	[[AppHelper mainManagedObjectContext] save:NULL];
}

#pragma mark - RollBack

+(void) RollBack{
    [[AppHelper mainManagedObjectContext] rollback];
}

#pragma mark -
#pragma mark Truncate

+ (void)truncate {
	NSArray *allObjs = [self getAll];
	for (Model *obj in allObjs) {
		[[AppHelper mainManagedObjectContext] deleteObject:obj];
	}
	[[AppHelper mainManagedObjectContext] save:NULL];
}

+ (void)truncateNoSave {
	NSArray *allObjs = [self getAll];
	for (Model *obj in allObjs) {
		[[AppHelper mainManagedObjectContext] deleteObject:obj];
	}
}

@end