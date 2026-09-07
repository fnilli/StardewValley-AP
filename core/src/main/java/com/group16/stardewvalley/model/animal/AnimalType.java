package com.group16.stardewvalley.model.animal;

import com.group16.stardewvalley.model.shops.MarniesRanchAnimals;

import java.util.List;


public enum AnimalType {
    //coop animals:
    CHICKEN("Chicken",800, List.of(AnimalProductTypes.HEN_EGG, AnimalProductTypes.HEN_BIG_EGG), 1, true),
    DUCK("Duck",1_200, List.of(AnimalProductTypes.DUCK_EGG, AnimalProductTypes.DUCK_FEATHER), 2, true),
    RABBIT("Rabbit",8_000, List.of(AnimalProductTypes.RABBIT_LEG, AnimalProductTypes.RABBIT_WOOL), 4, true),
    DINOSAUR("Dinosaur",1_400, List.of(AnimalProductTypes.DINOSAUR_EGG), 7, true),

    COW("Cow",1_500, List.of(AnimalProductTypes.BIG_MILK, AnimalProductTypes.MILK), 1, false),
    GOAT("Goat",4_000, List.of(AnimalProductTypes.BIG_GOAT_MILK, AnimalProductTypes.GOAT_MILK), 2, false),
    SHEEP("Sheep",8_000, List.of(AnimalProductTypes.SHEEP_WOOL), 3, false),
    PIG("Pig",16_000, List.of(AnimalProductTypes.TRUFFLE), 0, false);


    private final String name;
    private boolean hasCoop;
    private final Integer price;
    private final List<AnimalProductTypes> productList;
    private final Integer productPeriod; //in day



    AnimalType(String name,int price, List<AnimalProductTypes> productList, int productPeriod, boolean hasCoop) {
        this.name = name;
        this.price = price;
        this.productList = productList;
        this.productPeriod = productPeriod;
        this.hasCoop = hasCoop;
    }

    public static AnimalType animalTypeFromName(String animal) {
        for (AnimalType type : AnimalType.values()) {
            if (type.name.equalsIgnoreCase(animal)) {
                return type;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public boolean isHasCoop() {
        return hasCoop;
    }

    public Integer getPrice() {
        return price;
    }

    public List<AnimalProductTypes> getProductList() {
        return productList;
    }

    public Integer getProductPeriod() {
        return productPeriod;
    }
}
