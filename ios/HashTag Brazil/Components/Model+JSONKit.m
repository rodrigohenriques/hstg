#import "Model+JSONKit.h"
#import "objc/runtime.h"

@implementation Model (Model_JSONKit)

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

+(id)objectFromDictionary: (NSDictionary*) dic {
    
    id instancia = [[self class] new];
    NSDateFormatter *formatterDate = [NSDateFormatter new];
    NSDateFormatter *formatterDateTime = [NSDateFormatter new];
    NSDateFormatter *formatterTime = [NSDateFormatter new];
    [formatterDate setDateFormat:@"yyyy-MM-dd"];
    [formatterDateTime setDateFormat:@"yyyy-MM-dd HH:mm"];
    [formatterTime setDateFormat:@"HH:mm"];
    unsigned int outCount, i;
    objc_property_t *properties = class_copyPropertyList([instancia class], &outCount);
    for(i = 0; i < outCount; i++) {
        objc_property_t property = properties[i];
        const char *propName = property_getName(property);
        if(propName) {
            const char *propType = getPropertyType(property);
            NSString *propertyName = [NSString stringWithUTF8String:propName];
            NSString *propertyType = [NSString stringWithUTF8String:propType];
            if (![propertyType isEqualToString:@"NSSet"]) {
                NSString* value = [dic objectForKey:propertyName];
                if (value != nil) {
                    if ([propertyType isEqualToString:@"NSDate"]) {
                        NSDate *date = nil;
                        if ([value length] == 5) {
                            date = [formatterTime dateFromString:value];
                        } else
                        if ([value length] == 10) {
                            date = [formatterDate dateFromString:value];
                        } else if ([value length] > 10) {
                            value = [value substringToIndex:16];
                            date = [formatterDateTime dateFromString:value];
                        }
                        
                        [instancia setValue:date forKey:propertyName];
                    } else if ([propertyType isEqualToString:@"NSString"]) {
                        NSString *trimValue = [value stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
                        [instancia setValue:trimValue forKey:propertyName];
                    } else {
                        [instancia setValue:value forKey:propertyName];
                    }
                }
            }
        }
    }
    free(properties); 
    return instancia;
    
}

-(BOOL) validaPropriedade:(NSString*)propertyType {
    
    return (propertyType != nil && ([propertyType isEqualToString:@"NSString"] ||[propertyType isEqualToString:@"NSDate"]||[propertyType isEqualToString:@"NSNumber"]||[propertyType isEqualToString:@"NSDecimalNumber"]));
    
}

-(NSDictionary*)dictionaryFromObject {
    NSDateFormatter *formatterDateTime = [NSDateFormatter new];
    [formatterDateTime setDateFormat:@"yyyy-MM-dd HH:mm"];

    NSMutableDictionary *mDic = [NSMutableDictionary new]; 
    unsigned int outCount, i;
    objc_property_t *properties = class_copyPropertyList([self class], &outCount);
    for(i = 0; i < outCount; i++) {
        objc_property_t property = properties[i];
        const char *propName = property_getName(property);
        const char *propType = getPropertyType(property);
        NSString *propertyType = [NSString stringWithUTF8String:propType];
        if ([self validaPropriedade:propertyType]) {
            if(propName) {
                NSString *propertyName = [NSString stringWithUTF8String:propName];
                id value = [self valueForKey:propertyName];
                if (value != nil) {
                    if ([propertyType isEqualToString:@"NSDate"]) {
                        NSString *sVal = nil;
                        sVal = [formatterDateTime stringFromDate:value];
                        [mDic setValue:sVal forKey:propertyName]; 
                    } else {
                        [mDic setValue:value forKey:propertyName];    
                    }
                    
                }
            }
        }
    }
    free(properties);
    return mDic;
}

@end
