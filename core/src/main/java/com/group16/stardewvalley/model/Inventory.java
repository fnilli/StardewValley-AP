package com.group16.stardewvalley.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.items.Flower;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.agriculture.*;
import com.group16.stardewvalley.model.items.Wood;
import com.group16.stardewvalley.model.tools.*;
import com.group16.stardewvalley.model.crafting.CraftingRecipes;
import com.group16.stardewvalley.model.user.BackPackType;
import com.group16.stardewvalley.model.food.Food;
import com.group16.stardewvalley.model.food.FoodIngredient;
import com.group16.stardewvalley.model.food.Ingredient;
import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Inventory {
    private Map<Gadget, Integer> tools;
    private Map<Item, Integer> items;
    private int capacity;
    private ArrayList<CraftingRecipes> craftingRecipes;
    private Map<Crop, Integer> crops;
    private BackPackType backPackType;
    private DragAndDrop dragAndDrop;
    Table mainTable = new Table();
    Stage stage;

    public Inventory() {
        this.tools = new HashMap<>();
        this.items = new HashMap<>();
        this.crops = new HashMap<>();
        this.backPackType = BackPackType.Base_Pack;
        this.craftingRecipes = new ArrayList<>(
                List.of(CraftingRecipes.CherryBomb, CraftingRecipes.Sprinkler, CraftingRecipes.CharcoalKiln, CraftingRecipes.Furnace,
                        CraftingRecipes.Scarecrow, CraftingRecipes.BeeHouse, CraftingRecipes.MayonnaiseMachine));
        Axe newAse = new Axe("base_axe", 0, "base");
        FishingPole fishingPole = new FishingPole("Bamboo_Pole", 25, "training");
        Hoe hoe = new Hoe("Hoe", 0, "base");
        MilkPail milkPail = new MilkPail("Milkpail", 0);
        Scythe scythe = new Scythe("Scythe", 0, "base");
        Pickaxe pickaxe = new Pickaxe("Pickaxe", 0, "base");
        Shear shear = new Shear("Shears", 1000);
        WateringCan wateringCan = new WateringCan("Watering_Can", 0, "base");
        items.put(wateringCan, 1);
        tools.put(wateringCan, 1);
        tools.put(shear, 1);
        items.put(pickaxe, 1);
        tools.put(shear, 1);
        items.put(scythe, 1);
        tools.put(scythe, 1);
        items.put(hoe, 1);
        tools.put(hoe, 1);
        tools.put(milkPail, 1);
        items.put(fishingPole, 1);
        tools.put(fishingPole, 1);
        tools.put(newAse, 1);
        items.put(newAse, 1);
        Flower flower = new Flower("flower", 0);
        items.put(Seeds.APPLE_SAPLING, 1);
    }

    public void showTools(Stage stage, Skin skin) {
      Table table = new Table();
      table.top().left().pad(10);
      table.setFillParent(true);
      for (Gadget gadget : tools.keySet()) {
          Image icon = new Image(GameAssetManager.getGameAssetManager().getItemTexture(gadget));
          table.add(icon).pad(10);
      }
      stage.clear();
      stage.addActor(table);
    }

    List<Container<Image>> itemContainers = new ArrayList<>();

    public void showInventory(Stage stage, Skin skin, DragAndDrop dragAndDrop) {
        this.stage = stage;
        this.dragAndDrop = dragAndDrop;

        mainTable.clear();
        mainTable.setFillParent(true);
        mainTable.center();

        // Background panel for nicer look
        Table backgroundTable = new Table(skin);
        backgroundTable.defaults().pad(10);
        //backgroundTable.setBackground("default-round"); // Needs to be in your skin

        // Top icon bar
        Table topIcons = new Table();
        String[] topIconsStr = {
            "Inventory/Friendship.png",
            "Inventory/journal.png",
            "Inventory/Map.png",
            "Inventory/Skills.png",
            "Inventory/Social.png"
        };

        for (String iconPath : topIconsStr) {
            Texture iconTexture = new Texture(Gdx.files.internal(iconPath));
            Image tabIcon = new Image(iconTexture);
            if (iconPath.contains("Friendship.png") || iconPath.contains("journal.png")) {
                tabIcon.setSize(16, 16);
            } else {
                tabIcon.setSize(32, 32);
            }

            topIcons.add(tabIcon).width(32).height(32).pad(10);

            tabIcon.setTouchable(Touchable.enabled);
            DragAndDrop finalDragAndDrop1 = dragAndDrop;
            tabIcon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Tab clicked: " + iconPath);
                    //showInventory(stage, skin, finalDragAndDrop1);
                }
            });
        }

        backgroundTable.add(topIcons).expandX().fillX().row();

        // Initialize DragAndDrop
        if (dragAndDrop == null) {
            dragAndDrop = new DragAndDrop();
        } else {
            dragAndDrop.clear();
        }

        // Items table
        Table itemTable = new Table();
        itemTable.defaults().pad(10);
        itemTable.top().left().pad(10);
        int columnCount = 6;
        int index = 0;

        for (Item item : new ArrayList<>(items.keySet())) {
            if (items.get(item) < 1) {
                continue;
            }
            int quantity = items.get(item);

            Texture texture = GameAssetManager.getGameAssetManager().getItemTexture(item);
            Image icon = new Image(texture);
            icon.setSize(64, 64);
            icon.setTouchable(Touchable.enabled);

            // Create container for icon with default background
            Container<Image> iconContainer = new Container<>(icon);
            iconContainer.size(64, 64);

            TextureRegionDrawable defaultBg = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("Inventory/InventorySlotFrame.png"))
            ));
            TextureRegionDrawable selectedBg = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("Inventory/InventorySlotFrame2.png"))
            ));

            iconContainer.background(defaultBg);

            itemContainers.add(iconContainer);

            icon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for (Container<Image> c : itemContainers) {
                        c.background(defaultBg);
                    }
                    iconContainer.background(selectedBg);

                    if (item instanceof Gadget gadget) {
                        App.getActiveGame().getCurrentPlayer().equip(gadget);
                    } else {
                        App.getActiveGame().getCurrentPlayer().setCurrentThing(item);
                    }
                    System.out.println("Selected: " + item.getName());
                }
            });

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraftia-Regular.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 14;
            BitmapFont smallFont = generator.generateFont(parameter);
            generator.dispose();

            // Label for quantity
            Label.LabelStyle labelStyle = new Label.LabelStyle();

            labelStyle.font = smallFont;
            labelStyle.fontColor = Color.BLACK;
            Label quantityLabel = new Label(String.valueOf(quantity), labelStyle);
            quantityLabel.setAlignment(Align.right);

            // Table to stack icon on top, quantity below
            Table slotTable = new Table();
            slotTable.add(iconContainer).size(64);
            slotTable.add(quantityLabel).padLeft(-10).bottom().right();

            itemTable.add(slotTable).pad(5);

            // Drag setup
            dragAndDrop.addSource(new DragAndDrop.Source(icon) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(item);
                    payload.setDragActor(new Image(texture));
                    return payload;
                }
            });

            index++;
            if (index % columnCount == 0) {
                itemTable.row();
            }
        }


        // ScrollPane for items
        ScrollPane scrollPane = new ScrollPane(itemTable, skin);
        scrollPane.setScrollingDisabled(false, false); // Allow both directions
        scrollPane.setFadeScrollBars(false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setOverscroll(false, false);

        backgroundTable.add(scrollPane)
            .width(Gdx.graphics.getWidth() * 0.4f)
            .height(Gdx.graphics.getHeight() * 0.6f)
            .center()
            .row();

        // Trash icon
        Texture trashTexture = new Texture(Gdx.files.internal("Inventory/Trash.png"));
        Image trashIcon = new Image(trashTexture);
        trashIcon.setSize(48, 48);
        backgroundTable.add(trashIcon).padTop(10).center();

        // Drop target
        DragAndDrop finalDragAndDrop = dragAndDrop;
        dragAndDrop.addTarget(new DragAndDrop.Target(mainTable) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return payload.getObject() instanceof Item;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Item item = (Item) payload.getObject();
                App.getActiveGame().getCurrentPlayer().getInventory().addItem(item, 1);

                if (item instanceof FoodIngredient ingredient) {
                    Map<FoodIngredient, Integer> fridge = App.getActiveGame().getCurrentPlayer().getFarm().getRefrigerator();
                    fridge.put(ingredient, fridge.getOrDefault(ingredient, 1) - 1);
                    if (fridge.get(ingredient) <= 0) {
                        fridge.remove(ingredient);
                    }
                }

                showInventory(stage, skin, finalDragAndDrop);
            }
        });

        mainTable.add(backgroundTable).center();
        stage.addActor(mainTable);
    }

    public void removeInventory() {
        if (mainTable.hasParent()) {  // اگه واقعا توی Stage هست
            mainTable.remove();       // حذف از Stage
        }
        if (dragAndDrop != null) {
            dragAndDrop.clear();      // پاک کردن تمام Target و Source
        }
    }


    public Map<Crop, Integer> getCrops() {
        return crops;
    }

    public void setCrops(Map<Crop, Integer> crops) {
        this.crops = crops;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setTools(Map<Gadget, Integer> tools) {
        this.tools = tools;
    }

    public void setBackPackType(BackPackType backPackType) {
        this.backPackType = backPackType;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    public Result addTool(Gadget gadget, int count) {
        if (isFull()) {
            return new Result(false, "Oops! Your backpack is completely full ");
        }
        String name = gadget.getName();
        tools.put(gadget, tools.getOrDefault(gadget, 0) + count);
        return new Result(true, gadget.getName() + " added to inventory successfully");
    }

    public Result addItem(Item item, int count) {
        if (isFull()) {
            GameScreen.getGameScreen().getController().showErrorPopup("Oops! Your backpack is completely full ");
            return new Result(false, "Oops! Your backpack is completely full ");
        }
        String name = item.getName();
        items.put(item, items.getOrDefault(item, 0) + count);
        return new Result(true, item.getName() + " added to inventory successfully");
    }

    public ArrayList<CraftingRecipes> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void setCraftingRecipes(ArrayList<CraftingRecipes> craftingRecipes) {
        this.craftingRecipes = craftingRecipes;
    }

    public void addCraftingRecipes(CraftingRecipes craftingRecipes) {
        this.craftingRecipes.add(craftingRecipes);
    }


    public BackPackType getBackPackType() {
        return backPackType;
    }
    public void addCrop(Crop crop, int count) {
        crops.put(crop, crops.getOrDefault(crop, 0));
    }


    public Gadget findToolByName(String name) {
        for (Map.Entry<Gadget, Integer> entry : tools.entrySet()) {
            Gadget gadget = entry.getKey();
            if (gadget.getName().equalsIgnoreCase(name)) {
                return gadget;
            }
        }
        return null;
    }

    public Item findItemByName(String name) {
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item item = entry.getKey();
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public int getNumberOfItem(Item item) {
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (entry.getKey().equals(item)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public Map<Gadget, Integer> getTools() {
        return tools;
    }

    public Result removeItem(Item item, int count) {
        if (!items.containsKey(item)) {
            return new Result(false, "You don't have it");
        }

        int currentCount = items.get(item);
        if (currentCount < count) {
            return new Result(false, "Not enough " + item.getName() + " in inventory! (Available: " + currentCount + ")");
        }

        int newCount = currentCount - count;
        if (newCount > 0) {
            items.put(item, newCount);
        } else {
            items.remove(item);
        }

        return new Result(true, count + " " + item.getName() + "(s) removed from inventory");
    }


    public boolean isFull() {
        return getTotalItemsCount() >= backPackType.getCapacity();
    }

    private int getTotalItemsCount() {
        int toolCount = tools.values().stream().mapToInt(Integer::intValue).sum();
        int itemCount = items.values().stream().mapToInt(Integer::intValue).sum();
        return toolCount+ itemCount;
    }

    public boolean isSeedInInventory(SeedType seedType) {
        for (Item item : items.keySet()) {
            if (item instanceof Seed seed) {
                if (seed.getType().equals(seedType) && items.get(item) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public Item getItemByName(String name) {
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Seed findSeedByType(SeedType seedType) {
        for (Item item : items.keySet()) {
            if (item instanceof Seed seed) {
                if (seed.getType().equals(seedType) && items.get(item) > 0) {
                    return seed;
                }
            }
        }
        return null;
    }

    public Food getFood(String foodName) {
        for (Item item : items.keySet()) {
            if (item instanceof Food food) {
                if (food.getName().equalsIgnoreCase(foodName) && items.get(item) > 0) {
                    return food;
                }
            }
        }
        return null;
    }

    public FoodIngredient getFoodIngredient(String ingredientName) {
        for (Item item : items.keySet()) {
            if (item instanceof FoodIngredient ingredient) {
                if (ingredient.getName().equalsIgnoreCase(ingredientName) && items.get(item) > 0) {
                    return ingredient;
                }
            }
        }
        return null;
    }

    public Fertilizer getFertilizer(String name) {
        for (Item item : items.keySet()) {
            if (item instanceof Fertilizer fertilizer) {
                if (fertilizer.getName().equalsIgnoreCase(name) && items.get(item) > 0) {
                    return fertilizer;
                }
            }
        }
        return null;
    }

    public FoodIngredient getFoodIngredient(Ingredient ingredient) {
        for (Item item : items.keySet()) {
            if (item instanceof FoodIngredient foodIngredient) {
                if (foodIngredient.getType().equals(ingredient) && items.get(item) > 0) {
                    return foodIngredient;
                }
            }
        }
        return null;
    }

    public FishingPole getFishingPole(String name) {
        for (Gadget gadget : tools.keySet()) {
            if (gadget instanceof FishingPole fishingPole) {
                if (fishingPole.getName().equalsIgnoreCase(name) && tools.get(gadget) > 0) {
                    return fishingPole;
                }
            }
        }
        return null;
    }

    public Wood findWood(String woodName) {
        for (Item item : items.keySet()) {
            if (item instanceof Wood wood) {
                if (wood.getName().equalsIgnoreCase(woodName) && items.get(item) > 0) {
                    return wood;
                }
            }
        }
        return null;
    }

    public int countWood() {
        int count = 0;
        for (Item item : items.keySet()) {
            if (item instanceof Wood) {
                count++;
            }
        }
        return count;
    }
}
