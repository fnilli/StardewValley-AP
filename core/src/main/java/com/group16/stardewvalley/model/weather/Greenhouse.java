package com.group16.stardewvalley.model.weather;

import com.group16.stardewvalley.model.agriculture.Crop;
import com.group16.stardewvalley.model.agriculture.Tree;
import com.group16.stardewvalley.model.app.Game;
import com.group16.stardewvalley.model.map.Farm;

import java.util.ArrayList;

public class Greenhouse {

    private final int requiredCoin = 1000;
    private final int requiredWood = 500;
    private final int length = 6;
    private final int width = 5;
    private ArrayList<Crop> greenHouseCrops;
    private ArrayList<Tree> greenHouseTrees;
    private Farm farm;
    // این دو عدد بدون در نظر گرفتن دیوار وارد شده اند

    public Greenhouse(Farm farm) {
        this.farm = farm;
        this.greenHouseCrops = new ArrayList<>();
        this.greenHouseTrees = new ArrayList<>();
    }
    public void addGreenHouseCrop(Crop crop) {
        greenHouseCrops.add(crop);
    }

    public void addGreenHouseTree(Tree tree) {
        greenHouseTrees.add(tree);
    }

    public ArrayList<Tree> getGreenHouseTrees() {
        return greenHouseTrees;
    }

    public ArrayList<Crop> getGreenHouseCrops() {
        return greenHouseCrops;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }


    // رشد بدون محدودیت + عدم نیاز به مترسک + مخزن اب داخلی
    // برای کاشت هم عول پیکر نباید باشند محصولات
}
