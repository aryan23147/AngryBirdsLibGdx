package io.github.some_example_name;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleDummy;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleMusic;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleMute;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleRestart;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleSave;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleback;
import static io.github.some_example_name.TextButtonStyles.TextButtonStylepause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;


public class GameScreen implements Screen {
    private boolean IsPaused=false;
    private final Main game;
    private int level;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private Table table;
    private TextButton backButton;
    private Bird redBird;
    private Bird blackBird;
    private Bird blueBird;
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
    private Window pauseWindow;
    private TextButton pauseButton;
    private TextButton winButton;
    private Window winWindow;
    private TextButton nextLevelButton;
    private Window loseWindow;
    private TextButton loseButton;
    private boolean isMusicPlaying;
    private TextButton musiconoffButton;
    private Slingshot slingshot;
    private Queue<Bird> birdQueue;
    private List<Bird> allBirds;
    private static final float PIXELS_TO_METERS = 100f;


    public GameScreen(Main game, int level) {
        this.game = game;  // Save the reference to the main game object
        this.level = level;  // Save the level number
        isMusicPlaying = game.isMusicPlaying();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));
        float w=Gdx.graphics.getWidth();
        float h=Gdx.graphics.getHeight();
        cam=new OrthographicCamera();
        cam.setToOrtho(false,w,h);
        winButton=new TextButton("Win",TextButtonStyleDummy);
        loseButton =new TextButton("Lose",TextButtonStyleDummy);
        slingshot=new Slingshot(130,50);

        stage =new Stage();
        table=new Table();
//        System.out.println("bdw");
        createPauseWindow();
        createWinWindow();
        createLoseWindow();

        backButton =new TextButton("",TextButtonStyleback);
        pauseButton=new TextButton("",TextButtonStylepause);
//        System.out.println("wb");
        assetManager=new AssetManager();
        world = new World(new Vector2(0, -9.8f), false);

        stage.addActor(table);
        table.top().left();
        table.setFillParent(true);
//        table.add(backButton).padTop(5f).padLeft(5f).top().left();
        table.add(pauseButton).padTop(5f).padLeft(5f).top().left();
        table.add(pauseWindow).center();
        table.add(winButton);
        table.add(loseButton);
//        table.row();

        backButton.setSize(100,150);
        backButton.setPosition(100, stage.getHeight()-140); // Adjust x and y for placement
        stage.addActor(backButton);

        backButton.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x,float y){
                game.setScreen(new LevelSelectionScreen(game));
            }
        });
        pauseButton.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x,float y){
                if(!IsPaused){
                    pauseWindow.setVisible(true);
                    pauseWindow.toFront();
                    pauseButton.setText("");
                    pause();
                    IsPaused=true;
                }
                else{
                    pauseWindow.setVisible(false);
                    pauseButton.setText("");
                    resume();
                    IsPaused=false;
                }
            }
        });
        musiconoffButton =new TextButton("", TextButtonStyleMusic);
        TextButton saveGameButton =new TextButton("",TextButtonStyleSave);
        TextButton restartButton= new TextButton("",TextButtonStyleRestart);

        musiconoffButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toggleMusic();  // Toggle music in Main class
                musiconoffButton.setStyle(game.isMusicPlaying() ? TextButtonStyleMusic : TextButtonStyleMute);
            }
        });

        winButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                winWindow.setVisible(true);
                winWindow.toFront();
            }
        });

        loseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loseWindow.setVisible(true);
                loseWindow.toFront();
            }
        });

        pauseWindow.add().padBottom(300);
        pauseWindow.row();
        pauseWindow.add(musiconoffButton);
        pauseWindow.add(saveGameButton);
        pauseWindow.add(restartButton);


        birdQueue = new Queue<>();
        allBirds = new ArrayList<>();
        debugRenderer = new Box2DDebugRenderer();
        redBird = new RedBird(world,125,150);
        blackBird = new BlackBird(world,80,150);
        blueBird = new BlueBird(world, 20, 150);
        birdQueue.addFirst(redBird);
        birdQueue.addLast(blackBird);
        birdQueue.addLast(blueBird);
        allBirds.add(redBird);
        allBirds.add(blackBird);
        allBirds.add(blueBird);
        ground=new Ground(world);
        pig=new MediumPig(650,200,world);
        box1=new Box(550,82,world,64,64);
        box2=new Box(550,50,world,64,64);
        box3=new Box(500,50,world,64,64);
