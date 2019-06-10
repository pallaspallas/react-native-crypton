//#import "RNCrypton.h"
#import <Foundation/Foundation.h>
#import "React/RCTBridgeModule.h"

//@implementation RNCrypton
//
//- (dispatch_queue_t)methodQueue
//{
//    return dispatch_get_main_queue();
//}
//
//RCT_EXPORT_MODULE()

//RCT_REMAP_METHOD(Hi,
//                 message:(NSString *)message
//                 findEventsWithResolver:(RCTPromiseResolveBlock)resolve
//                 rejecter:(RCTPromiseRejectBlock)reject)
//{
//
//    if (message) {
//        resolve(message);
//    } else {
//        reject(@"no_events", @"There were no events", @"message not found");
//    };
//}



@interface RCT_EXTERN_MODULE(RNCrypton, NSObject);
//
RCT_EXTERN_METHOD(AES_CBC_256_encryption:(NSString *)message
                  key:(NSString *)key
                  iv:(NSString *)iv
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  )

RCT_EXTERN_METHOD(AES_CBC_256_decryption:(NSString *)Cipher
                  key:(NSString *)key
                  iv:(NSString *)iv
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  )

RCT_EXTERN_METHOD(AES_CBC_256_pbkdf2_Encrypt:(NSString *)message
                  password:(NSString *)password
                  splitter:(NSString *)splitter
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  )


RCT_EXTERN_METHOD(AES_CBC_256_pbkdf2_Decrypt:(NSString *)Cipher
                  password:(NSString *)password
                  splitter:(NSString *)splitter
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  )

@end





  
