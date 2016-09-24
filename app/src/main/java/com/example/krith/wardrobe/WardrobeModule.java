package com.example.krith.wardrobe;

import android.app.Application;

import com.example.krith.wardrobe.Database.DbModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krith on 23/09/16.
 */

@Module(includes = {DbModule.class,})

public class WardrobeModule {

    private final Application application;

    WardrobeModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }
}
