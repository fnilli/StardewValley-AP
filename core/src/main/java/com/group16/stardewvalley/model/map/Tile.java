package com.group16.stardewvalley.model.map;

import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.agriculture.*;
import com.group16.stardewvalley.model.items.Stone;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tile {
    private final Random random = new Random();
    private TileType type;
    private Item item;
    private Crop crop;
    private Tree tree;
    private Location location;
    private boolean isFertilized;
    private FertilizerType fertilizerType;
    private boolean hasWater;
    private boolean isBurned;
    private boolean isPlowed;

    public Tile(TileType tileType) {
        location = Location.Game;
        isBurned = false;
        isPlowed = false;
        isFertilized = false;
        if (tileType == TileType.Tree) {
            this.type = TileType.Ground;
            TreeType treeType = getRandomForagingTree();
            if (treeType != null) {
                this.tree = new Tree(treeType);
                this.tree.setStage((random.nextInt() % 5) + 1);
            }
        }
        else if (tileType == TileType.Stone) {
            this.type = TileType.Quarry;
            this.item = new Stone("Farm_Boulder.png", 20);
        }
        else if (tileType == TileType.Forage) {
            this.type = TileType.Ground;
            CropType cropType;
            if (random.nextBoolean()) {
                cropType = getRandomForagingCrop();
                this.item = new ForagingCrop(cropType.getName(), 50, cropType);
            }
            else {
                cropType = getRandomForagingSeed();
                if (cropType != null) {
                    this.crop = new Crop(cropType);
                }
            }
        }
        else if (tileType.equals(TileType.Rock)) {
            this.type = TileType.Ground;
            String[] stones = {"343", "450", "Farm_Boulder.png"};
            this.item = new Stone(stones[random.nextInt(3)], 20);
        }
        else if (tileType == TileType.MineralForage) {
            this.type = TileType.Quarry;
            MineralType mineralType = getRandomForagingMineral();
            this.item = new Mineral(mineralType.getName(), 150, mineralType);
        }
        else {
            this.type = tileType;
        }
    }

    private boolean buildingOrigin;
    public boolean isBuildingOrigin() { return buildingOrigin; }
    public void setBuildingOrigin(boolean origin) { this.buildingOrigin = origin; }


    public boolean isBurned() {
        return isBurned;
    }

    public void setBurned(boolean burned) {
        isBurned = burned;
    }

    public boolean isHasWater() {
        return hasWater;
    }

    public void setHasWater(boolean hasWater) {
        this.hasWater = hasWater;
    }

    public FertilizerType getFertilizerType() {
        return fertilizerType;
    }

    public void setFertilizerType(FertilizerType fertilizerType) {
        this.fertilizerType = fertilizerType;
    }

    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public boolean isTileEmpty() {
        return item == null && crop == null && tree == null && type != TileType.Lake;
    }

    public CropType getRandomForagingSeed() {
        SeedType[] seeds = {
                SeedType.JAZZ_SEEDS,
                SeedType.CARROT_SEEDS,
                SeedType.CAULIFLOWER_SEEDS,
                SeedType.COFFEE_BEANS,
                SeedType.GARLIC_SEEDS,
                SeedType.BEAN_STARTER,
                SeedType.KALE_SEEDS,
                SeedType.PARSNIP_SEEDS,
                SeedType.POTATO_SEEDS,
                SeedType.RHUBARB_SEEDS,
                SeedType.STRAWBERRY_SEEDS,
                SeedType.TULIP_BULB,
                SeedType.RICE,
                SeedType.BLUEBERRY_SEEDS,
                SeedType.CORN_SEEDS,
                SeedType.HOPS_STARTER,
                SeedType.PEPPER_SEEDS,
                SeedType.MELON_SEEDS,
                SeedType.POPPY_SEEDS,
                SeedType.RADISH_SEEDS,
                SeedType.RED_CABBAGE_SEEDS,
                SeedType.STARFRUIT_SEEDS,
                SeedType.SPANGLE_SEEDS,
                SeedType.SQUASH_SEEDS,
                SeedType.SUNFLOWER_SEEDS,
                SeedType.TOMATO_SEEDS,
                SeedType.WHEAT_SEEDS,
                SeedType.AMARANTH_SEEDS,
                SeedType.ARTICHOKE_SEEDS,
                SeedType.BEET_SEEDS,
                SeedType.BOK_CHOY_SEEDS,
                SeedType.BROCCOLI_SEEDS,
                SeedType.CRANBERRY_SEEDS,
                SeedType.EGGPLANT_SEEDS,
                SeedType.FAIRY_SEEDS,
                SeedType.GRAPE_STARTER,
                SeedType.PUMPKIN_SEEDS,
                SeedType.YAM_SEEDS,
                SeedType.RARE_SEED,
                SeedType.POWDERMELON_SEEDS,
                SeedType.ANCIENT_SEED,
                SeedType.MIXED_SEED
        };
        SeedType seed = seeds[random.nextInt(seeds.length)];
        return findCropTypeBySeed(seed);
    }

    public CropType getRandomForagingCrop() {
        List<CropType> foragingCrops = Arrays.stream(CropType.values())
                .filter(CropType::isForaging)
                .toList();
        return foragingCrops.get(random.nextInt(foragingCrops.size()));
    }

    private CropType findCropTypeBySeed(SeedType seed) {
        for (CropType cropType : CropType.values()) {
            if (cropType.getSource() == seed) {
                return cropType;
            }
        }
        return null;
    }

    public TreeType getRandomForagingTree() {
        SeedType[] seeds = {
                SeedType.ACORNS,
                SeedType.MAPLE_SEEDS,
                SeedType.PINE_CONES,
                SeedType.MAHOGANY_SEEDS,
                SeedType.MUSHROOM_TREE_SEEDS
        };
        SeedType seed = seeds[random.nextInt(seeds.length)];
        return findTreeTypeBySeed(seed);
    }

    public MineralType getRandomForagingMineral() {
        MineralType[] mineralTypes = MineralType.values();
        return mineralTypes[random.nextInt(mineralTypes.length)];
    }

    private TreeType findTreeTypeBySeed(SeedType seed) {
        return AgricultureController.findTreeTypeBySeed(seed);
    }




    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Crop getPlantedSeed() {
        return crop;
    }

    public void setPlantedSeed(Crop crop) {
        this.crop = crop;
    }

    public Item getItem() {
        return item;
    }

    public void setItems(Item item) {
        this.item = item;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }
}
