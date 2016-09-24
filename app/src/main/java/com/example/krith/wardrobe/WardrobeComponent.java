package com.example.krith.wardrobe;

import com.example.krith.wardrobe.Fragments.PantFragment;
import com.example.krith.wardrobe.Fragments.ShirtFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by krith on 23/09/16.
 */

@Singleton
@Component(modules = WardrobeModule.class)
public interface WardrobeComponent {
    void inject(ShirtFragment fragment);

    void inject(PantFragment fragment);
}
