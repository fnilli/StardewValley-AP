package com.group16.stardewvalley.model.user;

import com.group16.stardewvalley.model.items.Item;

import java.util.*;

public class PlayerInteraction {
    private boolean talked;
    private boolean traded;
    private boolean gifted;
    private boolean hugged;
    private boolean flowered;
    private int friendshipLevel;
    private int friendshipScore;
    private final int[] relationshipRanks = {100, 200, 300, 400};
    private List<String> dialogueHistory;
    private final Map<Item, Integer> gifts;

    public PlayerInteraction() {
        talked = false;
        traded = false;
        gifted = false;
        hugged = false;
        flowered = false;
        friendshipLevel = 0;
        friendshipScore = 0;
        dialogueHistory = new ArrayList<>();
        gifts = new LinkedHashMap<>();
    }

    public void addGift(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Don't give null");
        }
        int newIndex = GiftIdGenerator.generateNewId();
        gifts.put(item, newIndex);
        gifted = true;
    }

    public void setTalked(Boolean b) {
        talked = b;
    }

    public void setFlowered(boolean flowered) {
        this.flowered = flowered;
    }

    public void setHugged(boolean hugged) {
        this.hugged = hugged;
    }

    public void setGifted(boolean gifted) {
        this.gifted = gifted;
    }

    public Map<Item, Integer> getGifts() {
        return gifts;
    }

    public void setTraded(boolean traded) {
        this.traded = traded;
    }

    public List<String> getDialogueHistory() {
        return dialogueHistory;
    }

    public void setFriendshipLevel(int newLevel) {
        if (newLevel > 3) {
            return;
        }
        if (newLevel == 3) {
            friendshipScore = 300;
        }
        friendshipLevel = newLevel;
    }

    public boolean isFlowered() {
        return flowered;
    }

    public boolean isGifted() {
        return gifted;
    }

    public boolean isHugged() {
        return hugged;
    }

    public boolean isTalked() {
        return talked;
    }

    public boolean isTraded() {
        return traded;
    }

    public int getFriendshipLevel() {
        return friendshipLevel;
    }

    public void setFriendshipScore(int amount) {
        friendshipScore = 0;
    }

    public int getFriendshipScore() {
        return friendshipScore;
    }

    public void addDialogue(String line) {
        dialogueHistory.add(line);
    }

    public void increaseFriendshipLevelScore(int amount) {
        if (amount < 0) return;
        if (friendshipScore + amount >= 799) {
            friendshipScore = 799;
        } else {
            friendshipScore += amount;
        }

        if (friendshipScore >= relationshipRanks[2]) {
            friendshipLevel = 3;
        } else if (friendshipScore >= relationshipRanks[1]) {
            friendshipLevel = 2;
        } else if (friendshipScore >= relationshipRanks[0]) {
            friendshipLevel = 1;
        }
    }
}
