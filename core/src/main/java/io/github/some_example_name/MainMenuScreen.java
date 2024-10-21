package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {
    private final Game game;
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private BitmapFont font;
    private AssetManager assetManager;
    private Texture backgroundTexture;
    private Music music;
    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay ,buttonExit;
    public MainMenuScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();  // You can load custom fonts here.
        assetManager=new AssetManager();
        stage =new Stage();
        skin = new Skin(Gdx.files.internal("Skin/uiskin.json"));
        table=new Table();

    }

    @Override
    public void show() {
        // Setup when this screen is shown.
        // Load the background image
        assetManager.load("background.png", Texture.class); // Replace with your background image file path
        assetManager.load("music.mp3", Music.class);
        assetManager.finishLoading(); // Block until all assets are loaded

        // Once loaded, retrieve the texture
        backgroundTexture = assetManager.get("background.png", Texture.class);
        music=assetManager.get("music.mp3",Music.class);
        TextButton playButton = new TextButton("Play",skin);
        TextButton musicOnOffButton=new TextButton ("MusicOnOff",skin);
//        playButton.setPosition(200, 300);  // Set position
        playButton.setSize(300, 100); // Set size
        musicOnOffButton.setSize(300,100);

        // Add listener to the button
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectionScreen(game));  // Switch to level selection screen
            }
        });
        musicOnOffButton.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x,float y){
                if(music.isPlaying()){
                    music.stop();
                }
                else{
                    music.play();
                }
            }
        });
        stage.addActor(table);
        table.setFillParent(true);
        table.add(playButton).center();
        table.row();
        table.row().pad(5);
        table.add(musicOnOffButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin drawing
        batch.begin();
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        font.draw(batch, "Main Menu", 200, 400);  // Display main menu text
        font.draw(batch, "Press ENTER to start", 200, 300);  // User prompt

        // End drawing
        batch.end();

        // Check for user input to switch screens
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
        font.dispose();
    }
}

