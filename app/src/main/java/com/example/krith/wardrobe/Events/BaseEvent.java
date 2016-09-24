package com.example.krith.wardrobe.Events;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by krith on 24/09/16.
 */

public class BaseEvent {

    public void post() {
        EventBus.getDefault().post(this);
    }
}