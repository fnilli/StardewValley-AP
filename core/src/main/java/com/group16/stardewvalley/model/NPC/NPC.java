package com.group16.stardewvalley.model.NPC;

import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.user.Player;

import java.util.HashMap;
import java.util.Map;

public class NPC {
    private NPCType npcType;
    private final Map<String, Integer> favoriteItems;
    private final Map<Player, Integer> friendshipNPCLevel;
    private final Map<Player, Integer> friendshipNPCScore;
    private final int[] relationshipRanks = {200, 400, 600, 800};
    private Pos NPCPosition;

    public NPC(NPCType npcType) {
        this.npcType = npcType;
        this.friendshipNPCLevel = new HashMap<>();
        this.favoriteItems = new HashMap<>();
        this.friendshipNPCScore = new HashMap<>();
        this.NPCPosition = npcType.getPlaceType().getStartPosition();

    }

    public void setNPCPosition(Pos NPCPosition) {
        this.NPCPosition = NPCPosition;
    }

    public NPCType getNpcType() {
        return npcType;
    }

    public Pos getNPCPosition() {
        return NPCPosition;
    }

    public String getName() {
        return npcType.getName();
    }

    public Map<Player, Integer> getFriendshipNPCLevel() {
        return friendshipNPCLevel;
    }

    public Map<Player, Integer> getFriendshipNPCScore() {
        return friendshipNPCScore;
    }
}