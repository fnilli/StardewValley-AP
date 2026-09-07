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
import com.group16.stardewvalley.controller.menu.MainMenuController;
import com.group16.stardewvalley.controller.menu.SignUpMenuController;
import com.group16.stardewvalley.controller.menu.StartMenuController;
import com.group16.stardewvalley.model.Result;
import com.group16.stardewvalley.model.graphics.GameAssetManager;
import com.group16.stardewvalley.model.user.SecurityQuestions;

import java.util.Arrays;

public class SignUpMenuView implements Screen {
    private Stage stage;
    private final Label titleLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField passwordConfirmField;
    private final TextField nicknameField;
    private final TextField emailField;
    private final SelectBox<String> genderSelectBox;
    private final TextButton randomPasswordButton;
    private final TextButton registerButton;
    private final Table table;
    private final SignUpMenuController controller;
    private final Label messageLabel;
    private final TextButton backButton;

    private Dialog securityDialog;
    private SelectBox<String> questionSelectBox;
    private TextField answerField;


    public SignUpMenuView(SignUpMenuController controller, Skin skin) {
        this.controller = controller;
        this.titleLabel = new Label("S i g n  U p    M e n u", skin.get("title", Label.LabelStyle.class));

        this.usernameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.passwordConfirmField = new TextField("", skin);
        this.nicknameField = new TextField("", skin);
        this.emailField = new TextField("", skin);
        this.genderSelectBox = new SelectBox<>(skin);
        genderSelectBox.setItems("Male", "Female");
        this.randomPasswordButton = new TextButton("random", skin);



        this.registerButton = new TextButton("Sign Up", skin);
        this.messageLabel = new Label("", skin);

        this.backButton = new TextButton("Back", skin);

        this.table = new Table();

        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        passwordConfirmField.setPasswordCharacter('*');
        passwordConfirmField.setPasswordMode(true);


        controller.setView(this);
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Texture bgTexture = new Texture(Gdx.files.internal("Background/mainBack.jpeg"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        // Load and display logo image
        Texture logoTexture = new Texture(Gdx.files.internal("Background/Sign-Up-Menu.png"));
        Image logoImage = new Image(logoTexture);
        logoImage.setSize(logoTexture.getWidth() * 0.4f, logoTexture.getHeight() * 0.4f);

        //*------------------------------------------*//
        //button functions


        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                Result result = controller.register(
                    usernameField.getText(),
                    passwordField.getText(),
                    passwordConfirmField.getText(),
                    nicknameField.getText(),
                    emailField.getText(),
                    genderSelectBox.getSelected().toLowerCase()
                );

                setMessage(result.toString());

                if (result.isSuccessful()) {
                    showSecurityQuestionDialog();

                }



            }
        });

        randomPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();


                String generatedPassword = controller.generateRandomPassword();
                passwordField.setText(generatedPassword);
                passwordConfirmField.setText(generatedPassword);

                System.out.println(passwordField.getText());
                System.out.println(passwordConfirmField.getText());

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

//        table.add(titleLabel).colspan(2).padTop(30);
//        table.add(logoImage).colspan(2).padTop(30);
        // === Title Row ===
        Table titleTable = new Table();
        titleTable.center();
        titleTable.add(logoImage).center().padBottom(20).row();
        table.add(titleTable).colspan(2).center().padBottom(40).row();


        table.row().pad(10, 0, 10, 0);
        // Username row
        table.add(new Label("Username:", controller.getSkin())).left();
        table.add(usernameField).width(400);
        table.row().pad(10, 0, 10, 0);

        // Password row
        Table passwordRow = new Table();
        passwordRow.add(randomPasswordButton).width(270).padRight(10);
        passwordRow.add(passwordField).width(120); // 270 + 10 + 120 = 400 total
        table.add(new Label("Password:", controller.getSkin())).left();
        table.add(passwordRow).width(400);
        table.row().pad(10, 0, 10, 0);


        //password confirm row
        table.add(new Label("Password again:", controller.getSkin())).left();
        table.add(passwordConfirmField).width(400);
        table.row().pad(10, 0, 10, 0);

        //nickname row
        table.add(new Label("Nickname:", controller.getSkin())).left();
        table.add(nicknameField).width(400);
        table.row().pad(10, 0, 10, 0);

        //email row
        table.add(new Label("Email:", controller.getSkin())).left();
        table.add(emailField).width(400);
        table.row().pad(10, 0, 10, 0);

        // Gender selection row
        table.add(new Label("Gender:", controller.getSkin())).left();
        table.add(genderSelectBox).width(400);
        table.row().pad(10, 0, 10, 0);

        // Register button row
        table.add(registerButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);

        // Message label row
        table.add(messageLabel).colspan(2).center();

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

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }


    public void setMessage(String message) {
        messageLabel.setText(message);
    }


    public TextButton getRegisterButton() {
        return registerButton;
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

    public SelectBox<String> getGenderSelectBox() {
        return genderSelectBox;
    }


    public TextButton getBackButton() {
        return backButton;
    }

    private void showSecurityQuestionDialog() {
        securityDialog = new Dialog("Security Question", controller.getSkin()) {
            @Override
            protected void result(Object object) {
                GameAssetManager.getGameAssetManager().getBrightClickSound().play();

                if (Boolean.TRUE.equals(object)) {
                    String selectedQuestionText = questionSelectBox.getSelected();
                    String answer = answerField.getText().trim();

                    if (answer.isEmpty()) {
                        setMessage("Answer cannot be empty!");
                        return;
                    }

                    // Map question text back to enum to get the number
                    SecurityQuestions selectedQuestion = Arrays.stream(SecurityQuestions.values())
                        .filter(q -> q.getQuestion().equals(selectedQuestionText))
                        .findFirst()
                        .orElse(null);

                    if (selectedQuestion == null) {
                        setMessage("Invalid security question selected!");
                        return;
                    }

                    // Call the actual controller method with both answer and confirm (same in this case)
                    Result result = controller.setSecurityQuestion(
                        usernameField.getText(),
                        String.valueOf(selectedQuestion.getNumber()),
                        answer,
                        answer // we're using the same field for simplicity
                    );

                    setMessage(result.toString());

                    if (result.isSuccessful()) {
                        this.hide();
                        Main.getMain().getScreen().dispose();
                        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                    }

                } else {
                    this.hide(); // Cancel
                }
            }
        };

        questionSelectBox = new SelectBox<>(controller.getSkin());
        questionSelectBox.setItems(
            SecurityQuestions.q1.getQuestion(),
            SecurityQuestions.q2.getQuestion(),
            SecurityQuestions.q3.getQuestion(),
            SecurityQuestions.q4.getQuestion(),
            SecurityQuestions.q5.getQuestion()
        );

        answerField = new TextField("", controller.getSkin());

        Table content = securityDialog.getContentTable();
        content.pad(40);  // more padding
        content.defaults().width(900); // make content wider
        content.add(new Label("Choose a security question:", controller.getSkin())).left().colspan(2).row();
        content.add(questionSelectBox).colspan(2).padBottom(20).row();
        content.add(new Label("Your answer:", controller.getSkin())).left().colspan(2).row();
        content.add(answerField).colspan(2).padBottom(20).row();

        securityDialog.button("OK", true);
        securityDialog.button("Cancel", false);

        // Resize and center manually after showing
        securityDialog.show(stage);
        securityDialog.setSize(1000, 600);  // width, height
        securityDialog.setPosition(
            (stage.getWidth() - securityDialog.getWidth()) / 2f,
            (stage.getHeight() - securityDialog.getHeight()) / 2f
        );
    }


}
