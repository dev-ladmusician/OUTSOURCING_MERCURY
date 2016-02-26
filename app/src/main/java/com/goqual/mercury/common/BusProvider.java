package com.goqual.mercury.common;

import com.squareup.otto.Bus;

/**
 * Created by ladmusician on 16. 1. 20..
 */
public class BusProvider {
    private static Bus EventBus = null;

    public static Bus getEventBus() {
        if (EventBus == null) {
            EventBus = new Bus();
        }
        return EventBus;
    }
    private BusProvider() {
        // No instances.
    }
}
