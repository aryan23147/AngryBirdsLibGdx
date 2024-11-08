package io.github.some_example_name;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleDummy;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleMusic;
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
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;


public class GameScreen implements Screen {
    private boolean isPaused=false;
    private final Main game;
    private final int level;
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
    private Pig pig1, pig2, pig3;
    private Box box1, box2, box3, box4, box5, box6, box7, box8;
    private Window pauseWindow;
    private TextButton pauseButton;
    private TextButton winButton;
    private Window winWindow;
    private TextButton nextLevelButton;
    private Window loseWindow;
    private final boolean isMusicPlaying;
    private TextButton musiconoffButton;
    private Slingshot slingshot;
    private Queue<Bird> birdQueue;
    private List<Bird> allBirds;
    private List<Box> allBoxes;
    private List<Pig> allPigs;
    private static final float PIXELS_TO_METERS = 100f;

    public GameScreen(Main game, int level) {
        this.game = game;  // Save the reference to the main game object
        this.level = level;  // Save the level number
        isMusicPlaying = game.isMusicPlaying();
        initializeGameComponents();
        setupUIComponents();
        show();
        if(level==1) setupWorldObjectsLevel1();
        else if(level == 2) setupWorldObjectsLevel2();
        else if (level == 3) setupWorldObjectsLevel3();
        setupListeners();

        float w=Gdx.graphics.getWidth();
        float h=Gdx.graphics.getHeight();

        table.add(pauseWindow).center();

        backButton =new TextButton("",TextButtonStyleback);
        backButton.setSize(100,150);
        backButton.setPosition(100, stage.getHeight()-140); // Adjust x and y for placement
        stage.addActor(backButton);

        TextButton saveGameButton =new TextButton("",TextButtonStyleSave);
        TextButton restartButton= new TextButton("",TextButtonStyleRestart);

        pauseWindow.add().padBottom(300);
        pauseWindow.row();
        pauseWindow.add(musiconoffButton);
        pauseWindow.add(saveGameButton);
        pauseWindow.add(restartButton);
    }

