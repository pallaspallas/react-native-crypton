
#import "RNCrypton.h"
#import <React/RCTLog.h>

@implementation RNCrypton

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE();


RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location)
{
    RCTLogInfo(@"Pretending to create an event %@ at %@", name, location);
} 


RCT_REMAP_METHOD(Hi,
                 message:(NSString *)message
                 findEventsWithResolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{


  if (message) {
    resolve(message);
  } else {
    reject(@"no_events", @"There were no input", RCTErrorWithMessage(@"ol") );
  };
}


@end
  

