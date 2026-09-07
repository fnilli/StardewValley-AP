package com.group16.stardewvalley.model.user;

import java.util.concurrent.atomic.AtomicInteger;

public class GiftIdGenerator {
    private static final AtomicInteger globalIdCounter = new AtomicInteger(1);

    public static int generateNewId() {
        return globalIdCounter.getAndIncrement();
    }

    public static int getCurrentId() {
        return globalIdCounter.get();
    }
}
