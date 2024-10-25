package io.github.some_example_name;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleDummy;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleMusic;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleMute;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleRestart;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleSave;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleback;
import static io.github.some_example_name.TextButtonStyles.TextButtonStylepause;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.w3c.dom.Text;

import java.nio.file.attribute.UserPrincipalLookupService;

public class GameScreen implements Screen {
    private boolean IsPaused=false;
    private final Game game;
    private int level;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private Music music;
//    private Skin skin;
    private Table table;
    private TextButton back;
    private Bird redBird;
    private AssetManager assetManager;
    private Texture backgroundTexture;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Ground ground;
    private OrthographicCamera cam;
    private Pig pig;
    private Box box1;
    private Box box2;
    private Box box3;
    private Window Pausewindow;
    private TextButton pauseButton;
    private TextButton winButton;
    private TextButton looseButton;
    private boolean isMusicPlaying;
    public GameScreen(Game game, int level) {
        this.game = game;  // Save the reference to the main game object
        this.level = level;  // Save the level number
        this.music=Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        this.music.play();
        music.setLooping(true);
        isMusicPlaying=true;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));
        float w=Gdx.graphics.getWidth();
        float h=Gdx.graphics.getHeight();
        cam=new OrthographicCamera();
        cam.setToOrtho(false,w,h);
        winButton=new TextButton("Win",TextButtonStyleDummy);
        looseButton=new TextButton("Loose",TextButtonStyleDummy);

        stage =new Stage();
//        skin = new Skin(Gdx.files.internal("Skin/uiskin.json"));
        table=new Table();
        // Make sure RedBird extends Bird
        System.out.println("bdw");
        createWindow();
        back=new TextButton("Exit",TextButtonStyleback);
        pauseButton=new TextButton("Pause",TextButtonStylepause);
        System.out.println("wb");
        assetManager=new AssetManager();
        world = new World(new Vector2(0, -9.8f), false);

        stage.addActor(table);
        table.top().left();
        table.setFillParent(true);
        table.add(back).padTop(5f).padLeft(5f).top().left();
        table.add(pauseButton).padTop(5f).padLeft(5f).top().left();

        table.add(Pausewindow).center();
        table.add(winButton);
        table.add(looseButton);
//        table.row();

        back.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x,float y){
                game.setScreen(new LevelSelectionScreen(game));
            }
        });
        pauseButton.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x,float y){
                if(!IsPaused){
                Pausewindow.setVisible(true);
                pauseButton.setText("Resume");
                pause();
                IsPaused=true;}
                else{
                    Pausewindow.setVisible(false);
                    pauseButton.setText("Pause");
                    resume();
                    IsPaused=false;
                }
            }
        });
        TextButton musiconoffButton =new TextButton("", TextButtonStyleMusic);
        TextButton saveGameButton =new TextButton("",TextButtonStyleSave);
        TextButton restartButton= new TextButton("",TextButtonStyleRestart);
        musiconoffButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                if(!isMusicPlaying){
                    music.play();
                    isMusicPlaying=true;
                    musiconoffButton.setStyle(TextButtonStyleMusic);
                }
                else{
                    musiconoffButton.setStyle(TextButtonStyleMute);
                    isMusicPlaying=false;
                    music.stop();
                }
            }
        });
        Pausewindow.add().padBottom(300);
        Pausewindow.row();
        Pausewindow.add(musiconoffButton);
        Pausewindow.add(saveGameButton);
        Pausewindow.add(restartButton);
        debugRenderer = new Box2DDebugRenderer();
        redBird = new RedBird(world,100,150);
        ground=new Ground(world);
        pig=new MediumPig(650,200,world);
        box1=new Box(550,82,world,64,64);
        box2=new Box(550,50,world,64,64);
        box3=new Box(500,50,world,64,64);
//        cam=new OrthographicCamera(30,30*(Gdx.gr))
        Gdx.input.setInputProcessor(stage);



    }

    @Override
    public void show() {
        assetManager.load("GameBackground.png",Texture.class);
        assetManager.finishLoading();
        backgroundTexture = assetManager.get("GameBackground.png", Texture.class);
        // Initialize resources and setup the game for the given level
        System.out.println("Starting level: " + level);
//        stage.addActor(table);
//        table.setFillParent(true);
//        table.add(redBird);
    }
    public void createWindow(){
        Texture backgroundTexture = new Texture("abs/PauseWindowBackground (3).png");
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundTexture);

        // Define custom style
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.WHITE;
        windowStyle.background = backgroundDrawable;

        // Create and style the window
        Pausewindow = new Window("", windowStyle);
        Pausewindow.setVisible(false);

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        font.draw(batch, "Playing Level: " + level, 200, 400);
//        font.draw(batch, "Press ESC to return to Main Menu", 200, 300);
//        ground.draw(batch);
        pig.draw(batch);
        redBird.draw(batch);
        ground.draw(batch);
        box1.draw(batch);
        box2.draw(batch);
        box3.draw(batch);
        batch.end();

        // Return to Main Menu if ESC is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
        stage.act(delta);
        stage.draw();
        world.step(1/60f, 6, 2);
        debugRenderer.render(world, cam.combined);

        // Add the game logic for this level here (e.g., enemy spawning, player movement)
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {
        dispose();
    }
    @Override
    public void dispose() {
        // Dispose of resources
        batch.dispose();
        font.dispose();
        debugRenderer.dispose();
        music.dispose();
        music.stop();
    }
}
