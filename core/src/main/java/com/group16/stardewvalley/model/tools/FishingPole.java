package com.group16.stardewvalley.model.tools;

import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.agriculture.CropType;
import com.group16.stardewvalley.model.animal.Fish;
import com.group16.stardewvalley.model.animal.FishType;
import com.group16.stardewvalley.model.animal.ProductQuality;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.user.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.group16.stardewvalley.model.menu.GameMenuCommands.Season;

public class FishingPole extends Gadget{
    public FishingPole(String name, int price, String material) {
        super(name, price);
        this.material = material;
    }

    public int getPrice() {
        return ToolDataManager.getToolPrice("Fishing pole", this.material);
    }

    public int getConsumptionEnergy() {
        return ToolDataManager.getEnergyConsumption("Fishing pole", this.material);
    }

    public Result use(Tile targetTile, Game game) {
        Player player = game.getCurrentPlayer();
        int requiredEnergy = this.getConsumptionEnergy();
        if (player.getFishingAbilityLevel() == 4) {
            requiredEnergy--;
        }

        // خطای انرژی
        if (player.getEnergy() < requiredEnergy) {
            player.decreaseEnergy(requiredEnergy);
            player.faint();
            return new Result(false, "Have you not eaten bread today?");
        }

        // هیچکار نتواند بکند : روی زمین خالی مثلا چوب ماهیگیری انداخته

        if (targetTile == null) {
            player.decreaseEnergy(requiredEnergy - 1);
            return new Result(false, "There is no lake here!");
        }

        player.decreaseEnergy(requiredEnergy);
        List<FishType> fishes = new ArrayList<>(Arrays.stream(FishType.values())
                .filter(fish -> fish.getSeason().equals(game.getTimeDate().getCurrentSeason()))
                .toList());
        if (fishes.contains(FishType.Legend) && game.getCurrentPlayer().getFishingAbilityLevel() < 4) {
            fishes.remove(FishType.Legend);
        }
        int fishNumber = Math.min(calculateFishNumbers(), 6);
        int fishQuality = calculateFishQuality();
        Random random = new Random();
        ProductQuality quality;
        if (fishQuality >= 0.5 && fishQuality < 0.7) {
            quality = ProductQuality.SILVER;
        } else if (fishQuality >= 0.7 && fishQuality < 0.9) {
            quality = ProductQuality.IRIDIUM;
        } else if (fishQuality >= 0.9) {
            quality = ProductQuality.GOLD;
        } else {
            quality = ProductQuality.NORMAL;
        }
        ArrayList<FishType> fishesToUse = new ArrayList<>();

        int index;
        for (int i = 0; i < fishNumber; i++) {
            index = random.nextInt(fishes.size());
            fishesToUse.add(fishes.get(index));
//            game.getCurrentPlayer().getInventory().addItem(new Fish(fishes.get(index), ,quality), 1);
        }
        game.getCurrentPlayer().addFishingAbilityScore(5);
        StringBuilder result = new StringBuilder();
        result.append("you earned ").append(fishNumber).append(" fishes!\n");
        int number = 1;
        for (FishType fish : fishesToUse) {
            result.append(number).append("_ ").append(fish.toString()).append("\n");
            number++;
        }
        return new Result(true, result.toString());


    }

    private int calculateFishQuality() {
        float pole = switch (material) {
            case "training" -> 0.1F;
            case "bamboo" -> 0.5F;
            case "fiberglass" -> 0.9F;
            case "iridium" -> 1.2F;
            default -> 1;
        };
        int skill = App.getActiveGame().getCurrentPlayer().getFishingAbilityLevel();
        int R = new Random().nextInt(2);
        float M = switch (App.getActiveGame().getWeatherCondition()) {
            case SUNNY -> 1.5F;
            case RAINY -> 1.2F;
            case STORM -> 0.5F;
            default -> 1;
        };
        return (int) (R * (skill + 2) * pole) / (7 - skill);

    }

    private int calculateFishNumbers() {
        int skill = App.getActiveGame().getCurrentPlayer().getFishingAbilityLevel();
        int R = new Random().nextInt(2);
        float M = switch (App.getActiveGame().getWeatherCondition()) {
            case SUNNY -> 1.5F;
            case RAINY -> 1.2F;
            case STORM -> 0.5F;
            default -> 1;
        };
        return (int) (R * M * (skill + 2));
    }
}
