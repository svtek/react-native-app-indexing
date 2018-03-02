package com.ergizgizer.reactnative.appindexing;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class AppIndexingModule extends ReactContextBaseJavaModule {

    public static final String LOG_TAG = AppIndexingModule.class.getSimpleName();

    private static final String RESULT_KEY = "result";

    public AppIndexingModule(ReactApplicationContext reactContext) { super(reactContext); }

    @Override
    public String getName() {
        return "AppIndexingModule";
    }

    @ReactMethod
    public void initializeApp(ReadableMap options, Callback callback) {
        Log.d(LOG_TAG, "I was in method initalizeApp");

        FirebaseOptions.Builder builder = new FirebaseOptions.Builder();

        builder.setApiKey(options.getString("apiKey"));
        builder.setApplicationId(options.getString("appId"));
        builder.setProjectId(options.getString("projectId"));

        if (FirebaseApp.getInstance() == null)
            FirebaseApp.initializeApp(getReactApplicationContext(), builder.build());

        WritableMap response = Arguments.createMap();
        response.putString(RESULT_KEY, "success");
        callback.invoke(null, response);

    }

    @ReactMethod
    public void syncStickers(ReadableMap stickerPack){
        Log.d(LOG_TAG, "I was in method syncStickers");
        Log.d(LOG_TAG, stickerPack.toString());
        AppIndexingUpdateService.enqueueWork(getReactApplicationContext(), stickerPack);
    }
}
