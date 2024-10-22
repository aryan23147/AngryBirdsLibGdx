package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {
    private final Game game;
    private SpriteBatch batch;
    private AssetManager assetManager;
    private Texture backgroundTexture;
    private Music music;
    private Stage stage;
    private Skin skin;
    private Table table;
    private BitmapFont font; // For font loaded via AssetManager

    public MainMenuScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("Skin/uiskin.json"));
        table = new Table();

        // Load all assets, including the font and the texture it uses
        assetManager.load("abs/MainMenuBackground(1).png", Texture.class);
        assetManager.load("music.mp3", Music.class);
        assetManager.load("font/Chewy.fnt", BitmapFont.class); // Load the .fnt file
        assetManager.finishLoading(); // Block until all assets are loaded
    }

    @Override
    public void show() {
        // Once loaded, retrieve the background texture
        backgroundTexture = assetManager.get("abs/MainMenuBackground(1).png", Texture.class);
        music = assetManager.get("music.mp3", Music.class);

        // Retrieve the font from AssetManager
        font = assetManager.get("font/Chewy.fnt", BitmapFont.class);

        // Create button style and apply font from AssetManager
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        Texture up = new Texture("abs/ButtonBackground.png");
        Drawable updraw = new TextureRegionDrawable(up);
        updraw.setMinWidth(250);
        updraw.setMinHeight(100);

        textButtonStyle.up = updraw;
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.overFontColor = Color.BLACK;
        textButtonStyle.font = font; // Use the loaded font here

        // Create buttons and add them to the stage
        TextButton playButton = new TextButton("Play", textButtonStyle);
        TextButton musicOnOffButton = new TextButton("MusicOnOff", textButtonStyle);
        playButton.setSize(250, 100); // Set size
        musicOnOffButton.setSize(250, 100);

        // Add listeners to buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectionScreen(game));  // Switch to level selection screen
            }
        });

        musicOnOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (music.isPlaying()) {
                    music.stop();
                } else {
                    music.play();
                }
            }
        });

        // Add buttons to the table and stage
        table.setFillParent(true);
        table.add(playButton).padTop(250f).padRight(150f);
        table.add(musicOnOffButton).padTop(250f);
        stage.addActor(table);

        // Set the input processor to handle user inputs for this stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin drawing the background texture
        batch.begin();
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        batch.end();

        // Update and draw the stage (for buttons)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Handle screen resizing
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        // Cleanup resources
        batch.dispose();
        assetManager.dispose();
        stage.dispose();
    }
}
