//
//  RNCryptonBridge.m
//  RNCrypton
//
//  Created by AmirHossein on 6/9/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNCrypton, NSObject)

RCT_EXTERN_METHOD( message:(NSString *)message)

@end
