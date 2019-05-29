
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNCryptonModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNCryptonModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNCrypton";
  }

  @ReactMethod
  public void Answer(String answer, Promise promise){
    promise.resolve(answer);
  }
}