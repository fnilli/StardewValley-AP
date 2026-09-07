package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.menu.GameMenuController;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.controller.menu.ProfileMenuController;
import com.group16.stardewvalley.controller.menu.StartMenuController;
import com.group16.stardewvalley.model.app.App;
import com.group16.stardewvalley.model.graphics.AnimatedSpriteActor;
import com.group16.stardewvalley.model.graphics.GameAssetManager;

public class MainMenuView implements Screen {
    private Stage stage;

    private final TextButton profileButton;
    private final TextButton preGameButton;
    private final TextButton logoutButton;

    private final Label usernameLabel;

    private final Label gameTitle;
    private final Table table;
    private final MainMenuController controller;

    private AnimatedSpriteActor animatedAvatar;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.controller = controller;

        this.gameTitle = new Label("M a i n    M e n u", skin.get("title", Label.LabelStyle.class));
        this.profileButton = new TextButton("Profile", skin);
        this.preGameButton = new TextButton("Game", skin);
        this.logoutButton = new TextButton("Logout", skin);

        this.usernameLabel = new Label("Name: " + App.getLoggedInUser().getNickName(), skin);

        // Load user sprite sheet
        Texture avatarTexture = new Texture(App.getLoggedInUser().getHero().getTexturePath());

        // Create animated avatar actor (row 2 = index 2; assuming walking down)
        this.animatedAvatar = new AnimatedSpriteActor(avatarTexture, 16, 32, 0, 0.3f);
        animatedAvatar.setSize(64, 128); // Resize for menu


        this.table = new Table();

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Background
        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);
// Load and display logo image
        Texture logoTexture = new Texture(Gdx.files.internal("Background/Main-Menu.png"));
        float screenWidth = Gdx.graphics.getWidth();
        float logoWidth = screenWidth * 0.3f;
        float aspect = logoTexture.getHeight() / (float) logoTexture.getWidth();
        float logoHeight = logoWidth * aspect;

        Image logoImage = new Image(logoTexture);
        logoImage.setSize(logoWidth, logoHeight);



        //*------------------------------------------*//
        //button functions

        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        preGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreGameMenuView(new GameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                controller.logout();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(
                    new StartMenuView(new StartMenuController(), GameAssetManager.getGameAssetManager().getSkin())
                );
            }
        });

        //*------------------------------------------*//

        // === Root Table ===
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.center().pad(30);

        stage.addActor(rootTable);



// === Logo + Title Row ===
        Table titleTable = new Table();
        titleTable.center();

        titleTable.add(logoImage).center().padBottom(20).row();
// titleTable.add(gameTitle).padBottom(50).row(); // Uncomment if you want title text

        rootTable.add(titleTable).colspan(2).center().padBottom(40).row();

        // === LEFT COLUMN (User Info) ===
        Table leftColumn = new Table();
        leftColumn.top().padRight(100); // space between left and right

        leftColumn.add(animatedAvatar).size(128, 256).padBottom(20).row();
        leftColumn.add(usernameLabel).left().padBottom(30);

        // === RIGHT COLUMN (Buttons) ===
        Table rightColumn = new Table();
        rightColumn.top();

        rightColumn.add(preGameButton).width(280).padBottom(15).row();
        rightColumn.add(profileButton).width(280).padBottom(15).row();
        rightColumn.add(logoutButton).width(280);

        // === Add Columns to Root Table ===
        rootTable.add(leftColumn).top().left();
        rootTable.add(rightColumn).top().right();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() {
    }

    // Getters for controller access
    public TextButton getProfileButton() { return profileButton; }
    public TextButton getPreGameButton() { return preGameButton; }
    public TextButton getLogoutButton() { return logoutButton; }
    public Label getUsernameLabel() { return usernameLabel; }

}
