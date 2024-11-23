package io.github.some_example_name.screens;

import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleMusic;
import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleMute;
import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.some_example_name.setUp.LevelManager;
import io.github.some_example_name.setUp.WindowCreator;

public class LevelSelectionScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Stage stage;
    private Table table;
    private BitmapFont chewyFont;
    private TextButton musicOnOffButton;

    public LevelSelectionScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        stage = new Stage();
        table = new Table();

        // Load assets for the screen
        chewyFont = new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));
        backgroundTexture = new Texture("abs/LevelSelectionScreen(1).png");

        // Set up music toggle button with initial style based on current music state
        musicOnOffButton = new TextButton("", game.isMusicPlaying() ? TextButtonStyleMusic : TextButtonStyleMute);
        setupMusicToggleButton();

        // Create button style for level selection buttons
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        Texture buttonTexture = new Texture("abs/LevelButtonBackground.png");
        Drawable buttonDrawable = new TextureRegionDrawable(buttonTexture);
        buttonDrawable.setMinWidth(200);
        buttonDrawable.setMinHeight(200);
        buttonStyle.up = buttonDrawable;
        buttonStyle.font = chewyFont;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.overFontColor = Color.BLACK;

        // Create and set up buttons
        createLevelButtons(buttonStyle);
    }

    private void setupMusicToggleButton() {
        musicOnOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toggleMusic();
                musicOnOffButton.setStyle(game.isMusicPlaying() ? TextButtonStyleMusic : TextButtonStyleMute);
            }
        });
    }

    private void createLevelButtons(TextButton.TextButtonStyle buttonStyle) {
        // Level selection buttons
        TextButton level1Button = new TextButton("Level 1", buttonStyle);
        TextButton level2Button = new TextButton("Level 2", buttonStyle);
        TextButton level3Button = new TextButton("Level 3", buttonStyle);
        TextButton exitButton = new TextButton("", TextButtonStyleback);
        Window loadWindow= WindowCreator.createLoadedWindow(stage,chewyFont,game,1);
        // Add listeners to buttons
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(LevelManager.isLevelSaved(1)) {
                    loadWindow.toFront();
                    loadWindow.setVisible(!loadWindow.isVisible());
                    System.out.println("There is a saved game.");
                }
                else {
                    game.setScreen(new GameScreen(game, 1));
                }
            }
        });
        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(LevelManager.isLevelSaved(1)) {
                    loadWindow.toFront();
                    loadWindow.setVisible(!loadWindow.isVisible());
                    System.out.println("There is a saved game.");
                }
                else {
                    game.setScreen(new GameScreen(game, 2));
                }
            }
        });
        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(LevelManager.isLevelSaved(1)) {
                    loadWindow.toFront();
                    loadWindow.setVisible(!loadWindow.isVisible());
                    System.out.println("There is a saved game.");
                }
                else {
                    game.setScreen(new GameScreen(game, 3));
                }
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Add buttons to table
        table.setFillParent(true);
        table.add(level1Button).pad(10);
        table.add(level2Button).pad(10);
        table.add(level3Button).pad(10);
        table.row();
//        table.add(exitButton).bottom().left().padBottom(10).padRight(10);
//        table.add(musicOnOffButton).bottom().left().padBottom(10);

        exitButton.setSize(100,150);
        musicOnOffButton.setSize(100,100);

        exitButton.setPosition(40, stage.getHeight()-150); // Adjust x and y for placement
        musicOnOffButton.setPosition(stage.getWidth()-100,stage.getHeight()-120); // Adjust x and y for placement

        // Add buttons to the winWindow without using table positioning
        stage.addActor(exitButton);
        stage.addActor(musicOnOffButton);

        // Add the table to the stage
        stage.addActor(table);
    }

    private TextButton.TextButtonStyle createButtonStyle(String buttonName) {
        // Load the button textures
        Texture buttonUpTexture = new Texture(Gdx.files.internal(buttonName)); // Replace with your actual texture path
        Texture buttonDownTexture = new Texture(Gdx.files.internal(buttonName)); // Replace with your actual texture path

        // Create a Drawable for each button state
        TextureRegionDrawable upDrawable = new TextureRegionDrawable(buttonUpTexture);
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(buttonDownTexture);

        // Create and style the button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = upDrawable;
        buttonStyle.down = downDrawable;
        buttonStyle.font = chewyFont; // Set your font

        return buttonStyle;
    }

    @Override
    public void show() {
        // Set input processor to handle stage inputs
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw background texture
        batch.begin();
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        batch.end();

        // Draw stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        // Dispose resources
        batch.dispose();
        chewyFont.dispose();
        stage.dispose();
        backgroundTexture.dispose();
    }
}
