package com.group16.stardewvalley.model.map;

public enum FarmType {
    small(45, 70, "small"),
    big(60, 75, "big");

    private int width;
    private int height;
    private TileType[][] tiles;

    FarmType(int height, int width, String name) {
        this.height = height;
        this.width = width;
        this.tiles = makeFarm(name);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public TileType[][] getTiles() {
        return tiles;
    }

    public void setTiles(TileType[][] tiles) {
        this.tiles = tiles;
    }

    private TileType[][] makeFarm(String type) {
        tiles = new TileType[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = TileType.Ground;
            }
        }
        if (type.equals("small")) {
            for (int i = 1; i < 6; i++) {
                for (int j = 1; j < 7; j++) {
                    tiles[i][j] = TileType.GreenHouse;
                }
            }
            for (int i = 1; i < 11; i++) {
                for (int j = 33; j < 44; j++) {
                    tiles[i][j] = TileType.Cottage;
                    if (i == 5 && j == 38) tiles[i][j] = TileType.CottageStartPos;
                }
            }
            for (int i = 34; i < 43; i++) {
                for (int j = 1; j < 10; j++) {
                    tiles[i][j] = TileType.Quarry;
                }
            }
            for (int i = 25; i < 35; i++) {
                for (int j = 45; j < 62; j++) {
                    tiles[i][j] = TileType.Lake;
                }
            }
        }
        if (type.equals("big")) {
            for (int i = height - 7; i < height - 2; i++) {
                for (int j = 3; j < 9; j++) {
                    tiles[i][j] = TileType.GreenHouse;
                }
            }
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    tiles[i][j] = TileType.Cottage;
                    if (i == 5 && j == 5) tiles[i][j] = TileType.CottageStartPos;
                }
            }
            for (int i = 1; i < 7; i++) {
                for (int j = width - 7; j < width - 1; j++) {
                    tiles[i][j] = TileType.Quarry;
                }
            }
            for (int i = height - 10; i < height - 1; i++) {
                for (int j = width - 13; j < width - 1; j++) {
                    tiles[i][j] = TileType.Lake;
                }
            }
            for (int i = (height - 5)/2 ; i < (height - 5)/2 + 5; i++) {
                for (int j = 5; j < 10; j++) {
                    tiles[i][j] = TileType.Lake;
                }
            }
        }

        return tiles;
    }
}