//        cam=new OrthographicCamera(30,30*(Gdx.gr))
        Gdx.input.setInputProcessor(stage);
    }
    public void createPauseWindow(){
        Texture backgroundTexture = new Texture("abs/PauseWindowBackground (3).png");
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundTexture);

        // Define custom style
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.WHITE;
        windowStyle.background = backgroundDrawable;

        // Create and style the window
        pauseWindow = new Window("", windowStyle);
        pauseWindow.setVisible(false);

    }
    private void createWinWindow() {
        Texture backgroundTexture = new Texture("abs/WinWindowBackground.png");
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundTexture);

        Window.WindowStyle winWindowStyle = new Window.WindowStyle();
        winWindowStyle.titleFont = font;
        winWindowStyle.titleFontColor = Color.WHITE;
        winWindowStyle.background = backgroundDrawable;

        winWindow = new Window("", winWindowStyle);
        winWindow.setVisible(false);

        nextLevelButton = new TextButton("", createButtonStyle("abs/NextButton.png"));
        backButton = new TextButton("", createButtonStyle("abs/BackButton.png"));

        // Set button sizes
        nextLevelButton.setSize(100, 100); // Set width and height of the nextLevelButton
        backButton.setSize(100, 150); // Set width and height of the backButton

        // Position the buttons manually within the winWindow
        nextLevelButton.setPosition(280, 60); // Adjust x and y for placement
        backButton.setPosition(55, 30); // Adjust x and y for placement

        // Add buttons to the winWindow without using table positioning
        winWindow.addActor(nextLevelButton);
        winWindow.addActor(backButton);

        // Set the window size and position to center it on the screen
        winWindow.setSize(470, 500);
        winWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - winWindow.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - winWindow.getHeight() / 2f
        );

        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (level<3){
                    game.setScreen(new GameScreen(game, level + 1));
                }
                else {
                    game.setScreen(new LevelSelectionScreen(game));
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(winWindow);
    }

    private void createLoseWindow() {
        Texture backgroundTexture = new Texture("abs/LoseWindowBackground.png");
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundTexture);

        Window.WindowStyle loseWindowStyle = new Window.WindowStyle();
        loseWindowStyle.titleFont = font;
        loseWindowStyle.titleFontColor = Color.WHITE;
        loseWindowStyle.background = backgroundDrawable;

        loseWindow = new Window("", loseWindowStyle);
        loseWindow.setVisible(false);

        TextButton restartButton = new TextButton("", createButtonStyle("abs/RestartButton.png"));
        backButton = new TextButton("", createButtonStyle("abs/BackButton.png"));

        // Set button sizes
        restartButton.setSize(100, 100); // Set width and height of the nextLevelButton
        backButton.setSize(100, 150); // Set width and height of the backButton

        // Position the buttons manually within the winWindow
        restartButton.setPosition(280, 60); // Adjust x and y for placement
        backButton.setPosition(55, 30); // Adjust x and y for placement

        // Add buttons to the winWindow without using table positioning
        loseWindow.addActor(restartButton);
        loseWindow.addActor(backButton);

        // Set the window size and position to center it on the screen
        loseWindow.setSize(470, 500);
        loseWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - loseWindow.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - loseWindow.getHeight() / 2f
        );

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, level));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(loseWindow);
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
        buttonStyle.font = font; // Set your font

        return buttonStyle;
    }

    private void updateMusicButtonStyle() {
        if (isMusicPlaying) {
            musiconoffButton.setStyle(TextButtonStyles.TextButtonStyleMusic);
        } else {
            musiconoffButton.setStyle(TextButtonStyles.TextButtonStyleMute);
        }
    }

    private void checkAndLoadBird() {
        if (slingshot.isEmpty() && !birdQueue.isEmpty()) {
            Bird bird = birdQueue.removeFirst();  // Get the next bird from the queue
            slingshot.loadBird(bird);  // Load it into the slingshot
            bird.setInSlingshot(true);  // Disable gravity for the bird in the slingshot
            bird.getBody().setTransform(520 / PIXELS_TO_METERS, 420 / PIXELS_TO_METERS, 0);
            System.out.println("Bird loaded into slingshot at position: " + bird.x + ", " + bird.y);
        }

        // Any bird left in the queue should fall due to gravity
        for (Bird b : birdQueue) {
            b.setInSlingshot(false);  // Enable gravity for birds not in the slingshot
        }
    }

    @Override
    public void show() {
        assetManager.load("GameBackground.png",Texture.class);
        assetManager.finishLoading();
        backgroundTexture = assetManager.get("GameBackground.png", Texture.class);
        updateMusicButtonStyle();
        // Initialize resources and setup the game for the given level
        System.out.println("Starting level: " + level);
//        stage.addActor(table);
//        table.setFillParent(true);
//        table.add(redBird);
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
        pig.draw(batch);
        // Draw all birds in the birdQueue (they'll fall if not in slingshot)
        for (Bird bird : allBirds) {
            bird.draw(batch);
        }
        slingshot.draw(batch);
        ground.draw(batch);
        box1.draw(batch);
        box2.draw(batch);
        box3.draw(batch);
        batch.end();

        checkAndLoadBird();




        //this is deletable but i wanted to see how to do something
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                slingshot.releaseBird();
            }
        });



        // Return to Main Menu if ESC is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
        world.step(1/60f, 6, 2);
        for (Bird bird : allBirds) {
            Vector2 bodyPosition = bird.getBody().getPosition(); // Get position in meters
            bird.setPosition(bodyPosition.x * PIXELS_TO_METERS, bodyPosition.y * PIXELS_TO_METERS);
        }

        debugRenderer.render(world, cam.combined);
        stage.act(delta);
        stage.draw();

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
    }
}
