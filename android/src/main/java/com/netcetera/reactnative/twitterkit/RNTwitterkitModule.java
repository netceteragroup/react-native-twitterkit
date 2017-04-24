
package com.netcetera.reactnative.twitterkit;

import android.content.Context;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNTwitterKitModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNTwitterKitModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNTwitterkit";
  }

  //this function can be used to send a value to JS
  //height can be used as a parameter
  public static void sendToJs(Context context){

    WritableMap params = Arguments.createMap();
    params.putString("height", "350");
    ReactContext reactContext = (ReactContext)context;

    reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit("TweetDidLoad", params);
  }
}