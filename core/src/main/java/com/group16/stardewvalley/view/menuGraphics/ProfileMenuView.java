package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.controller.menu.ProfileMenuController;
import com.group16.stardewvalley.controller.menu.StartMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.AnimatedSpriteActor;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.graphics.Heros;

public class ProfileMenuView implements Screen {
    private final ProfileMenuController controller;
    private Stage stage;
    private final Skin skin;

    public ProfileMenuView(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Label resultLabel = new Label("", skin);
        resultLabel.setColor(Color.WHITE);
        Label.LabelStyle whiteStyle = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
        whiteStyle.fontColor = Color.WHITE;

        // Background
        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        // Logo
        Texture logoTexture = new Texture(Gdx.files.internal("Background/Profile-Menu.png"));
        Image logoImage = new Image(logoTexture);
        logoImage.setSize(logoTexture.getWidth() * 0.3f, logoTexture.getHeight() * 0.3f);

        // Avatar Buttons
        AnimatedSpriteActor[] heroes = {
            createHero(Heros.ABIGAIL), createHero(Heros.ALEX),
            createHero(Heros.KENT), createHero(Heros.LEO), createHero(Heros.MARNIE)
        };
        for (AnimatedSpriteActor hero : heroes) {
            hero.setSize(80, 200);
        }

        heroes[0].addListener(new AvatarClickListener(Heros.ABIGAIL, resultLabel));
        heroes[1].addListener(new AvatarClickListener(Heros.ALEX, resultLabel));
        heroes[2].addListener(new AvatarClickListener(Heros.KENT, resultLabel));
        heroes[3].addListener(new AvatarClickListener(Heros.LEO, resultLabel));
        heroes[4].addListener(new AvatarClickListener(Heros.MARNIE, resultLabel));

        Table avatarRow = new Table();
        for (AnimatedSpriteActor hero : heroes) {
            avatarRow.add(hero).pad(10);
        }

        // Change Buttons
        TextButton changeUsername = new TextButton("Change Username", skin);
        TextButton changePassword = new TextButton("Change Password", skin);
        TextButton changeName = new TextButton("Change Name", skin);
        TextButton changeEmail = new TextButton("Change Email", skin);

        changeUsername.addListener(new FieldDialogListener("Change Username", "Enter new username", controller::changeUsername, resultLabel));
        changeName.addListener(new FieldDialogListener("Change Name", "Enter new name", controller::changeNickName, resultLabel));
        changeEmail.addListener(new FieldDialogListener("Change Email", "Enter new Email", controller::changeEmail, resultLabel));

        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Dialog dialog = new Dialog("Change Password", skin);

                TextField oldPass = new TextField("", skin);
                oldPass.setMessageText("Enter old password");
                oldPass.setPasswordMode(true);
                oldPass.setPasswordCharacter('*');

                TextField newPass = new TextField("", skin);
                newPass.setMessageText("Enter new password");
                newPass.setPasswordMode(true);
                newPass.setPasswordCharacter('*');

                Label feedback = new Label("", skin);
                feedback.setWrap(true);

                TextButton submit = new TextButton("Submit", skin);
                TextButton cancel = new TextButton("Cancel", skin);

                submit.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                        Result result = controller.changePassword(oldPass.getText().trim(), newPass.getText().trim());
                        feedback.setText(result.toString());

                        if (result.isSuccessful()) {
                            resultLabel.setText(result.toString());
                            dialog.hide();
                            refresh();
                        }

                    }
                });

                cancel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                        dialog.hide();
                    }
                });

                dialog.getContentTable().add(oldPass).width(400).padBottom(10).row();
                dialog.getContentTable().add(newPass).width(400).padBottom(10).row();
                dialog.getContentTable().add(feedback).width(400).height(80).padBottom(20).row();
                dialog.getButtonTable().add(submit).width(250).pad(5);
                dialog.getButtonTable().add(cancel).width(250).pad(5);

                dialog.show(stage);
                dialog.setSize(600, 400);
            }
        });

        // Back Button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        // Change button grid
        Table buttonGrid = new Table();
        buttonGrid.add(changeUsername).width(530).pad(10);
        buttonGrid.row();
        buttonGrid.add(changePassword).width(530).pad(10);
        buttonGrid.row();
        buttonGrid.add(changeName).width(530).pad(10);
        buttonGrid.row();
        buttonGrid.add(changeEmail).width(530).pad(10);

        // === Root Table ===
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.top().pad(30);
        stage.addActor(rootTable);

        // === Title Row ===
        Table titleTable = new Table();
        titleTable.center();
        titleTable.add(logoImage).center().padBottom(20).row();
        rootTable.add(titleTable).colspan(2).center().padBottom(40).row();

        // === LEFT COLUMN (User Info) ===
        Table leftColumn = new Table();
        leftColumn.top().padRight(100);

        leftColumn.add(new AnimatedSpriteActor(new Texture(App.getLoggedInUser().getHero().getTexturePath()),
                16, 32, 0, 0.3f))
            .size(160, 400).padBottom(20).row();


        leftColumn.add(new Label("Username: " + App.getLoggedInUser().getUsername(), skin)).left().padBottom(30).row();
        leftColumn.add(new Label("Nickname: " + App.getLoggedInUser().getNickName(), skin)).left().padBottom(30).row();
        leftColumn.add(new Label("Email: " + App.getLoggedInUser().getEmail(), skin)).left().padBottom(30).row();
        leftColumn.add(new Label("Game Played: " + App.getLoggedInUser().getGamePlayed(), skin)).left().padBottom(30).row();
        leftColumn.add(new Label("Most Money: " + "0.0", skin)).left().padBottom(30).row();


        // === RIGHT COLUMN (Avatars & Buttons) ===
        Table rightColumn = new Table();
        rightColumn.top();
        rightColumn.add(avatarRow).width(280).padBottom(15).row();
        rightColumn.add(buttonGrid).padBottom(15).row();

        // === Add Columns ===
        rootTable.add(leftColumn).top().left();
        rootTable.add(rightColumn).top().right();
        rootTable.row();

        // === Result Label ===
        Table resultRow = new Table();
        resultRow.add(resultLabel).colspan(2).center().padTop(20);
        rootTable.add(resultRow).colspan(2).padBottom(10).row();

        // === Back Button Bottom-Left ===
        Table bottomRow = new Table();
        bottomRow.add(backButton).left().width(250).pad(20);
        rootTable.add(bottomRow).colspan(2).left().expandY().bottom();
    }


    private AnimatedSpriteActor createHero(Heros hero) {
        Texture texture = new Texture(Gdx.files.internal(hero.getTexturePath()));
        return new AnimatedSpriteActor(texture, hero.getFrameWidth(), hero.getFrameHeight(), hero.getUpRow(), 0.3f);
    }

    private class AvatarClickListener extends ClickListener {
        private final Heros hero;
        private final Label resultLabel;

        public AvatarClickListener(Heros hero, Label resultLabel) {
            this.hero = hero;
            this.resultLabel = resultLabel;

        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            GameAssetManager.getGameAssetManager().getBrightClickSound().play();

            Result result = controller.setAvatar(hero);
            resultLabel.setText(result.toString());

            if (result.isSuccessful()) {
                refresh();
            }
        }

    }


    private class FieldDialogListener extends ClickListener {
        private final String title;
        private final String hint;
        private final InputHandler handler;
        private final Label resultLabel;

        public FieldDialogListener(String title, String hint, InputHandler handler, Label resultLabel) {
            this.title = title;
            this.hint = hint;
            this.handler = handler;
            this.resultLabel = resultLabel;

        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            GameAssetManager.getGameAssetManager().getBrightClickSound().play();

            Dialog dialog = new Dialog(title, skin);
            TextField input = new TextField("", skin);
            input.setMessageText(hint);

            TextButton submit = new TextButton("Submit", skin);
            TextButton cancel = new TextButton("Cancel", skin);

            Label feedback = new Label("", skin);
            feedback.setWrap(true);

            submit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                    Result result = handler.onSubmit(input.getText().trim());

                    feedback.setText(result.toString());
                    if (result.isSuccessful()) {
                        resultLabel.setText(result.toString());
                        dialog.hide();
                        refresh();
                    }


                }
            });

            cancel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                    dialog.hide();
                }
            });

            dialog.getContentTable().add(input).width(500).padBottom(10).row();
            dialog.getContentTable().add(feedback).width(500).height(80).padBottom(20).padLeft(50).row();
            dialog.getButtonTable().add(submit).width(250).pad(5);
            dialog.getButtonTable().add(cancel).width(250).pad(5);

            dialog.show(stage);
            dialog.setSize(600, 400);
        }
    }


    @FunctionalInterface
    private interface InputHandler {
        Result onSubmit(String input);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }

    private void refresh() {
        Main.getMain().setScreen(new ProfileMenuView(controller, skin));
    }

}
