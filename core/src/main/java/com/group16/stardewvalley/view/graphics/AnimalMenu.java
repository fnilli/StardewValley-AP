package com.group16.stardewvalley.view.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.group16.stardewvalley.model.animal.Animal;
import com.group16.stardewvalley.controller.AnimalController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

public class AnimalMenu extends Window {

    private final AnimalController controller = new AnimalController();
    private final Label feedbackLabel;

    public AnimalMenu(Skin skin, final Animal animal) {
        super(animal.getName(), skin);
        pad(20);
        setModal(true);
        setMovable(true);
        setResizable(false);

        // Make it wider
        this.setWidth(400);

        // Info labels
        Label typeLabel = new Label("Type: " + animal.getAnimalType().getName(), skin);
        Label petLabel = new Label("Pet today: " + (animal.isPet() ? "Yes" : "No"), skin);
        Label outLabel = new Label("Is out now: " + (animal.isOut() ? "Yes" : "No"), skin);
        Label friendshipLabel = new Label("Friendship: " + animal.getFriendship(), skin);
        Label fedLabel = new Label("Fed today: " + (animal.isFeed() ? "Yes" : "No"), skin);
        feedbackLabel = new Label("", skin);
        feedbackLabel.setWrap(true); // allow multi-line

        // Buttons
        TextButton feedButton = new TextButton("Feed", skin);
        feedButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.feedHay(animal.getName());
                feedbackLabel.setText(result.toString());
                if (result.isSuccessful()) {
                    animal.setIsFeed(true);
                    fedLabel.setText("Fed today: Yes");
                    animal.triggerFeedingEffect();;
                }
            }
        });

        TextButton petButton = new TextButton("Pet", skin);
        petButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.pet(animal.getName());
                feedbackLabel.setText(result.toString());
                if (result.isSuccessful()) {
                    animal.setIsPet(true);
                    petLabel.setText("Pet today: " + (animal.isPet() ? "Yes" : "No"));
                    friendshipLabel.setText("Friendship: " + animal.getFriendship());
                    animal.triggerPettingEffect();
                }
            }
        });

        TextButton takeOutButton = new TextButton("Take Out", skin);
        takeOutButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (animal.isOut()) {
                    // Bring animal back inside (5 tiles above current)
                    animal.setPixelPosition(
                        animal.getPixelX(),
                        animal.getPixelY() + 5 * GameScreen.TILE_SIZE
                    );
                    animal.setYardBounds(
                        animal.getPixelX() - GameScreen.TILE_SIZE,  // minX
                        animal.getPixelY() - GameScreen.TILE_SIZE,  // minY
                        animal.getPixelX() + GameScreen.TILE_SIZE,  // maxX
                        animal.getPixelY() + GameScreen.TILE_SIZE   // maxY
                    );
                    animal.setIsOut(false);
                } else {
                    // Take animal outside (5 tiles below current)
                    animal.setPixelPosition(
                        animal.getPixelX(),
                        animal.getPixelY() - 5 * GameScreen.TILE_SIZE
                    );
                    animal.setYardBounds(
                        animal.getPixelX() - GameScreen.TILE_SIZE,  // minX
                        animal.getPixelY() - GameScreen.TILE_SIZE,  // minY
                        animal.getPixelX() + GameScreen.TILE_SIZE,  // maxX
                        animal.getPixelY() + GameScreen.TILE_SIZE   // maxY
                    );
                    animal.setIsOut(true);
                }

                animal.triggerShepherdEffect(); // sparkle/shepherd effect
            }
        });


        TextButton sellButton = new TextButton("Sell", skin);
        sellButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.sellAnimal(animal.getName());
                feedbackLabel.setText(result.toString());
                if (result.isSuccessful()) {
                    remove();
                }
            }
        });
        System.out.println();
        TextButton getProductsButton = new TextButton("Products", skin);
        getProductsButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                Result result = controller.animalProduces();
                feedbackLabel.setText(result.toString());

                if(result.isSuccessful()) {
                    animal.triggerProductEffect();
                }
            }
        });

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                remove();
            }
        });

        // Layout
        defaults().pad(10).fillX();

        // Labels on separate rows
        row();
        add(typeLabel).colspan(3).row();
        add(outLabel).colspan(3).row();
        add(fedLabel).colspan(3).row();
        add(petLabel).colspan(3).row();
        add(friendshipLabel).colspan(3).row();

        // Buttons in 3 columns, 3 rows
        add(feedButton).width(350).expandX();
        add(petButton).width(350).expandX();
        add(takeOutButton).width(350).expandX().row();

        add(sellButton).width(350).expandX();
        add(getProductsButton).width(350).expandX();
        add(closeButton).width(350).expandX().row();

        // Feedback label at the bottom
// Feedback label at the bottom
        feedbackLabel.setWrap(true);
        add(feedbackLabel)
            .colspan(3)
            .width(350)   // controls wrapping
            .padTop(10)
            .expandX()
            .fillX()
            .height(60)   // ensures enough vertical room
            .row();
        row();

        pack();
    }




}
