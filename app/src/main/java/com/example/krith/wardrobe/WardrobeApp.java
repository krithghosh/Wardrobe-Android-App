package com.example.krith.wardrobe;

import android.app.Application;
import android.content.Context;

/**
 * Created by krith on 23/09/16.
 */

public class WardrobeApp extends Application {

    private WardrobeComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerWardrobeComponent.builder().wardrobeModule(new WardrobeModule(this)).build();
    }

    public static WardrobeComponent getComponent(Context context) {
        return ((WardrobeApp) context.getApplicationContext()).component;
    }
}
