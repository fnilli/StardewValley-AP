package com.group16.stardewvalley.view.menuGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.group16.stardewvalley.Main;
import com.group16.stardewvalley.controller.menu.LoginMenuController;
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.controller.menu.StartMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.user.SecurityQuestions;
import com.group16.stardewvalley.model.user.User;

import java.util.Arrays;

import static com.group16.stardewvalley.model.user.User.getUserByUsername;


public class LoginMenuView implements Screen {
    private final LoginMenuController controller;
    private Stage stage;
    private final Label titleLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextButton loginButton;
    private final Table table;
    private final Label messageLabel;
    private final TextButton forgotPasswordButton;
    private final TextField securityAnswerField;
    private boolean securityUIVisible = false;
    private final TextButton backButton;
    private final CheckBox stayLoggedInCheckbox;


    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.controller = controller;
        this.titleLabel = new Label("L o g i n   M e n u", skin.get("title", Label.LabelStyle.class));
        this.usernameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.loginButton = new TextButton("Login", skin);
        this.table = new Table();
        this.messageLabel = new Label("", skin); // empty message initially
        this.forgotPasswordButton = new TextButton("Forgot Password?", skin);
        this.securityAnswerField = new TextField("", skin);
        securityAnswerField.setMessageText("Your security answer");
        this.backButton = new TextButton("Back", skin);
        this.stayLoggedInCheckbox = new CheckBox(" Stay Logged In", skin);

        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        controller.setView(this);
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load background texture and set it as an Image
        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true); // Make it stretch to screen size

        // Add background first so it stays behind everything else
        stage.addActor(background);

        // Load and display logo image
        Texture logoTexture = new Texture(Gdx.files.internal("Background/Login-Menu.png"));
        Image logoImage = new Image(logoTexture);
        logoImage.setScale(1.2f);

        //*------------------------------------------*//
        //button functions

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                Result result = controller.login(
                    usernameField.getText(),
                    passwordField.getText(),
                    stayLoggedInCheckbox.isChecked()
                );

                setMessage(result.toString());

                if (result.isSuccessful()) {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));                }
            }
        });

        forgotPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                String username = usernameField.getText().trim();
                if (username.isEmpty()) {
                    setMessage("Please enter your username first.");
                    return;
                }

                User user = getUserByUsername(username);
                if (user == null) {
                    setMessage("User not found.");
                    return;
                }

                showSecurityQuestionDialog(user);
            }
        });




        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new StartMenuView(new StartMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        //*------------------------------------------*//


        table.setFillParent(true);
        table.center();
        table.add(logoImage).padLeft(200).padBottom(100);
//        table.add(titleLabel).colspan(2).padTop(30);
        table.row().pad(10, 0, 10, 0);
        table.add(new Label("Username"+":", controller.getSkin())).left();
        table.add(usernameField).width(300);
        table.row().pad(10, 0, 10, 0);
        table.add(new Label("Password"+":", controller.getSkin())).left();
        table.add(passwordField).width(300);

        table.row().pad(10, 250, 10, 0);
        Table loginRow = new Table();
        loginRow.add(loginButton).width(350).padRight(20);
        loginRow.add(stayLoggedInCheckbox).left();

        table.add(loginRow).colspan(2).padLeft(300);

        table.row().pad(5, 0, 5, 0);
        table.add(forgotPasswordButton).colspan(2).width(550);

        table.row().pad(10, 0, 10, 0);
        table.add(messageLabel).colspan(2);

        table.row().pad(0, 0, 0, 600);
        table.add(backButton).width(250);


        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Skin getSkin() {
        return controller.getSkin();
    }


    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public TextButton getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public TextField getSecurityAnswerField() {
        return securityAnswerField;
    }


    public boolean isSecurityUIVisible() {
        return securityUIVisible;
    }

    private void showSecurityQuestionDialog(User user) {
        TextField answerField = new TextField("", getSkin());
        answerField.setMessageText("Your answer");

        Dialog dialog = new Dialog("Security Check", getSkin());

        Table content = dialog.getContentTable();
        content.add(new Label("Security Question:", getSkin())).padBottom(5).colspan(1);
        content.row();
        content.add(new Label(user.getUserSecurityQuestion().getQuestion(), getSkin())).padBottom(10).colspan(1);
        content.row();
        content.add(answerField).width(300).padBottom(10);

        TextButton submitButton = new TextButton("Submit", getSkin());
        TextButton cancelButton = new TextButton("Cancel", getSkin());

        dialog.getButtonTable().add(submitButton).width(250).pad(5);
        dialog.getButtonTable().add(cancelButton).width(250).pad(5);

        // Show the dialog
        dialog.show(stage);
        dialog.setSize(800, 400);  // width, height

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                dialog.hide();
            }
        });

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                String enteredAnswer = answerField.getText().trim();
                if (enteredAnswer.equalsIgnoreCase(user.getSecurityAnswer())) {
                    setMessage("Security answer correct.");
                    dialog.hide();
                    showResetPasswordDialog(user);  // ➤ Show second dialog
                } else {
                    setMessage("Incorrect security answer.");
                }
            }
        });
    }

    private void showResetPasswordDialog(User user) {
        TextField newPasswordField = new TextField("", getSkin());
//        newPasswordField.setPasswordMode(true);
//        newPasswordField.setPasswordCharacter('*');
        newPasswordField.setMessageText("New password");

        TextButton randomButton = new TextButton("Random", getSkin());

        Dialog dialog = new Dialog("Reset Password", getSkin());

        Table content = dialog.getContentTable();
        content.add(new Label("Enter your new password:", getSkin())).colspan(2).padBottom(5);
        content.row();
        content.add(newPasswordField).width(300).padRight(10).padBottom(10);
        content.add(randomButton).width(300).padBottom(10);

        TextButton confirmButton = new TextButton("Submit", getSkin());
        TextButton cancelButton = new TextButton("Cancel", getSkin());

        dialog.getButtonTable().add(confirmButton).width(300).pad(5);
        dialog.getButtonTable().add(cancelButton).width(300).pad(5);

        dialog.show(stage);
        dialog.setSize(800, 400);  // width, height


        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                String generated = controller.generateRandomPassword();
                newPasswordField.setText(generated);
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();
                dialog.hide();
            }
        });

        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                String newPassword = newPasswordField.getText().trim();

                Result result = controller.getNewPassword(user, newPassword);
                if (result.isSuccessful()) {
                    setMessage(result.toString());
                    dialog.hide();
                } else {
                    setMessage(result.toString());
                }

            }
        });
    }



}
