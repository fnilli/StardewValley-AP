package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.group16.stardewvalley.controller.agriculture.AgricultureController;
import com.group16.stardewvalley.model.agriculture.*;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.items.Flower;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.items.Stone;
import com.group16.stardewvalley.model.map.Direction;
import com.group16.stardewvalley.model.shops.Building;
import com.group16.stardewvalley.model.time.TimeDate;
import com.group16.stardewvalley.model.tools.Gadget;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.math.Rectangle.tmp;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("assets/skin-rainbow/rainbow-ui.json"));

    private final String crop = "Foraging/Grape.png";
    private final String tree = "Trees/Pine_Stage_4.png";
    private final String item = "Crafting/Stone.png";
    private final String burn = "Flooring/Flooring_33.png";
    private final String water = "Flooring/Flooring_47.png";
    private final String fertalize = "Fertilizer/Stardew-texture_Basic-Fertilizer.png";

    private Texture cropTexture = new Texture(crop);
    private Texture seedTexture = new Texture("Farming/Bok_Choy_Seeds.png");
    private Texture treeTexture = new Texture(tree);
    private Texture itemTexture = new Texture(item);
    public Texture getBasicItemTexture() {
        return itemTexture;
    }
    private Texture burnTexture = new Texture(burn);
    private Texture waterTexture = new Texture(water);
    private Texture fertalizeTexture = new Texture(fertalize);
    private Texture animalTextur = new Texture("Animals/Dog.png");

    private final Map<String, TextureRegion> treeRegions = new HashMap<>();
    private final Map<String, TextureRegion> cropRegions = new HashMap<>();
    private final Map<String, Texture> mineralTextures = new HashMap<>();
    private final Map<String, Texture> stoneTextures = new HashMap<>();
    private final Map<String, Texture> buildingTextures = new HashMap<>();
    private final Map<String, Texture> giantCropTextures = new HashMap<>();
    private final Map<String, Texture> foodTextures = new HashMap<>();
    private final Map<String, Texture> ingredientTextures = new HashMap<>();
    private final Map<String, Texture> craftingTextures = new HashMap<>();
    private final Map<String, Texture> craftingIngredientTextures = new HashMap<>();
    private final Map<String, Texture> animalTextures = new HashMap<>();
    private final Map<String, Texture> seedTextures = new HashMap<>();
    private final Map<String, Texture> toolTextures = new HashMap<>();

    private Map<String, Map<Direction, Animation<TextureRegion>>> animalAnimations = new HashMap<>();

    private final Texture houseTexture = new Texture("House/House_1.png");

    private Sound eatingSound = Gdx.audio.newSound(Gdx.files.internal("SFX/eating.mp3"));



    //private final Music backgroundMusic;
    //private final Animation<Texture> character1_idle_frames = new Animation<>(0.1f, character1_idle0_tex);


    private GameAssetManager(){
        /*
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("musics/alex-productions-epic-cinematic-gaming-cyberpunk-reset(chosic.com).mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
         */
    }

    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Sound getEatingSound() {
        return eatingSound;
    }

    public TextureRegion getCropRegion(Crop crop) {
        String name = crop.getCropType().getName().replace(" ", "_");
        int stage = crop.getStage();
        String key = name + "_stage_" + stage;

        if (!cropRegions.containsKey(key)) {
            try {
                Texture texture = new Texture("Crops/" + name + "_Stage_" + stage + ".png");
                TextureRegion region = new TextureRegion(texture);
                cropRegions.put(key, region);
            } catch (Exception e) {
                cropRegions.put(key, new TextureRegion(cropTexture));
            }
        }
        return cropRegions.get(key);
    }

    public  Texture getGiantCropTexture(Crop crop) {
        String name = crop.getCropType().getName();
        if (!giantCropTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Crops/Giant_" + name + ".png");
                giantCropTextures.put(name, texture);
            } catch (Exception e) {
                giantCropTextures.put(name, cropTexture);
            }
        }
        return giantCropTextures.get(name);
    }


    public TextureRegion getTreeRegion(Tree tree) {
        String name = tree.getTreeType().getName().replace(" ", "_");
        int stage = tree.getStage();
        String season = App.getActiveGame().getTimeDate().getSeason().getName().toLowerCase();
        String fruitState = tree.HasFruit() ? "Fruit" : "";
        String key = name + "_stage_" + stage + "_season_" + season + fruitState;

        if (!treeRegions.containsKey(key)) {
            try {
                if (stage != 5) {
                    Texture texture = new Texture("Trees/" + name + "_Stage_" + stage + ".png");
                    TextureRegion region = new TextureRegion(texture);
                    treeRegions.put(key, region);
                } else {
                    if (tree.HasFruit()) {
                        try {
                            Texture texture = new Texture("Trees/" + name + "_Stage_5_Fruit.png");
                            treeRegions.put(key, new TextureRegion(texture));
                        } catch (Exception e) {
                            TextureRegion region = getTextureRegion(name, season);
                            treeRegions.put(key, region);
                        }
                    }
                    else if (AgricultureController.isTreeNotSeasonal(tree.getTreeType())) {
                        Texture texture = new Texture("Trees/" + name + "_Stage_5.png");
                        treeRegions.put(key, new TextureRegion(texture));
                    } else {
                        TextureRegion region = getTextureRegion(name, season);
                        treeRegions.put(key, region);
                    }
                }
            } catch (Exception e) {
                treeRegions.put(key, new TextureRegion(treeTexture));
            }
        }

        return treeRegions.get(key);
    }

    private static TextureRegion getTextureRegion(String name, String season) {
        Texture fullTexture = new Texture("Trees/" + name + "_Stage_5.png");

        int treeWidth, treeHeight;

        treeWidth = fullTexture.getWidth() / 4;
        treeHeight = fullTexture.getHeight();

        int seasonIndex = switch (season) {
            case "summer" -> 1;
            case "fall" -> 2;
            case "winter" -> 3;
            default -> 0;
        };

        return new TextureRegion(
            fullTexture,
            seasonIndex * (treeWidth + 2),
            0,
            treeWidth,
            treeHeight
        );
    }


    public Texture getItemTexture(Item item) {
        if (item instanceof Mineral mineral) {
            String name = mineral.getType().getName().replace(" ", "_");
            if (!mineralTextures.containsKey(name)) {
                try {
                    Texture texture = new Texture("Mineral/" + name + ".png");
                    mineralTextures.put(name, texture);
                } catch (Exception e) {
                    mineralTextures.put(name, itemTexture);
                }
            }
            return mineralTextures.get(name);
        }
        if (item instanceof Building building) {
            String name = building.getName().replace(" ", "_");
            if (!buildingTextures.containsKey(name)) {
                try {
                    System.out.println("Trying to load building texture: Shops/Carpenter/" + name + ".png");
                    Texture texture = new Texture("Building/" + name + ".png");
                    buildingTextures.put(name, texture);
                } catch (Exception e) {
                    buildingTextures.put(name, itemTexture);
                }
            }
            return buildingTextures.get(name);
        }

        else if (item instanceof Stone stone) {
            String name = stone.getName();
            if (name.equals("Farm_Boulder.png")) {
                if (!stoneTextures.containsKey(name)) {
                    stoneTextures.put(name, new Texture("Rock/Farm_Boulder.png"));
                }
                return stoneTextures.get(name);
            }
            if (!stoneTextures.containsKey(name)) {
                try {
                    Texture texture = new Texture("Rock/Stone_Index" + name + ".png");
                    stoneTextures.put(name, texture);
                } catch (Exception e) {
                    stoneTextures.put(name, itemTexture);
                }
            }
            return stoneTextures.get(name);
        }
        else if (item instanceof FoodIngredient foodIngredient) {
            return getIngredientTexture(foodIngredient.getType());
        }
        else if (item instanceof Flower) {
            return new Texture("Crops/Sunflower.png");
        }
        else if (item instanceof Food food) {
            return getFoodTexture(food);
        }
        else if (item instanceof Seed seed) {
            String name = seed.getName().replace(" ", "_");
            if (!seedTextures.containsKey(name)) {
                try {
                    Texture texture = new Texture("Farming/" + name + ".png");
                    seedTextures.put(name, texture);
                } catch (Exception e) {
                    seedTextures.put(name, seedTexture);
                }
            }
            return seedTextures.get(name);
        }
        else if (item instanceof Gadget gadget) {
            String name = gadget.getName();
            if (!toolTextures.containsKey(name)) {
                try {
                    Texture texture = new Texture("tools/" + name + ".png");
                    toolTextures.put(name, texture);
                } catch (Exception e) {
                    toolTextures.put(name, seedTexture);
                }
            }
            return toolTextures.get(name);
        }

        return itemTexture;
    }


    public Texture getBurnTexture() {
        return burnTexture;
    }

    public Texture getWaterTexture() {
        return waterTexture;
    }

    public Texture getFertalizeTexture() {
        return fertalizeTexture;
    }

    public Texture getHouseTexture() {
        return houseTexture;
    }

    public Texture getFoodTexture(Food food) {
        String name = food.getName().replace(" ", "_");
        if (!foodTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Recipe/" + name + ".png");
                foodTextures.put(name, texture);
            } catch (Exception e) {
                foodTextures.put(name, itemTexture);
            }
        }
        return foodTextures.get(name);
    }

    public Texture getIngredientTexture(Ingredient ingredient) {
        String name = ingredient.getName().replace(" ", "_");
        if (!ingredientTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Ingredient/" + name + ".png");
                ingredientTextures.put(name, texture);
            } catch (Exception e) {
                ingredientTextures.put(name, itemTexture);
            }
        }
        return ingredientTextures.get(name);
    }

    public Texture getCraftingTexture(CraftingRecipes craftingItem) {
        String name = craftingItem.getName().replace(" ", "_");
        if (!craftingTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Crafting/" + name + ".png");
                craftingTextures.put(name, texture);
            } catch (Exception e) {
                craftingTextures.put(name, itemTexture);
            }
        }
        return craftingTextures.get(name);
    }

    public Texture getCraftingIngredientTexture(Ingredient craftingItem) {
        String name = craftingItem.getName().replace(" ", "_");
        if (!craftingIngredientTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Crafting/" + name + ".png");
                craftingIngredientTextures.put(name, texture);
            } catch (Exception e) {
                craftingIngredientTextures.put(name, itemTexture);
            }
        }
        return craftingIngredientTextures.get(name);
    }

    public Texture getAnimalTexture(Animal animal) {
        String name = animal.getFromShopType().getName().replace(" ", "_");
        if (!animalTextures.containsKey(name)) {
            try {
                Texture texture = new Texture("Animals/" + name + ".png");
                animalTextures.put(name, texture);
            } catch (Exception e) {
                animalTextures.put(name, animalTextur);
            }
        }
        return animalTextures.get(name);
    }

    public Map<Direction, Animation<TextureRegion>> getAnimalAnimations(Animal animal) {
        String name = animal.getName().replace(" ", "_");

        if (!animalAnimations.containsKey(name)) {
            try {
                Texture texture = new Texture("Animals/" + name + ".png");

                int frameCols = 4; // columns per row
                int frameRows = 8; // total rows in sprite sheet (your dog sheet has 8)
                int frameWidth  = texture.getWidth() / frameCols;
                int frameHeight = texture.getHeight() / frameRows;

                TextureRegion[][] tmp = TextureRegion.split(texture, frameWidth, frameHeight);

                Map<Direction, Animation<TextureRegion>> animMap = new HashMap<>();
                animMap.put(Direction.DOWN,  new Animation<>(0.15f, tmp[0]));
                animMap.put(Direction.RIGHT, new Animation<>(0.15f, tmp[1]));
                animMap.put(Direction.UP,    new Animation<>(0.15f, tmp[2]));
                animMap.put(Direction.LEFT,  new Animation<>(0.15f, tmp[3]));

                // loop all animations
                for (Animation<TextureRegion> anim : animMap.values()) {
                    anim.setPlayMode(Animation.PlayMode.LOOP);
                }

                animalAnimations.put(name, animMap);

            } catch (Exception e) {
                // fallback: empty map so we don't crash
                animalAnimations.put(name, new HashMap<>());
            }
        }
        return animalAnimations.get(name);
    }


    public Texture getTexture(String path) {
        return new Texture(path);
    }

    public void dispose() {

    }


    //SFX
    private final Sound wonSound = Gdx.audio.newSound(Gdx.files.internal("SFX/goodresult.mp3"));
    private final Sound loseSound = Gdx.audio.newSound(Gdx.files.internal("SFX/death-scream.mp3"));
    private final Sound clickButtonSound = Gdx.audio.newSound(Gdx.files.internal("SFX/Pop.wav"));
    private final Sound brightClickSound = Gdx.audio.newSound(Gdx.files.internal("SFX/click_bright.mp3"));
    private final Sound getCoinSound = Gdx.audio.newSound(Gdx.files.internal("SFX/Crystal Reward Tick.wav"));
    private final Sound monsterDeathSound = Gdx.audio.newSound(Gdx.files.internal("SFX/Explosion_Blood_01.wav"));
    private final Sound shootSound = Gdx.audio.newSound(Gdx.files.internal("SFX/single_shot.wav"));
    private final Sound powerUpSound = Gdx.audio.newSound(Gdx.files.internal("SFX/Special & Powerup (8).wav"));
    private final Sound damageSound = Gdx.audio.newSound(Gdx.files.internal("SFX/sfx_sounds_impact1.wav"));
    private final Sound weaponReloadSound = Gdx.audio.newSound(Gdx.files.internal("SFX/Weapon_Shotgun_Reload.wav"));

    public Sound getBrightClickSound() {return brightClickSound;}
    public Sound getLoseSound() {return loseSound;}
    public Sound getWonSound() {    return wonSound;}
    public Sound getGetCoinSound() {return getCoinSound;}
    public Sound getPowerUpSound() {return powerUpSound; }
    public Sound getDamageSound() {return damageSound;}
    public Sound getWeaponReloadSound() {return weaponReloadSound;}
    public Sound getMonsterDeathSound() {return monsterDeathSound;}
    public Sound getClickButtonSound() { return clickButtonSound;}
    public Sound getShootSound() {return shootSound;}


    //SOUND TRACKS
    private Music backgroundMusic;
    private float musicVolume = 3.0f; // default volume
    private final String defaultMusicPath = "Soundtracks/01. Stardew Valley Overture.mp3";

    public void setMusic(String path) {
        if (backgroundMusic != null) backgroundMusic.stop();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(musicVolume);
        backgroundMusic.play();
    }

    public void setVolume(float volume) {
        this.musicVolume = volume;
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(volume);
        }
    }

    public float getVolume() {
        return musicVolume;
    }
    public void stopMusic() {
        if (backgroundMusic != null) backgroundMusic.stop();
    }
    public void playMusic() {
        if (backgroundMusic != null) backgroundMusic.play();
    }
    public String getDefaultMusicPath() {
        return defaultMusicPath;
    }
    public String getMusic1Path() {
        return "Soundtracks/02. Cloud Country.mp3";
    }
    public String getMusic2Path() {
        return "Soundtracks/03. Grandpa's Theme.mp3";
    }
    public String getMusic3Path() {
        return "Soundtracks/04. Settling In.mp3";
    }
    public String getMusic4Path() {
        return "Soundtracks/08. Pelican Town.mp3";
    }


    Texture spriteSheet = new Texture("sprites/Abigail.png");
    TextureRegion[][] frames = TextureRegion.split(spriteSheet, 16, 32);  // Width and height of each frame
    public Animation<TextureRegion> menuWalkDown = new Animation<>(0.4f, frames[2]);



}


