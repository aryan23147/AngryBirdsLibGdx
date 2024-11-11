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
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Queue;
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
    private static final float PPM = 1;
    private boolean isPaused=false;
    private final Main game;
    private final int level;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private Table table;
    private TextButton backButton;
    private AssetManager assetManager;
    private Texture backgroundTexture;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Ground ground;
    private OrthographicCamera cam;
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
    private List<Block> allBlocks;
    private List<Pig> allPigs;
    private static final float PIXELS_TO_METERS = 100f;

    public GameScreen(Main game, int level) {
        this.game = game;  // Save the reference to the main game object
        this.level = level;  // Save the level number
        isMusicPlaying = game.isMusicPlaying();
        initializeGameComponents();
        setupUIComponents();
        show();
        ReturnStruct returnStruct = null;
        if(level==1) returnStruct=LevelCreator.setupWorldObjectsLevel1(world);
        else if(level == 2) returnStruct=LevelCreator.setupWorldObjectsLevel2(world);
        else if (level == 3) returnStruct=LevelCreator.setupWorldObjectsLevel3(world);
        setupListeners();

        if(returnStruct!=null) {
            this.birdQueue = returnStruct.birdQueue;
            this.allBirds = returnStruct.birds;
            this.allPigs = returnStruct.pigs;
            this.allBlocks = returnStruct.blocks;
            this.ground = returnStruct.ground;
        }

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
        allBlocks = new ArrayList<>();
        allPigs = new ArrayList<>();
    }

    private void setupUIComponents() {
        // Create the UI buttons, windows, and table layout
        pauseButton = new TextButton("", TextButtonStylepause);
        winButton = new TextButton("Win", TextButtonStyleDummy);
        musiconoffButton = new TextButton("", TextButtonStyleMusic);
        nextLevelButton = new TextButton("", WindowCreator.createButtonStyle("abs/NextButton.png", font));

        // Set up pause, win, and lose windows
        pauseWindow = WindowCreator.createPauseWindow(pauseWindow, font);
        winWindow = WindowCreator.createWinWindow(winWindow, backButton, nextLevelButton, level, game, stage, font);
        loseWindow = WindowCreator.createLoseWindow(loseWindow, backButton, level, game, stage, font);

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

    private void updateMusicButtonStyle() {
        if (isMusicPlaying) {
            musiconoffButton.setStyle(TextButtonStyleMusic);
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
            }, 5f);
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
    private void update(float deltaTime) {
        // Assume birds is a list of active birds
        List<Bird> birdsToRemove = new ArrayList<>();
        for (Bird bird : allBirds) {
            if (bird.isLaunched() && bird.getBody().getLinearVelocity().len2() < 0.005f) { // Velocity close to zero
                birdsToRemove.add(bird);
            }
        }
        for(Bird bird :birdsToRemove){
            world.destroyBody(bird.getBody()); // Destroy the physics body in Box2D
            allBirds.remove(bird); // Remove from active list
        }
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
        for (Block block : allBlocks){
            block.draw(batch);
        }

        slingshot.draw(batch);
        ground.draw(batch);
        batch.end();

        update(delta);
        for (Bird bird : allBirds) {
            Vector2 bodyPosition = bird.getBody().getPosition(); // Get position in meters
            bird.setPosition(bodyPosition.x * PIXELS_TO_METERS, bodyPosition.y * PIXELS_TO_METERS);
        }

        debugRenderer.render(world, cam.combined);
        stage.act(delta);
        stage.draw();
        if (!isPaused) world.step(1 / 60f, 6, 2);
        for (Block block : allBlocks) {
            block.update();
        }
        checkAndLoadBird();
        checkForEscapeKey();
//        debugRenderer.render(world, batch.getProjectionMatrix().cpy().scale(PPM, PPM, 0));

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
        for (Block block : allBlocks) {
            block.dispose();
        }
    }
}
