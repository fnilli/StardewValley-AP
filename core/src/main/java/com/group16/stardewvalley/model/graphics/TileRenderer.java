package com.group16.stardewvalley.model.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.model.crafting.CraftItem;
import com.group16.stardewvalley.model.map.Tile;
import com.group16.stardewvalley.model.map.TileTextureManager;
import com.group16.stardewvalley.model.map.TileType;
import com.group16.stardewvalley.model.shops.Building;
import com.group16.stardewvalley.view.graphics.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class TileRenderer {
    private final GameAssetManager textureManager;
    private static TileRenderer tileRenderer;
    float stateTime = 0;
    private Texture animalPetEffectTexture = new Texture("Heart/Secret_Heart.png");;
    private Texture animalFeedEffectTexture = new Texture("Resource/Fiber.png");;
    private Texture animalShepherdEffectTexture = new Texture("Animals/Qi_Gem.png");
    private Texture animalProductEffectTexture = new Texture("Animal_product/Wool.png");

    public TileRenderer() {
        this.textureManager = GameAssetManager.getGameAssetManager();
    }

    public static TileRenderer getTileRenderer() {
        if (tileRenderer == null) {
            tileRenderer = new TileRenderer();
        }
        return tileRenderer;
    }




    public void renderTile(SpriteBatch batch, Tile tile, int x, int y) {
        int TILE_SIZE = GameScreen.TILE_SIZE;
        int drawX = x * TILE_SIZE;
        int drawY = y * TILE_SIZE;


        if (tile.getType().equals(TileType.CottageStartPos)) {
            Texture house = GameAssetManager.getGameAssetManager().getHouseTexture();
            int realWidth = TILE_SIZE * 10;
            int realHeight = TILE_SIZE * 10;

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(house, offsetX, offsetY, realWidth, realHeight);
        }

        if (tile.isPlowed()) {
            Texture plowedTile = TileTextureManager.getTileTextureManager().getTexture(TileType.Plowed);
            batch.draw(plowedTile, drawX, drawY, TILE_SIZE, TILE_SIZE);
        }

        if (tile.getCrop() != null) {
            if (tile.getCrop().isColossal()) {
                if (tile.getCrop().isMature()) {
                    Texture cropTexture = textureManager.getGiantCropTexture(tile.getCrop());
                    batch.draw(cropTexture, drawX, drawY, TILE_SIZE * 2 , TILE_SIZE * 2);
                } else {
                    TextureRegion cropTexture = textureManager.getCropRegion(tile.getCrop());
                    drawPlant(batch, TILE_SIZE * 2, drawX, drawY, cropTexture);
                }
            } else if (!tile.getCrop().isPartOfColossal()) {
                TextureRegion cropTexture = textureManager.getCropRegion(tile.getCrop());
                drawPlant(batch, TILE_SIZE, drawX, drawY, cropTexture);
            }
        }

        if (tile.getItem() instanceof Building building) {
            if (tile.isBuildingOrigin()) {
                // Draw building texture
                Texture buildingTexture = textureManager.getItemTexture(building);
                float scale = 0.25f;
                int drawWidth = (int) (buildingTexture.getWidth() * scale);
                int drawHeight = (int) (buildingTexture.getHeight() * scale);
                batch.draw(buildingTexture, drawX, drawY, drawWidth, drawHeight);

                // Yard bottom-left corner (aligned to building)
                int yardTileWidth = 3;
                int yardTileHeight = 3;
                int yardX = drawX;
                int yardY = drawY - yardTileHeight * TILE_SIZE; // below building

                // --- Draw grass texture under the yard ---
                Texture grassTexture = GameAssetManager.getGameAssetManager().getTexture("Animals/Fireflies.png");
                for (int row = 0; row < yardTileHeight; row++) {
                    for (int col = 0; col < yardTileWidth; col++) {
                        int grassX = yardX + col * TILE_SIZE;
                        int grassY = yardY + row * TILE_SIZE;
                        batch.draw(grassTexture, grassX, grassY, TILE_SIZE, TILE_SIZE);
                    }
                }

                // Draw animals on top
                ArrayList<Animal> animals = building.getBuildingAnimals();
                if (!animals.isEmpty()) {
                    int animalsPerRow = yardTileWidth;
                    int animalSize = TILE_SIZE;

                    for (int i = 0; i < animals.size(); i++) {
                        Animal animal = animals.get(i);
                        Texture animalTexture = textureManager.getAnimalTexture(animal);

                        int row = i / animalsPerRow;
                        int col = i % animalsPerRow;

                        int animalX = yardX + col * TILE_SIZE;
                        int animalY = yardY + row * TILE_SIZE;

                        batch.draw(animalTexture, animal.getPixelX(), animal.getPixelY(), animalSize, animalSize);

                        // Draw any active effects (pet, feed, shepherd)
                        drawAnimalEffects(batch, animal, animalSize);
                    }
                }
            }
        }


        if(tile.getItem() instanceof CraftItem craftItem){
            // Draw crafting texture
            Texture craftingTexture = textureManager.getCraftingTexture(craftItem.getRecipe());
            float scale = 0.3f;
            int drawWidth = (int) (craftingTexture.getWidth() * scale);
            int drawHeight = (int) (craftingTexture.getHeight() * scale);
            batch.draw(craftingTexture, drawX, drawY, drawWidth, drawHeight);

        }

        else if (tile.getItem() != null) {
            Texture texture = textureManager.getItemTexture(tile.getItem());
            int realWidth = Math.min(TILE_SIZE, texture.getWidth());
            int realHeight = Math.min(TILE_SIZE, texture.getHeight());
            batch.draw(texture, drawX, drawY, realWidth, realHeight);
        }



        if (tile.getTree() != null) {
            TextureRegion treeTexture = textureManager.getTreeRegion(tile.getTree());
            drawPlant(batch, TILE_SIZE, drawX, drawY, treeTexture);
        }

        if (tile.isBurned()) {
            Texture fireOverlay = textureManager.getBurnTexture();
            int realWidth = fireOverlay.getWidth();
            int realHeight = fireOverlay.getHeight();

            int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
            int offsetY = drawY;
            batch.draw(fireOverlay, offsetX, offsetY, realWidth, realHeight);
        }


    }

    private void drawPlant(SpriteBatch batch, int TILE_SIZE, int drawX, int drawY, TextureRegion cropTexture) {
        int realWidth = cropTexture.getRegionWidth() / 3;
        int realHeight = cropTexture.getRegionHeight() / 3;

        int offsetX = drawX + (TILE_SIZE - realWidth) / 2;
        int offsetY = drawY;
        batch.draw(cropTexture, offsetX, offsetY, realWidth, realHeight);
    }

    private void drawAnimalEffects(SpriteBatch batch, Animal animal, int animalSize) {
        if (animal.isPettingEffectActive()) {
            float alpha = animal.getPetEffectAlpha();
            batch.setColor(1,1,1,alpha);
            batch.draw(animalPetEffectTexture, animal.getPixelX()-8, animal.getPixelY()-8, animalSize+16, animalSize+16);
            batch.setColor(1,1,1,1);
        }
        if (animal.isFeedingEffectActive()) {
            float alpha = animal.getFeedEffectAlpha();
            batch.setColor(1,1,1,alpha);
            batch.draw(animalFeedEffectTexture, animal.getPixelX()-8, animal.getPixelY()-8, animalSize+16, animalSize+16);
            batch.setColor(1,1,1,1);
        }
        if (animal.isShepherdEffectActive()) {
            float alpha = animal.getShepherdEffectAlpha();
            batch.setColor(1,1,1,alpha);
            batch.draw(animalShepherdEffectTexture, animal.getPixelX()-8, animal.getPixelY()-8, animalSize+16, animalSize+16);
            batch.setColor(1,1,1,1);
        }
        if (animal.isProductEffectActive()){

            switch (animal.getAnimalType()){
                case CHICKEN -> animalProductEffectTexture = new Texture("Animal_product/Egg.png");
                case COW -> animalProductEffectTexture = new Texture("Animal_product/Milk.png");
                case DUCK -> animalProductEffectTexture = new Texture("Animal_product/Duck_Egg.png");
                case DINOSAUR -> animalProductEffectTexture = new Texture("Animal_product/Dinosaur_Egg.png");
                case PIG -> animalProductEffectTexture = new Texture("Animal_product/Truffle.png");
                case GOAT -> animalProductEffectTexture = new Texture("Animal_product/Goat_Milk.png");
                case SHEEP -> animalProductEffectTexture = new Texture("Animal_product/Wool.png");
                case RABBIT -> animalProductEffectTexture = new Texture("Animal_product/Rabbit_Foot.png");
            }

            float alpha = animal.getProductEffectAlpha();
            batch.setColor(1,1,1,alpha);
            batch.draw(animalProductEffectTexture, animal.getPixelX()-8, animal.getPixelY()-8, animalSize+16, animalSize+16);
            batch.setColor(1,1,1,1);
        }
    }

}
