package com.group16.stardewvalley.model.map;

import com.badlogic.gdx.graphics.Texture;

public enum PlaceType {
    Blacksmith(10, 20, new Pos(230, 110), "shop"),
    CarpentersShop(20,40, new Pos(124, 40), "shop"),
    JojaMart(15, 30, new Pos(250, 90), "shop"),
    FishShop(10, 20, new Pos(170, 170), "shop"),
    MarniesRanch(10, 20, new Pos(25, 90), "shop"),
    PierresGeneralStore(10, 15, new Pos(170, 40), "shop"),
    TheStardropSaloon(10, 20, new Pos(120, 145), "shop"),

    Sebastian(10, 10, new Pos(120, 87), "npc"),
    Abigail(10, 10, new Pos(140, 90), "npc"),
    Harvey(10, 10, new Pos(120, 107), "npc"),
    Leah(10, 10, new Pos(160, 87), "npc"),
    Robin(10, 10, new Pos(160, 107), "npc");

    private final int width;
    private final int height;
    private final Pos startPosition;
    private TileType[][] tiles;

    PlaceType(int height, int width, Pos startPosition, String type) {
        this.height = height;
        this.width = width;
        this.startPosition = startPosition;
        this.tiles = makePlace(type);
    }
    private TileType[][] makePlace(String type) {
        TileType[][] tiles = new TileType[height][width];
        switch (type) {
            case "shop":
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        tiles[y][x] = TileType.Shop;
                    }
                }
                break;
            case "npc" :
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        tiles[y][x] = TileType.NPCHouse;
                    }
                }
                break;
        }
        return tiles;
    }

    public int getWidth() {
        return width;
    }

    public Pos getStartPosition() {
        return startPosition;
    }

    public int getHeight() {
        return height;
    }

    public TileType[][] getTiles() {
        return tiles;
    }

    public void setTiles(TileType[][] tiles) {
        this.tiles = tiles;
    }


}
