package com.group16.stardewvalley.model.NPC;

import java.util.ArrayList;
import java.util.List;

public class NPCInteraction {
    private boolean metToday;
    private boolean giftedToday;
    private List<String> dialogueHistory = new ArrayList<>();
    private int friendshipNPCScore;
    private int friendshipNPCLevel;
    private final int[] NPCRelationshipRanks = {200, 400, 600, 800};

    public NPCInteraction() {
        this.dialogueHistory = new ArrayList<>();
        this.giftedToday = false;
        this.metToday = false;
        this.friendshipNPCLevel = 0;
        this.friendshipNPCScore = 0;
    }

    public int getFriendshipNPCLevel() {
        return friendshipNPCLevel;
    }

    public int getFriendshipNPCScore() {
        return friendshipNPCScore;
    }

    public void increaseFriendshipLevelScore(int amount) {
        if (amount < 0) return;
        if (friendshipNPCScore + amount >= 799) {
            friendshipNPCScore = 799;
        } else {
            friendshipNPCScore += amount;
        }

        if (friendshipNPCScore >= NPCRelationshipRanks[2]) {
            friendshipNPCLevel = 3;
        } else if (friendshipNPCScore >= NPCRelationshipRanks[1]) {
            friendshipNPCLevel = 2;
        } else if (friendshipNPCScore >= NPCRelationshipRanks[0]) {
            friendshipNPCLevel = 1;
        }
    }

    public boolean isMetToday() {
        return metToday;
    }

    public boolean isGiftedToday() {
        return giftedToday;
    }

    public void setMetToday(Boolean b) {
        metToday = b;
    }

    public void setGiftedToday(Boolean b) {
        giftedToday = b;
    }

    public void addDialogue(String line) {
        dialogueHistory.add(line);
    }

    public List<String> getDialogueHistory() {
        return dialogueHistory;
    }
}
