package io.github.some_example_name.screens;//package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.extras.Ground;
import io.github.some_example_name.actors.extras.Slingshot;
import io.github.some_example_name.actors.blocks.Block;
import io.github.some_example_name.actors.blocks.GlassBlock;
import io.github.some_example_name.actors.blocks.StoneBlock;
import io.github.some_example_name.actors.blocks.WoodBlock;
import io.github.some_example_name.actors.pigs.Pig;
import io.github.some_example_name.bonusStuff.BlackPower;
import io.github.some_example_name.returnStructs.CollisionReturnStruct;
import io.github.some_example_name.returnStructs.ReturnStruct;
import io.github.some_example_name.returnStructs.SetUpReturnStruct;
import io.github.some_example_name.setUp.CollisionManager;
import io.github.some_example_name.setUp.GameSetUp;
import io.github.some_example_name.setUp.LevelManager;
import io.github.some_example_name.setUp.WindowCreator;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private static final float PPM = 1;
    public static boolean isPaused=false;
    private final Main game;
    public final int level;
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
    private  Window pauseWindow;
    private TextButton pauseButton;
    private TextButton winButton;
    private Window winWindow;
    private TextButton nextLevelButton;
    private Window loseWindow;
    private final boolean isMusicPlaying;
    private TextButton musiconoffButton;
    private Slingshot slingshot;
    private Queue<Bird> birdQueue;
    public static List<Bird> allBirds;
    public List<Block> allBlocks;
    public List<Pig> allPigs;
    private boolean isLoaded;
    public float totalDamage=0;
    private boolean isCompleted = false;
    public LevelManager levelManager;
    public static final float PIXELS_TO_METERS = 100f;
    public static final float DAMAGE_MULTIPLIER = 0.1f;

    public static final short CATEGORY_BIRD = 0x0001;    // Binary 00000001
    public static final short CATEGORY_PIG = 0x0002;     // Binary 00000010
    public static final short CATEGORY_BLOCK = 0x0003;     // Binary 00000011
    public static final short CATEGORY_SLINGSHOT = 0x0004; // Binary 00000100
    public GameScreen(Main game,int level){
        this(game,level,false);
    }

    public GameScreen(Main game, int level,boolean isLoaded) {
        this.game = game;  // Save the reference to the main game object
        game.resume();
        this.level = level;  // Save the level number
//        this.totalDamage = 0;
        isMusicPlaying = game.isMusicPlaying();
        this.isLoaded=isLoaded;
        SetUpReturnStruct return1 = GameSetUp.initializeGameComponents();
        this.batch = return1.batch;
        this.font = return1.font;
        this.cam = return1.cam;
        this.stage = return1.stage;
        this.world = return1.world;
        this.debugRenderer = return1.debugRenderer;
        this.slingshot = return1.slingshot;

        levelManager = new LevelManager();

        SetUpReturnStruct return2 = GameSetUp.setupUIComponents(stage, levelManager, pauseWindow, winWindow, loseWindow, game, level, font,this);
        this.pauseButton = return2.pause;
        this.musiconoffButton = return2.music;
        this.nextLevelButton = return2.next;
        this.backButton = return2.back;
        this.pauseWindow = return2.pauseWindow;
        this.winWindow = return2.winWindow;
        this.loseWindow = return2.loseWindow;

        CollisionReturnStruct return3 = CollisionManager.show(world, level, totalDamage);
        this.assetManager = return3.assetManager;
        this.backgroundTexture = return3.backgroundTexture;
        this.totalDamage = return3.totalDamage;

        ReturnStruct returnStruct = null;
        if(isLoaded)returnStruct=levelManager.loadObjects(world);
        else if(level==1) returnStruct= levelManager.setupWorldObjectsLevel1(world);
        else if(level == 2) returnStruct= levelManager.setupWorldObjectsLevel2(world);
        else if (level == 3) returnStruct= levelManager.setupWorldObjectsLevel3(world);

        GameSetUp.setupListeners(stage, pauseButton, musiconoffButton, backButton, game, slingshot, this);

        if(returnStruct!=null) {
            this.birdQueue = returnStruct.birdQueue;
            this.allBirds = returnStruct.birds;
            this.allPigs = returnStruct.pigs;
            this.allBlocks = returnStruct.blocks;
            this.ground = returnStruct.ground;
        }

        Block wallLeft = new WoodBlock(0, 50, world, 1, 720, false);
        Block wallRight = new WoodBlock(1380, 50, world, 1, 720, false);
        batch.begin();
        wallLeft.draw(batch);
        wallRight.draw(batch);
        batch.end();
    }

    public void togglePause() {
        isPaused = !isPaused;
        pauseWindow.setVisible(isPaused);
        pauseWindow.toFront();
        if (isPaused) {
            pause();
        } else {
            resume();
        }
    }

    private void checkAndLoadBird() {
        if(allPigs.isEmpty()){
            isCompleted=true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    WindowCreator.showScore(font, true, allBirds.size(), level);
                    // Code to execute after 2 seconds
                    winWindow.setVisible(true);
                    winWindow.toFront();
                    Sound wonSound = Gdx.audio.newSound(Gdx.files.internal("LevelWon.mp3"));
                    wonSound.play();
                }
            }, 3f);
        }
        else if(birdQueue.isEmpty() && slingshot.isEmpty()){
            isCompleted=true;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if(!allPigs.isEmpty()) {
                        WindowCreator.showScore(font, false, allBirds.size(), level);
                        // Code to execute after 2 seconds
                        loseWindow.setVisible(true);
                        loseWindow.toFront();
                        Sound lostSound = Gdx.audio.newSound(Gdx.files.internal("LevelLost.mp3"));
                        lostSound.play();
                    }
                    else{
                        WindowCreator.showScore(font, true, allBirds.size(), level);
                        // Code to execute after 2 seconds
                        winWindow.setVisible(true);
                        winWindow.toFront();
                        Sound wonSound = Gdx.audio.newSound(Gdx.files.internal("LevelWon.mp3"));
                        wonSound.play();
                    }
                }
            }, 7f);
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

    private void update() {
        // Assume birds is a list of active birds
        List<Bird> birdsToRemove = new ArrayList<>();
        List<Pig> pigsToRemove =new ArrayList<>();
        List<Block> blocksToRemove =new ArrayList<>();
        for (Bird bird : allBirds) {
            if (bird.isLaunched() && bird.getBody().getLinearVelocity().len2() < 0.05f) { // Velocity close to zero
                birdsToRemove.add(bird);
            }
        }
        for(Bird bird :birdsToRemove){
            bird.disappear();
            allBirds.remove(bird); // Remove from active list
        }
        for(Pig pig:allPigs){
            if(pig.getHp()<=0){
                pigsToRemove.add(pig);
            }
        }
        for(Block block:allBlocks){
            if(block.getHp()<=0){
                blocksToRemove.add(block);
            }
        }
        for(Pig pig:pigsToRemove){
            pig.disappear();
            allPigs.remove(pig);
            Sound pigDestroyedSound = Gdx.audio.newSound(Gdx.files.internal("PigDestroyed.mp3"));
            pigDestroyedSound.play();
        }
        for(Block block:blocksToRemove){
            if (block instanceof WoodBlock) {
                Sound woodDestroyedSound = Gdx.audio.newSound(Gdx.files.internal("WoodDestroyed.mp3"));
                woodDestroyedSound.play();
            }
            else if (block instanceof GlassBlock) {
                Sound glassDestroyedSound = Gdx.audio.newSound(Gdx.files.internal("GlassDestroyed.mp3"));
                glassDestroyedSound.play();
            }
            else if (block instanceof StoneBlock) {
                Sound stoneDestroyedSound = Gdx.audio.newSound(Gdx.files.internal("StoneDestroyed.mp3"));
                stoneDestroyedSound.play();
            }
            block.disappear();
            allBlocks.remove(block);
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
        BlackPower.render(batch, Gdx.graphics.getDeltaTime());
        batch.end();

        update();

        stage.act(delta);
        stage.draw();
        if (!isPaused) world.step(1 / 60f, 6, 2);
        for (Block block : allBlocks) {
            block.update();
        }
        if(!isCompleted) {
            checkAndLoadBird();
        }
        checkForEscapeKey();
        debugRenderer.render(world, batch.getProjectionMatrix().cpy().scale(30,30 , 0));
        Gdx.input.setInputProcessor(stage);
    }

    private void checkForEscapeKey() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
        cam.update();
    }
    @Override
    public void show(){
    }
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
