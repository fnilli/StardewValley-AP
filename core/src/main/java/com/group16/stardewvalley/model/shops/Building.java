package com.group16.stardewvalley.model.shops;

import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.items.Item;
import com.group16.stardewvalley.model.map.Pos;

import java.util.ArrayList;

public class Building extends Item {
    public Building(String name, int price) {
        super(name, price);
    }

    private BuildingType buildingType;
    private int capacity;
    private Pos startPosition;
    private ArrayList<Animal> buildingAnimals = new ArrayList<>();


    public Building(String name, int price, BuildingType buildingType, Pos startPosition) {
        super(name, price);
        this.buildingType = buildingType;
        this.startPosition = startPosition;
    }

    public Pos getStartPosition() {
        return startPosition;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Animal> getBuildingAnimals() {
        return buildingAnimals;
    }

    public void setBuildingAnimals(ArrayList<Animal> buildingAnimals) {
        this.buildingAnimals = buildingAnimals;
    }

    public void addAnimal(Animal animal) {
        buildingAnimals.add(animal);
    }

    public void increaseCapacity() {
        this.capacity += 1;
    }


    public boolean isNearBuilding(Pos pos) {
        int length = buildingType.getLength();
        int width = buildingType.getWidth();

        // Building boundaries
        int buildingMinX = startPosition.getX();
        int buildingMaxX = startPosition.getX() + length - 1;
        int buildingMinY = startPosition.getY();
        int buildingMaxY = startPosition.getY() + width - 1;

        // Check if position is adjacent (left/right/top/bottom)
        boolean isLeft = pos.getX() == buildingMinX - 1 && pos.getY() >= buildingMinY && pos.getY() <= buildingMaxY;
        boolean isRight = pos.getX() == buildingMaxX + 1 && pos.getY() >= buildingMinY && pos.getY() <= buildingMaxY;
        boolean isTop = pos.getY() == buildingMaxY + 1 && pos.getX() >= buildingMinX && pos.getX() <= buildingMaxX;
        boolean isBottom = pos.getY() == buildingMinY - 1 && pos.getX() >= buildingMinX && pos.getX() <= buildingMaxX;

        return isLeft || isRight || isTop || isBottom;
    }

}