    private void initializeGameComponents() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage();
        world = new World(new Vector2(0, -9.8f), false);
        debugRenderer = new Box2DDebugRenderer();
        slingshot = new Slingshot(130, 50);
        birdQueue = new Queue<>();
        allBirds = new ArrayList<>();
        allBoxes = new ArrayList<>();
        allPigs = new ArrayList<>();
    }

    private void setupUIComponents() {
        // Create the UI buttons, windows, and table layout
        pauseButton = new TextButton("", TextButtonStylepause);
        winButton = new TextButton("Win", TextButtonStyleDummy);
        musiconoffButton = new TextButton("", TextButtonStyleMusic);
        nextLevelButton = new TextButton("", createButtonStyle("abs/NextButton.png"));

        // Set up pause, win, and lose windows
        createPauseWindow();
        createWinWindow();
        createLoseWindow();

        // Manually set size and position for winButton
        winButton.setSize(100, 100); // Set the size of the win button
        winButton.setPosition(Gdx.graphics.getWidth() / 2f +100, Gdx.graphics.getHeight() / 2f + 160); // Set position in the center

        // Add buttons to the stage directly
        stage.addActor(winButton);

        // Configure table layout
        table = new Table();
        table.top().left().setFillParent(true);
        table.add(pauseButton).padTop(5f).padLeft(5f).top().left();
        stage.addActor(table);
    }

    private void setupWorldObjectsLevel1() {
        // Initialize game objects
        redBird = new RedBird(world, 125, 150);
        blackBird = new BlackBird(world, 80, 150);
        blueBird = new BlueBird(world, 20, 150);
        birdQueue.addFirst(redBird);
        birdQueue.addLast(blackBird);
        birdQueue.addLast(blueBird);
        allBirds.add(redBird);
        allBirds.add(blackBird);
        allBirds.add(blueBird);
        ground = new Ground(world);

        pig1 = new MediumPig(650, 200, world);
        allPigs.add(pig1);
        box1 = new Box(560, 200, world, 64, 64);
        box2 = new Box(560, 100, world, 64, 64);
        box3 = new Box(490, 100, world, 64, 64);
        allBoxes.add(box1);
        allBoxes.add(box2);
        allBoxes.add(box3);
    }
    private void setupWorldObjectsLevel2() {
        // Initialize game objects
        redBird = new RedBird(world, 125, 150);
        blackBird = new BlackBird(world, 80, 150);
        blueBird = new BlueBird(world, 20, 150);
        birdQueue.addFirst(redBird);
        birdQueue.addLast(blackBird);
        birdQueue.addLast(blueBird);
        allBirds.add(redBird);
        allBirds.add(blackBird);
        allBirds.add(blueBird);
        ground = new Ground(world);

        pig1 = new MediumPig(570, 180, world);
        pig2 = new KidPig(640, 180, world);
        allPigs.add(pig1);
        allPigs.add(pig2);
        box1 = new Box(600, 200, world, 250, 50);
        box2 = new Box(700, 100, world, 50, 80);
        box3 = new Box(500, 100, world, 50, 80);
        allBoxes.add(box1);
        allBoxes.add(box2);
        allBoxes.add(box3);
    }
    private void setupWorldObjectsLevel3() {
        // Initialize game objects
        redBird = new RedBird(world, 125, 150);
        blackBird = new BlackBird(world, 80, 150);
        blueBird = new BlueBird(world, 20, 150);
        birdQueue.addFirst(redBird);
        birdQueue.addLast(blackBird);
        birdQueue.addLast(blueBird);
        allBirds.add(redBird);
        allBirds.add(blackBird);
        allBirds.add(blueBird);
        ground = new Ground(world);

        pig1 = new KingPig(535, 200, world);
        pig2 = new MediumPig(535, 600, world);
        allPigs.add(pig1);
        allPigs.add(pig2);
        box1 = new Box(610, 150, world, 50, 50);
        box2 = new Box(610, 70, world, 50, 50);
        box3 = new Box(530, 70, world, 50, 50);
        box4 = new Box(475, 75, world, 50, 150);
        box5 = new Box(470, 260, world, 50, 50);
        box6 = new Box(590, 230, world, 50, 100);
        box7 = new Box(520, 320, world, 175, 50);
        box8 = new Box(460, 400, world, 50, 50);

        allBoxes.add(box1);
        allBoxes.add(box2);
        allBoxes.add(box3);
        allBoxes.add(box4);
        allBoxes.add(box5);
        allBoxes.add(box6);
        allBoxes.add(box7);
        allBoxes.add(box8);
    }

    private void setupListeners() {
        Gdx.input.setInputProcessor(stage);
        final boolean[] click = {false};
        pauseButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
                click[0] =true;
            }
        });

        musiconoffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toggleMusic();
                updateMusicButtonStyle();
                click[0] =true;
            }
        });

        winButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                winWindow.setVisible(true);
                winWindow.toFront();
                click[0] =true;
            }
        });

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!click[0]) {
                    slingshot.releaseBird(x,y);
                    click[0]=false;
                }
            }
        });
    }

    private void togglePause() {
        isPaused = !isPaused;
        pauseWindow.setVisible(isPaused);
        if (isPaused) {
            pause();
        } else {
            resume();
        }
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
        Timer timer = new Timer();
        if(birdQueue.isEmpty() && slingshot.isEmpty() && !allPigs.isEmpty()){

            timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    // Code to execute after 2 seconds
                    loseWindow.setVisible(true);
                    loseWindow.toFront();
                }
            }, 2f);
        }
        else if (slingshot.isEmpty()) {
            Bird bird = birdQueue.removeFirst();  // Get the next bird from the queue
            slingshot.loadBird(bird);  // Load it into the slingshot
            bird.setInSlingshot(true);  // Disable gravity for the bird in the slingshot
            bird.getBody().setTransform(520 / PIXELS_TO_METERS, 420 / PIXELS_TO_METERS, 0);
        }

        // Any bird left in the queue should fall due to gravity
        for (Bird b : birdQueue) {
            b.setInSlingshot(false);  // Enable gravity for birds not in the slingshot
        }
    }

    @Override
    public void show() {
        assetManager = new AssetManager();
        assetManager.load("GameBackground.png",Texture.class);
        assetManager.finishLoading();
        backgroundTexture = assetManager.get("GameBackground.png", Texture.class);
        updateMusicButtonStyle();
        // Initialize resources and setup the game for the given level
        System.out.println("Starting level: " + level);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        font.draw(batch, "Playing Level: " + level, 200, 400);

        for (Bird bird : allBirds) {
            bird.draw(batch);
        }
        for (Pig pig : allPigs){
            pig.draw(batch);
        }
        for (Box box : allBoxes){
            box.draw(batch);
        }

        slingshot.draw(batch);
        ground.draw(batch);
        batch.end();

        for (Bird bird : allBirds) {
            Vector2 bodyPosition = bird.getBody().getPosition(); // Get position in meters
            bird.setPosition(bodyPosition.x * PIXELS_TO_METERS, bodyPosition.y * PIXELS_TO_METERS);
        }

        debugRenderer.render(world, cam.combined);
        stage.act(delta);
        stage.draw();
        if (!isPaused) world.step(1 / 60f, 6, 2);
        for (Box box : allBoxes) {
            box.update();
        }
        checkAndLoadBird();
        checkForEscapeKey();
        // Add the game logic for this level here (e.g., enemy spawning, player movement)
    }
    private void checkForEscapeKey() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
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
        for (Box box : allBoxes) {
            box.dispose();
        }
    }
}
