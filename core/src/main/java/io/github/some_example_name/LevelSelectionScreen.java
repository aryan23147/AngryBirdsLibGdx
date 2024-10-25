package io.github.some_example_name;

import static io.github.some_example_name.TextButtonStyles.TextButtonStyleMusic;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleMute;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleback;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelSelectionScreen implements Screen {
    private final Game game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture backgroundTexture;
    private Stage stage;
    private Table table;
    private BitmapFont chewyfont;
    private MainMenuScreen mainMenu;
    private boolean isMusicPlaying;
    private Music music;
    private TextButton musicOnOffButton;
    public LevelSelectionScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        stage = new Stage();
        table = new Table();
        chewyfont=new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));
        musicOnOffButton=new TextButton("",TextButtonStyleMusic);
        this.music=Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        this.music.play();
        this.music.setLooping(true);
        isMusicPlaying=true;

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Load background texture
        backgroundTexture = new Texture("abs/LevelSelectionScreen(1).png");

        // Create a basic style for the buttons
        Texture up=new Texture("abs/LevelButtonBackground.png");
        Drawable updraw=new TextureRegionDrawable(up);
        updraw.setMinWidth(200);
        updraw.setMinHeight(200);
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.up=updraw;
        buttonStyle.font = chewyfont;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.overFontColor = Color.BLACK;  // Change color on hover
          // Change color on hover
//        public void createPauseWindow(){
//            Texture windowBackgroundTexture = new Texture("path/to/windowBackground.png");
//            Drawable windowBackground = new TextureRegionDrawable(new TextureRegion(windowBackgroundTexture));
//
//// Create a font for the window title
//            BitmapFont windowFont = new BitmapFont(Gdx.files.internal("path/to/font.fnt"));
//
//// Set up the window style
//            Window.WindowStyle windowStyle = new Window.WindowStyle();
//            windowStyle.titleFont = windowFont;  // Font for the title
//            windowStyle.background = windowBackground;  // Background for the window
//
//// Create the window with the custom style
//            Window customWindow = new Window("My Window Title", windowStyle);
//
//// Set window size, position, etc.
//            customWindow.setSize(400, 300);
//            customWindow.setPosition(100, 100);
////            pauseWindow=new Window("Pause Window")
//        };
        musicOnOffButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                if(!isMusicPlaying){
                    music.play();

                    isMusicPlaying=true;
                    musicOnOffButton.setStyle(TextButtonStyleMusic);
                }
                else{
                    musicOnOffButton.setStyle(TextButtonStyleMute);
                    isMusicPlaying=false;
                    music.stop();
                }
            }
        });
        // Create buttons for levels
        TextButton level1Button = new TextButton("Level 1", buttonStyle);
        TextButton level2Button = new TextButton("Level 2", buttonStyle);
        TextButton level3Button = new TextButton("Level 3", buttonStyle);
        TextButton exitButton =new TextButton("Exit",TextButtonStyleback);
        // Add listeners to the buttons
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, 1));  // Start Level 1
            }
        });
        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, 2));  // Start Level 2
            }
        });
        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, 3));  // Start Level 3
            }
        });

        exitButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x,float y){
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Add buttons to the table with padding
        table.setFillParent(true);
        table.add(level1Button).pad(10);
//        table.row();
        table.add(level2Button).pad(10);
//        table.row();
        table.add(level3Button).pad(10);
        table.row();
        table.add(exitButton).bottom().left().padBottom(10).padRight(10);
        table.add(musicOnOffButton).bottom().left().padBottom(10);
        // Add the table to the stage
        stage.addActor(table);
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
//        font.draw(batch, "Level Selection", 200, 400);
        batch.end();

        // Draw the stage (buttons)
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
        batch.dispose();
        font.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        music.dispose();
    }
}
