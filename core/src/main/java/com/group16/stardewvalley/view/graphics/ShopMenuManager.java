package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.shops.MarniesRanch;

public class ShopMenuManager {
    private final Stage stage;
    private final Skin skin;
    private Window currentMenu = null;
    private PlaceType currentShopType = null;

    public ShopMenuManager(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
    }

    public void openMenu(PlaceType shopType) {
        if (currentMenu != null) {
            currentMenu.remove(); // Close existing
        }

        currentMenu = createMenuFor(shopType);
        if (currentMenu != null) {
//            stage.addActor(currentMenu);
            currentShopType = shopType;
            GameScreen.getGameScreen().getStage().addActor(currentMenu);
        }
    }

    public void closeMenu() {
        if (currentMenu != null) {
            currentMenu.remove();
            currentMenu = null;
            currentShopType = null;
        }
    }

    public boolean isMenuOpen() {
        return currentMenu != null;
    }

    public boolean isMenuOpenFor(PlaceType shopType) {
        return currentShopType == shopType;
    }

    private Window createMenuFor(PlaceType shopType) {
        switch (shopType) {
            case CarpentersShop:
                return new CarpenterMenu(skin);
            case MarniesRanch:
                return new MarniesRanchMenu(skin);

            // Add other shops here
            default:
                return null;
        }
    }
}
