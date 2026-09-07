package com.group16.stardewvalley.model.user;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.group16.stardewvalley.controller.GameController;
import com.group16.stardewvalley.model.Inventory;
import com.group16.stardewvalley.model.NPC.NPCInteraction;
import com.group16.stardewvalley.model.Request;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.food.BuffType;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.graphics.Heros;
import com.group16.stardewvalley.model.food.FoodFactory;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.Pos;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.graphics.PlayerGraphics;
import com.group16.stardewvalley.model.map.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;

import com.group16.stardewvalley.model.NPC.NPC;
import com.group16.stardewvalley.model.notification;
import com.group16.stardewvalley.model.shops.Shop;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.tools.Gadget;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.map.*;
import com.group16.stardewvalley.model.tools.WateringCan;


public class Player {
    private User user;
    private Farm farm;
    private HomeMap homeMap;
    private double energy;
    public static final double MAXENERGY = 2000;
    private boolean isEnergyUnlimited;
    private Pos position;
    private Inventory inventory;
    private int energyCeiling;
    private Gadget currentEquipment;
    private Item currentThing;
    private Set<Food> knownRecipes = new HashSet<>();
    private int coin = 500;
    private final int[] levelRanks = {450, 350, 250, 150};
    private int farmingAbilityLevel;
    private int farmingAbilityScore;
    private int miningAbilityLevel;
    private int miningAbilityScore;
    private int foragingAbilityLevel;
    private int foragingAbilityScore;
    private int fishingAbilityLevel;
    private int fishingAbilityScore;
    private boolean isFainted;
    private int rejectionCooldown;
    private final Map<NPC, NPCInteraction> dailyNPCInteraction;
    private final Map<Player, PlayerInteraction> dailyPlayerInteraction;
    private final List<Request> quests;
    private final List<notification> notifications;
    private int todayIncome;
    private final List<Request> requestHistory;
    private Player spouse;
    private final int[] relationshipRanks = {100, 200, 300, 400};
    private final int[] NPCRelationshipRanks = {200, 400, 600, 800};
    private BuffType buffer;
    private boolean isBuffActive;
    private int hourPastForBuff;
    private int finalHourBuff;
    private Location location;
    private Heros hero;
    private boolean isAtHome = false;


    //UI
    private PlayerGraphics playerGraphics;
    private float speed = 1;
    private boolean moving = false;
    private Direction currentDirection = Direction.DOWN;


    // مقدار های ماکسیمم هر توانایی رو هم در گیم ذخیره کردم سر جمع شه
    // تابعی برای بالا بردن لول شخص در این موارد نوشته نشده است
    public Player(User user) {
        this.user = user;
        farmingAbilityLevel = 0;
        miningAbilityLevel = 0;
        fishingAbilityLevel = 0;
        foragingAbilityLevel = 0;
        farmingAbilityScore = 0;
        miningAbilityScore = 0;
        foragingAbilityScore = 0;
        fishingAbilityScore = 0;
        isEnergyUnlimited = false;
        inventory = new Inventory();
        energyCeiling = 2000;
        energy = MAXENERGY;
        this.spouse = null;
        isFainted = false;
        this.dailyPlayerInteraction = new HashMap<>();
        this.dailyNPCInteraction = new HashMap<>();
        this.quests = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.requestHistory = new ArrayList<>();
        this.rejectionCooldown = 0;
        this.isBuffActive = false;
        hourPastForBuff = 0;
        finalHourBuff = 0;
        this.buffer = BuffType.NONE;
        this.location = null;

       this.hero = user.getHero();
    }

    //TODO یادت باشه ست کنی اینو وقتی بازی جدید میسازی


    public PlayerGraphics getPlayerGraphics() {
        return playerGraphics;
    }

    public void setPlayerGraphics(String spritePath, int frameWidth, int frameHeight) {
        this.playerGraphics = new PlayerGraphics(this, spritePath, frameWidth, frameHeight);
    }

    public HomeMap getHomeMap() {
        return homeMap;
    }

    public void setHomeMap(HomeMap homeMap) {
        this.homeMap = homeMap;
    }

    public boolean isAtHome() {
        return isAtHome;
    }

