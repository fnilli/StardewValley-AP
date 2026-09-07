package com.group16.stardewvalley.model.map;

import com.badlogic.gdx.graphics.Texture;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

import java.util.HashMap;
import java.util.Map;

public class TileTextureManager {
    private static TileTextureManager tileTextureManager;


    private Map<TileType, Texture> tileTextures = new HashMap<>();

    public TileTextureManager() {
        String ground_tile = "Flooring/Flooring_14.png";
        tileTextures.put(TileType.Ground, new Texture(ground_tile));
        tileTextures.put(TileType.Cottage, new Texture(ground_tile));
        tileTextures.put(TileType.CottageStartPos, new Texture(ground_tile));
        String lake_tile = "Flooring/Flooring_26.png";
        tileTextures.put(TileType.Lake, new Texture(lake_tile));
        String tree_tile = "Trees/Pine_Stage_4.png";
        tileTextures.put(TileType.Tree, new Texture(tree_tile));
        String plowed_tile = "Decor/Gravel_Path.png";
        tileTextures.put(TileType.Plowed, new Texture(plowed_tile));
        String quarry_tile = "Flooring/Flooring_11.png";
        tileTextures.put(TileType.Quarry, new Texture(quarry_tile));
        tileTextures.put(TileType.Forage, new Texture("Foraging/Grape.png"));
        tileTextures.put(TileType.NPCHouse, new Texture(ground_tile));
        tileTextures.put(TileType.Shop, new Texture(ground_tile));
        tileTextures.put(TileType.Stone, new Texture("Crafting/Stone.png"));
        tileTextures.put(TileType.MineralForage, new Texture("Crafting/Diamond.png"));
        tileTextures.put(TileType.GreenHouse, new Texture("Flooring/Flooring_29.png"));
        tileTextures.put(TileType.Grass, new Texture("Flooring/Flooring_28.png"));
        tileTextures.put(TileType.StonePath, new Texture("Flooring/Flooring_52.png"));
        tileTextures.put(TileType.Fence, new Texture("Fence/Gate.png"));
    }

    public static TileTextureManager getTileTextureManager() {
        if (tileTextureManager == null) tileTextureManager = new TileTextureManager();
        return tileTextureManager;
    }

    public Texture getTexture(TileType type) {
        return tileTextures.get(type);
    }

    public void dispose() {
        for (Texture texture : tileTextures.values()) {
            texture.dispose();
        }
    }
}
