package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.time.TimeDate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Shop {
    private String shopName;
    private String shopkeeperName;
    protected final Map<Item, Integer> dailyLimit = new HashMap<>();
    protected final Map<Item, Integer> soldToday = new HashMap<>();
    private final int START_TIME;
    private final int END_TIME;
    private int balance;
    private final TimeDate timeDate;
    private final PlaceType placeType;

    public Shop(String shopName,
                String shopkeeperName,
                int START_TIME,
                int END_TIME,
                PlaceType placeType) {
        this.shopName = shopName;
        this.shopkeeperName = shopkeeperName;
        this.START_TIME = START_TIME;
        this.END_TIME = END_TIME;
        this.balance = 0;
        timeDate = TimeDate.getInstance(App.getActiveGame());
        this.placeType = placeType;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public String getShopName() {
        return shopName;
    }

    public boolean canSell(Item item) {
        if (!dailyLimit.containsKey(item)) {
            return false;
        }

        int limit = dailyLimit.get(item);
        int sold = soldToday.get(item);

        return sold < limit;
    }

    public void addBalance(int amount) {
        balance += amount;
    }

    public void sellItem(Item item) {

        if (canSell(item)) {
            int sold = soldToday.get(item);
            soldToday.put(item, sold + 1);
            addBalance(item.getPrice());    //**
        }
    }

    public int getBalance() {
        return balance;
    }

    public void increaseBalance(int amount) {
        balance += amount;
    }

    public void addItem(Item item, int dailyLimit) {
        this.dailyLimit.put(item, dailyLimit);
        this.soldToday.put(item, 0);
    }

    public boolean isOpen() {
        return (START_TIME <= timeDate.getHour() && timeDate.getHour() <= END_TIME);
    }

    public Set<Item> getAvailableItems() {
        Set<Item> availableItems = new HashSet<>();
        for (Map.Entry<Item, Integer> entry : dailyLimit.entrySet()) {
            Item item = entry.getKey();
            int limit = entry.getValue();
            int sold = soldToday.getOrDefault(item, 0);

            if (sold < limit) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }

    public Set<Item> getAllProducts() {
        return new HashSet<>(dailyLimit.keySet());
    }

    public Item findItemByName(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            return null;
        }

        for (Item item : dailyLimit.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public int getAvailableCountForToday(Item item) {
        if (!dailyLimit.containsKey(item)) {
            return 0;
        }

        int limit = dailyLimit.get(item);
        int sold = soldToday.getOrDefault(item, 0);

        return limit - sold;
    }

}