    public void setAtHome(boolean atHome) {
        isAtHome = atHome;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public String getName() {
        return user.getNickName();
    }

    public boolean isEnergyUnlimited() {
        return isEnergyUnlimited;
    }

    public void setEnergyUnlimited(boolean energyUnlimited) {
        isEnergyUnlimited = energyUnlimited;
    }

    public int getBaseEnergyCeiling() {
        return energyCeiling;
    }

    public void learnFood(Food food) {
        this.knownRecipes.add(food);
    }

    public int getFinalHourBuff() {
        return finalHourBuff;
    }

    public void setFinalHourBuff(int finalHourBuff) {
        this.finalHourBuff = finalHourBuff;
    }

    public int getEnergyCeiling() {
        return energyCeiling;
    }

    public void setEnergyCeiling(int energyCeiling) {
        this.energyCeiling = energyCeiling;
    }

    public int getHourPastForBuff() {
        return hourPastForBuff;
    }

    public void setPosition(int x, int y) {
        this.position.setY(y);
        this.position.setX(x);
    }

    public void setHourPastForBuff(int hourPastForBuff) {
        this.hourPastForBuff = hourPastForBuff;
    }

    public boolean isBuffActive() {
        return isBuffActive;
    }

    public void setBuffActive(boolean buffActive) {
        isBuffActive = buffActive;
    }

    public BuffType getBuffer() {
        return buffer;
    }

    public void setBuffer(BuffType buffer) {
        this.buffer = buffer;
    }

    public Result showNotifications() {
        StringBuilder sb = new StringBuilder();
        if (!notifications.isEmpty()) {
        for (notification notification : notifications) {
            sb.append(notification.getMessage());
            sb.append("\n");
        }
        return new Result(true, sb.toString());
        }
        return new Result(false, "you don't have any notification");
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<NPC, NPCInteraction> getDailyNPCInteraction() {
        return dailyNPCInteraction;
    }

    public Map<Player, PlayerInteraction> getDailyPlayerInteraction() {
        return dailyPlayerInteraction;
    }

    public void learnRecipe(Food food) {
        knownRecipes.add(food);
    }

    public Set<Food> getKnownRecipes() {
        return knownRecipes;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public Pos getPosition() {
        return position;
    }

    public void setPosition(Pos position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Farm getFarm() {
        return farm;
    }

    public int getFarmingAbilityLevel() {
        return farmingAbilityLevel;
    }

    public int getMiningAbilityLevel() {
        return miningAbilityLevel;
    }

    public NPCInteraction getOrCreateInteractionWith(NPC npc) {
        // اگر interaction وجود داشت، آن را برگردان
        if (dailyNPCInteraction.containsKey(npc)) {
            return dailyNPCInteraction.get(npc);
        }

        // اگر وجود نداشت، یک interaction جدید بساز و اضافه کن
        NPCInteraction newInteraction = new NPCInteraction();
        dailyNPCInteraction.put(npc, newInteraction);
        return newInteraction;
    }

    public Location getLocationLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getForagingAbilityLevel() {
        return foragingAbilityLevel;
    }

    public int getFishingAbilityLevel() {
        return fishingAbilityLevel;
    }

    public int getCoin() {
        if (spouse != null) {
            return getTotalCoin();
        }
        return coin;
    }

    public void setCoin(int amount) {
        this.coin = amount;
    }

    public String getGender() {
        return user.getGender();
    }

    public void addNotification(notification notification) {
        notifications.add(notification);
    }

    public PlayerInteraction getInteractionWith(Player player) {
        return dailyPlayerInteraction.get(player);
    }

    public void increaseCoin(int amount) {
        if (spouse != null) {
            spouse.increaseCoin(amount / 2);
            this.increaseCoin(amount / 2);
        }
        coin += amount;
    }

    public int getTotalCoin() {
        return coin + spouse.getCoin();
    }

    public Player getSpouse() {
        return spouse;
    }

    public void setSpouse(Player spouse) {
        this.spouse = spouse;
    }

    public void addFarmingAbilityScore(int amount) {
        farmingAbilityScore += amount;
        if (farmingAbilityScore >= levelRanks[0]) {
            farmingAbilityLevel = 4;
        }

        if (farmingAbilityScore >= levelRanks[1]) {
            farmingAbilityLevel = 3;
        }

        if (farmingAbilityScore >= levelRanks[2]) {
            farmingAbilityLevel = 2;
        }

        if (farmingAbilityScore >= levelRanks[3]) {
            farmingAbilityLevel = 1;
        }
    }

    public void addMiningAbilityScore(int amount) {
        miningAbilityScore += amount;
        if (miningAbilityScore >= levelRanks[0]) {
            miningAbilityLevel = 4;
        }

        if (miningAbilityScore >= levelRanks[1]) {
            miningAbilityLevel = 3;
        }

        if (miningAbilityScore >= levelRanks[2]) {
            miningAbilityLevel = 2;
        }

        if (miningAbilityScore >= levelRanks[3]) {
            miningAbilityLevel = 1;
        }
    }

    public void addNatureTourismAbilityScore(int amount) {
        foragingAbilityScore += amount;
        if (foragingAbilityScore >= levelRanks[0]) {
            foragingAbilityLevel = 4;
        }

        if (foragingAbilityScore >= levelRanks[1]) {
            foragingAbilityLevel = 3;
        }

        if (foragingAbilityScore >= levelRanks[2]) {
            foragingAbilityLevel = 2;
        }

        if (foragingAbilityScore >= levelRanks[3]) {
            foragingAbilityLevel = 1;
        }
    }

    public void addFishingAbilityScore(int amount) {
        fishingAbilityScore += amount;
        if (fishingAbilityScore >= levelRanks[0]) {
            fishingAbilityLevel = 4;
        }

        if (fishingAbilityScore >= levelRanks[1]) {
            fishingAbilityLevel = 3;
        }

        if (fishingAbilityScore >= levelRanks[2]) {
            fishingAbilityLevel = 2;
        }

        if (fishingAbilityScore >= levelRanks[3]) {
            fishingAbilityLevel = 1;
        }
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public boolean hasEnoughEnergy(int amount) {
        if (isEnergyUnlimited) {
            return true;
        }
        return energy > amount;
    }

    public void equip(Gadget gadget) {
        this.currentEquipment = gadget;
    }

    public Gadget getCurrentEquipment() {
        return currentEquipment;
    }

    public Item getCurrentThing() {
        return currentThing;
    }

    public void setCurrentThing(Item currentThing) {
        this.currentThing = currentThing;
    }

    public void decreaseEnergy(double amount) {
        energy = Math.max(0, energy - amount);
        if (energy < 1) energy = 0;
        if (energy == 0.0) faint();
    }

    public Tile getCurrentTile() {
        // طبیعتا باید  x, y این یارو هم معتبر باشه
        if (this.getX() < App.getActiveGame().getMapHeight() && this.getY() < App.getActiveGame().getMapWidth()) {
            return App.getActiveGame().getMap()[this.getX()][this.getY()];
        }
        return null;
    }

    public void increaseEnergy(int amount) {
        if (amount <= 0) {
            amount = 0;
        }
        energy = Math.min(energy + amount, MAXENERGY);
    }

    private boolean isInBound(int x, int y, TileType[][] map) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }


    public void setFaintStatus(boolean b) {
        this.isFainted = b;
    }

    public void setFriendshipLevelWith(int amount, String username) {
        Player target = App.getActiveGame().getPlayerByUsername(username);
        this.getInteractionWith(target).setFriendshipLevel(amount);
    }

    public List<notification> getNotifications() {
        return notifications;
    }

    public void setRejectionCooldown(int amount) {
        rejectionCooldown = amount;
    }

    public void resetForNewDay() {
        if (rejectionCooldown > 0) {
            energyCeiling = 150;
            rejectionCooldown--;
        }
        if (isFainted) {
            energy = (double) (energyCeiling * 75) / 100;
        } else {
            energy = energyCeiling;
        }

        this.isFainted = false;
        energy = energyCeiling;
        for (Map.Entry<NPC, NPCInteraction> entry : dailyNPCInteraction.entrySet()) {
            NPCInteraction interaction = entry.getValue();
            interaction.setMetToday(false);
            interaction.setGiftedToday(false);
        }

        for (Map.Entry<Player, PlayerInteraction> entry : dailyPlayerInteraction.entrySet()) {
            PlayerInteraction interaction = entry.getValue();
            interaction.setTalked(false);
            interaction.setFlowered(false);
            interaction.setGifted(false);
            interaction.setHugged(false);
            interaction.setTraded(false);
        }
        increaseCoin(todayIncome);
        this.todayIncome = 0;
        this.isEnergyUnlimited = false;
    }

    public void decreaseCoin(int amount) {
        this.coin -= amount;
    }

    public void faint(){
        this.isFainted = true;
        playerGraphics.startFainting();
        this.energy = 0;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public boolean isFainted() {
        return isFainted;
    }

    public PlayerInteraction getOrCreateInteractionWith(Player player) {

        if (dailyPlayerInteraction.containsKey(player)) {
            return dailyPlayerInteraction.get(player);
        }

        PlayerInteraction newInteraction = new PlayerInteraction();
        dailyPlayerInteraction.put(player, newInteraction);
        return newInteraction;
    }

    public void increaseTodayIncome(int amount){
        todayIncome += amount;
    }
}
