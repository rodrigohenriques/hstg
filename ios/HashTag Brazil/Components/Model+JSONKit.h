#import <Foundation/Foundation.h>
#import "Model.h"

@interface Model (Model_JSONKit)

+ (id)objectFromDictionary:(NSDictionary *)dic;
- (NSDictionary *)dictionaryFromObject;

@end
