#import <Foundation/Foundation.h>
#import "RCTBridgeModule.h"

#define RCT_EXTERN_MODULE(objc_name, objc_supername) \
  RCT_EXTERN_REMAP_MODULE(objc_name, objc_name, objc_supername)

#define RCT_EXTERN_REMAP_MODULE(js_name, objc_name, objc_supername) \
  objc_name : objc_supername \
  @end \
  @interface objc_name (RCTExternModule) <RCTBridgeModule> \
  @end \
  @implementation objc_name (RCTExternModule) \
  RCT_EXPORT_MODULE(js_name)

#define RCT_EXTERN_METHOD(method) \
  RCT_EXTERN_REMAP_METHOD(, method)

#define RCT_EXTERN_REMAP_METHOD(js_name, method) \
  - (void)__rct_export__##method { \
    __attribute__((used, section("__DATA,RCTExport"))) \
    __attribute__((__aligned__(1))) \
    static const char *__rct_export_entry__[] = { __func__, #method, #js_name }; \
  }